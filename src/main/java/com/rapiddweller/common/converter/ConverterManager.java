/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.Assert;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.LogCategoriesConstants;
import com.rapiddweller.common.OrderedMap;
import com.rapiddweller.common.Patterns;
import com.rapiddweller.common.ReaderLineIterator;
import com.rapiddweller.common.Resettable;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.context.ContextAware;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Manages converters. A default configuration is provided and can be overwritten by a local file 'converters.txt',
 * that lists each converter's class name, one name per line, e.g.
 * <pre>
 *     com.my.MyString2ThingConverter
 *     com.my.MyString2ComplexConverter
 * </pre>
 * Created: 04.08.2007 19:43:17
 * @author Volker Bergmann
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ConverterManager implements ContextAware, Resettable {

  private static final Logger configLogger = LoggerFactory.getLogger(LogCategoriesConstants.CONFIG);

  public static final String COMPONENT_NAME = "ConverterManager";

  private static final String DEFAULT_SETUP_FILENAME = "com/rapiddweller/common/converter/converters.txt";
  private static final String CUSTOM_SETUP_FILENAME = "converters.txt";

  private static ConverterManager instance;
  private Context context;

  private OrderedMap<ConversionTypes, Class<? extends Converter>> configuredConverterClasses;

  private Map<ConversionTypes, Converter> converterPrototypes;


  // static methods --------------------------------------------------------------------------------------------------

  public static <S, T> Object convertAll(S[] sourceValues, Converter<S, T> converter, Class componentType) {
    Object convertedValues = Array.newInstance(componentType, sourceValues.length);
    for (int i = 0; i < sourceValues.length; i++) {
      Array.set(convertedValues, i, converter.convert(sourceValues[i]));
    }
    return convertedValues;
  }

  public static <S, T> Collection<T> convertAll(Collection<S> sourceValues, Converter<S, T> converter) {
    List<T> result = new ArrayList<>(sourceValues.size());
    for (S sourceValue : sourceValues) {
      result.add(converter.convert(sourceValue));
    }
    return result;
  }

  public static <S, T> Converter<S, T>[] cloneIfSupported(Converter<S, T>[] prototypes) {
    Converter[] result = new Converter[prototypes.length];
    for (int i = 0; i < prototypes.length; i++) {
      result[i] = cloneIfSupported(prototypes[i]);
    }
    return result;
  }

  public static <S, T> Converter<S, T> cloneIfSupported(Converter<S, T> prototype) {
    Converter<S, T> result;
    if (prototype.isParallelizable()) {
      result = BeanUtil.clone(prototype);
    } else if (prototype.isThreadSafe()) {
      result = prototype;
    } else {
      result = new SynchronizedConverterProxy(prototype);
    }
    return result;
  }

  public static ConverterManager getInstance() {
    if (instance == null) {
      instance = new ConverterManager();
    }
    return instance;
  }

  public static void removeInstance() {
    instance = null;
  }

  @SuppressWarnings("cast")
  private static Converter tryToCreateStringConverter(Class targetType) {
    if (targetType.getEnumConstants() != null) {
      return new String2EnumConverter(targetType);
    } else if (targetType == Boolean.class) {
      return new String2BooleanConverter();
    } else if (Number.class.isAssignableFrom(targetType)) {
      if (targetType != Number.class) {
        return new String2NumberConverter((Class<Number>) targetType);
      } else {
        return new String2NumberConverter(Double.class);
      }
    } else if (targetType.isArray()) {
      Class componentType = targetType.getComponentType();
      if (componentType == byte.class) {
        return new String2ByteArrayConverter();
      } else {
        return new CommaSeparatedListConverter(componentType);
      }
    }
    return null;
  }

  private static <T> Converter<?, T> tryToCreateBooleanConverter(Class targetType) {
    if (Number.class.isAssignableFrom(targetType)) {
      return new Boolean2NumberConverter(targetType);
    }
    Class<?> wrapperClass = BeanUtil.getWrapper(targetType.getName());
    if (wrapperClass != null && Number.class.isAssignableFrom(wrapperClass)) {
      return new Boolean2NumberConverter(wrapperClass);
    }
    return null;
  }


  // constructor -----------------------------------------------------------------------------------------------------

  private ConverterManager() {
    init();
  }


  // interface -------------------------------------------------------------------------------------------------------

  @Override
  public void setContext(Context context) {
    this.context = context;
    for (Converter converter : converterPrototypes.values()) {
      injectContext(converter);
    }
  }

  public <S, T> Converter<S, T> createConverter(Class<S> sourceType, Class<T> targetType) {
    // check preconditions
    Assert.notNull(targetType, "targetType");

    // check if we already know how to do this conversion
    ConversionTypes conversionTypes = new ConversionTypes(sourceType, targetType);
    Converter result = converterPrototypes.get(conversionTypes);
    if (result != null) {
      return cloneIfSupported(result);
    }

    // we need to investigate...
    result = searchAppropriateConverter(sourceType, targetType);
    // ...and cache the result for future requests
    if (result != null && result.isParallelizable()) {
      converterPrototypes.put(conversionTypes, result);
    }

    // inject context if appropriate
    injectContext(result);

    // done
    return result;
  }

  @Override
  public void reset() {
    init();
  }


  // non-public helper methods ---------------------------------------------------------------------------------------

  protected void init() {
    this.configuredConverterClasses = new OrderedMap<>();
    this.converterPrototypes = new HashMap<>();
    try {
      if (IOUtil.isURIAvailable(CUSTOM_SETUP_FILENAME)) {
        configLogger.debug("Reading custom converter config: {}", CUSTOM_SETUP_FILENAME);
        readConfigFile(CUSTOM_SETUP_FILENAME);
      }
      readConfigFile(DEFAULT_SETUP_FILENAME);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().componentInitializationFailed(COMPONENT_NAME, e);
    }
  }

  private void injectContext(Converter converter) {
    if (converter instanceof ContextAware) {
      ((ContextAware) converter).setContext(context);
    }
  }

  private Converter searchAppropriateConverter(Class sourceType, Class targetType) {
    // catch primitive types
    Class<?> wrapperClass = BeanUtil.getWrapper(targetType.getName());
    if (wrapperClass != null) {
      return createConverter(sourceType, wrapperClass);
    }

    Converter result;
    if (targetType.isAssignableFrom(sourceType) && !targetType.isPrimitive()) {
      return new NoOpConverter();
    }

    // to string conversion
    if (String.class.equals(targetType)) {
      result = createToStringConverter(sourceType);
      if (result != null) {
        return result;
      }
    }

    // from string conversion
    if (String.class.equals(sourceType)) {
      result = tryToCreateStringConverter(targetType);
      if (result != null) {
        return result;
      }
    }

    // from number conversion
    if (Number.class.isAssignableFrom(sourceType) && Number.class.isAssignableFrom(targetType)) {
      return new NumberToNumberConverter(sourceType, targetType);
    }

    // from boolean conversion
    if (Boolean.class.isAssignableFrom(sourceType)) {
      result = tryToCreateBooleanConverter(targetType);
      if (result != null) {
        return result;
      }
    }

    if (targetType.isArray()) {
      return new ToArrayConverter(targetType.getComponentType());
    }

    if (Collection.class.isAssignableFrom(targetType)) {
      return new ToCollectionConverter(targetType);
    }

    result = tryToCreateFactoryConverter(sourceType, targetType);
    if (result != null) {
      return result;
    } else {
      throw ExceptionFactory.getInstance().conversionFailed(
          "Cannot convert " + sourceType.getName() + " to " + targetType.getName(), null);
    }
  }

  private Converter<?, String> createToStringConverter(Class<?> sourceType) throws ConversionException {
    if (sourceType.isArray()) {
      Class<?> componentType = sourceType.getComponentType();
      if (componentType == byte.class) {
        return new ByteArrayToBase64Converter();
      } else if (componentType == char.class) {
        return new CharArray2StringConverter();
      } else {
        return new FormatFormatConverter(sourceType, new ArrayFormat(), true);
      }
    } else if (sourceType == Time.class) {
      return new FormatFormatConverter<>(Time.class, new SimpleDateFormat(Patterns.DEFAULT_TIME_PATTERN), false);
    } else if (sourceType == Timestamp.class) {
      return new TimestampFormatter();
    } else if (sourceType == Date.class) {
      return new FormatFormatConverter<>(Time.class, new SimpleDateFormat(Patterns.DEFAULT_DATETIME_PATTERN), false);
    } else if (sourceType == Class.class) {
      return new Class2StringConverter();
    } else if (Enum.class.isAssignableFrom(sourceType)) {
      return new Enum2StringConverter(sourceType);
    } else {
      Converter<?, String> result = tryToCreateFactoryConverter(sourceType, String.class);
      return Objects.requireNonNullElseGet(result, () -> new ToStringMethodInvoker(sourceType));
    }
  }

  private Converter tryToCreateFactoryConverter(Class sourceType, Class targetType) {
    // find instance method <targetType>Value() in source type
    String typeValueMethodName = StringUtil.uncapitalize(targetType.getSimpleName()) + "Value";
    Method typeValueMethod = BeanUtil.findMethod(sourceType, typeValueMethodName);
    if (typeValueMethod != null && (typeValueMethod.getModifiers() & Modifier.STATIC) == 0) {
      return new SourceClassMethodInvoker(sourceType, targetType, typeValueMethod);
    }
    // find static getInstance() method in target type
    Method getInstanceMethod = BeanUtil.findMethod(targetType, "getInstance", sourceType);
    if (getInstanceMethod != null && (getInstanceMethod.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
      return new StaticTargetClassMethodInvoker(sourceType, targetType, getInstanceMethod);
    }
    // find static valueOf() method in target type
    Method valueOfMethod = BeanUtil.findMethod(targetType, "valueOf", sourceType);
    if (valueOfMethod != null && (valueOfMethod.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
      return new StaticTargetClassMethodInvoker(sourceType, targetType, valueOfMethod);
    }
    // find static fromString() method in target type
    Method fromStringMethod = BeanUtil.findMethod(targetType, "fromString", sourceType);
    if (fromStringMethod != null && (fromStringMethod.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
      return new StaticTargetClassMethodInvoker(sourceType, targetType, fromStringMethod);
    }
    // find target type constructor which takes source type argument
    Constructor constructor = BeanUtil.findConstructor(targetType, sourceType);
    if (constructor != null) {
      return new ConstructorInvoker(sourceType, constructor);
    }
    return findPoorConfiguredMatch(sourceType, targetType);
  }

  private Converter findPoorConfiguredMatch(Class<?> srcType, Class dstType) {
    if (srcType == dstType || (dstType.isAssignableFrom(srcType) && !dstType.isPrimitive())) {
      return new NoOpConverter();
    }
    for (Map.Entry<ConversionTypes, Class<? extends Converter>> entry : configuredConverterClasses.entrySet()) {
      ConversionTypes types = entry.getKey();
      if (types.sourceType == srcType && dstType.isAssignableFrom(types.targetType)) {
        return BeanUtil.newInstance(entry.getValue());
      }
    }
    return null;
  }

  public void registerConverterClass(Class<? extends Converter> converterClass) {
    Converter converter = BeanUtil.newInstance(converterClass);
    ConversionTypes types = new ConversionTypes(converter);
    configuredConverterClasses.put(types, converterClass);
    if (converter.isParallelizable()) {
      converterPrototypes.put(types, converter);
    }
  }

  private void readConfigFile(String filename) throws ClassNotFoundException {
    try (ReaderLineIterator iterator = new ReaderLineIterator(IOUtil.getReaderForURI(filename))) {
      while (iterator.hasNext()) {
        String className = iterator.next();
        registerConverterClass((Class<? extends Converter>) Class.forName(className));
      }
    }
  }

}

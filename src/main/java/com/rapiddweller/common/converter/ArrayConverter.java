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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Converts arrays from one component type to arrays of another component type.
 * If there are no converters registered, it only transforms the array type.
 * If there is a single converter, all elements are converted with the same converter.
 * If there are several converters, the number of converters and array elements are
 * assumed to be equal and each element is converted with the converter of the same index.
 * Created: 07.06.2007 14:35:18
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 */
public class ArrayConverter<S, T> extends MultiConverterWrapper<S, T> implements Converter<S[], T[]> {

  private final Class<T> targetComponentType;
  private final Class<S[]> sourceType;
  private final Class<T[]> targetType;

  @SuppressWarnings("unchecked")
  public ArrayConverter(Class<S> sourceComponentType, Class<T> targetComponentType, Converter<S, T>... converters) {
    super(converters);
    this.targetComponentType = targetComponentType;
    this.sourceType = ArrayUtil.arrayType(sourceComponentType);
    this.targetType = ArrayUtil.arrayType(targetComponentType);
  }

  @Override
  public Class<S[]> getSourceType() {
    return sourceType;
  }

  @Override
  public Class<T[]> getTargetType() {
    return targetType;
  }

  /**
   * If there are no converters, it only transforms the array type;
   * if there is a single converter, all elements are converted with the same converter;
   * if there are several converters, the number of converters and array elements are
   * assumed to be equal and each element is converted with the converter of the same index.
   */
  @Override
  public T[] convert(S[] sourceValues) throws ConversionException {
    if (sourceValues == null) {
      return null;
    }
    if (components.length == 0) {
      return convertWith(null, targetComponentType, sourceValues);
    } else if (components.length == 1) {
      return convertWith(components[0], targetComponentType, sourceValues);
    } else {
      if (sourceValues.length != components.length) {
        throw ExceptionFactory.getInstance().illegalArgument("Array has a different size than the converter list");
      }
      T[] result = ArrayUtil.newInstance(targetComponentType, components.length);
      for (int i = 0; i < components.length; i++) {
        result[i] = components[i].convert(sourceValues[i]);
      }
      return result;
    }
  }

  /**
   * Converts all array elements with the same {@link Converter}.
   * @param <S>           the object type to convert from
   * @param <T>           the object type to convert to
   * @param converter     the converter to apply
   * @param componentType the component type of the result array
   * @param sourceValues  the source values to convert
   * @return an array with the mapped values
   * @throws ConversionException if conversion fails
   */
  public static <S, T> T[] convertWith(Converter<S, T> converter, Class<T> componentType, S[] sourceValues) throws ConversionException {
    T[] result = ArrayUtil.newInstance(componentType, sourceValues.length);
    for (int i = 0; i < sourceValues.length; i++) {
      Object tmp = (converter != null ? converter.convert(sourceValues[i]) : sourceValues[i]);
      result[i] = AnyConverter.convert(tmp, componentType);
    }
    return result;
  }

}

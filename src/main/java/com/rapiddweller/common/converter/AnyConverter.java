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

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Patterns;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Converts any source type to any target type. It also makes use of the ConverterManager.
 * Created: 16.06.2007 11:34:42
 * @param <E> the type to convert to
 * @author Volker Bergmann
 */
public class AnyConverter<E> extends FormatHolder implements Converter<Object, E> {

  private static final Logger logger = LoggerFactory.getLogger(AnyConverter.class);

  private final Class<E> targetType;

  public AnyConverter(Class<E> targetType) {
    this(targetType, Patterns.DEFAULT_DATE_PATTERN);
  }

  public AnyConverter(Class<E> targetType, String datePattern) {
    this.targetType = targetType;
    this.datePattern = datePattern;
  }

  @Override
  public Class<Object> getSourceType() {
    return Object.class;
  }

  @Override
  public Class<E> getTargetType() {
    return targetType;
  }

  @Override
  public E convert(Object sourceValue) throws ConversionException {
    return convert(sourceValue, targetType, datePattern, timePattern, timestampPattern);
  }

  @Override
  public boolean isParallelizable() {
    return true;
  }

  @Override
  public boolean isThreadSafe() {
    return true;
  }

  public static <TT> TT convert(Object source, Class<TT> targetType) throws ConversionException {
    return convert(source, targetType, null, null, null);
  }

  /** Converts an object of a given type to an object of the target type. */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <TT> TT convert(Object source, Class<TT> targetType, String datePattern,
                                String timePattern, String timestampPattern) throws ConversionException {
    if (logger.isDebugEnabled()) {
      logger.debug("Converting " + source + (source != null ? " (" + source.getClass().getName() + ")" : "") + " to " + targetType);
    }
    if (source != null && targetType.equals(source.getClass())) {
      return (TT) source;
    }
    if (source == null) {
      if (targetType == double.class) {
        return (TT) (Double) Double.NaN;
      } else if (targetType == int.class) {
        return (TT) (Integer) 0;
      } else {
        return null;
      }
    }
    Converter converter = ConverterManager.getInstance().createConverter(source.getClass(), targetType);
    return (TT) converter.convert(source);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(-> " + targetType.getSimpleName() + ')';
  }

}

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

import com.rapiddweller.common.Converter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Converts Number objects of one type to another Number type.
 * Created: 16.06.2007 11:51:14
 *
 * @param <S> the number type to convert from
 * @param <T> the number type to convert to
 * @author Volker Bergmann
 */
public class NumberToNumberConverter<S extends Number, T extends Number> extends ConverterProxy<S, T> {

  /**
   * Instantiates a new Number to number converter.
   *
   * @param targetType the target type
   */
  @SuppressWarnings("unchecked")
  public NumberToNumberConverter(Class<T> targetType) {
    this((Class<S>) Number.class, targetType);
  }

  /**
   * Instantiates a new Number to number converter.
   *
   * @param sourceType the source type
   * @param targetType the target type
   */
  @SuppressWarnings("unchecked")
  public NumberToNumberConverter(Class<S> sourceType, Class<T> targetType) {
    super((Converter<S, T>) createConverter(targetType));
  }

  private static <TT extends Number> Converter<Number, ? extends Number> createConverter(Class<TT> targetType) {
    if (Integer.class == targetType || int.class == targetType) {
      return new Number2IntegerConverter();
    } else if (Long.class == targetType || long.class == targetType) {
      return new Number2LongConverter();
    } else if (Byte.class == targetType || byte.class == targetType) {
      return new Number2ByteConverter();
    } else if (Short.class == targetType || short.class == targetType) {
      return new Number2ShortConverter();
    } else if (Double.class == targetType || double.class == targetType) {
      return new Number2DoubleConverter();
    } else if (Float.class.equals(targetType) || float.class == targetType) {
      return new Number2FloatConverter();
    } else if (BigInteger.class.equals(targetType)) {
      return new Number2BigIntegerConverter();
    } else if (BigDecimal.class.equals(targetType)) {
      return new Number2BigDecimalConverter();
    } else {
      throw new IllegalArgumentException("Not a supported number type: " + targetType);
    }
  }

  /**
   * Converts a number of one number type to another number type.
   *
   * @param <TT>       the type to convert the number to
   * @param src        the number to convert
   * @param targetType the target number type of the conversion
   * @return an object of the target number type
   */
  @SuppressWarnings("unchecked")
  public static <TT extends Number> TT convert(Number src, Class<TT> targetType) {
    if (src == null) {
      return null;
    } else if (Integer.class == targetType || int.class == targetType) {
      return (TT) Integer.valueOf(src.intValue());
    } else if (Long.class == targetType || long.class == targetType) {
      return (TT) Long.valueOf(src.longValue());
    } else if (Byte.class == targetType || byte.class == targetType) {
      return (TT) Byte.valueOf(src.byteValue());
    } else if (Short.class == targetType || short.class == targetType) {
      return (TT) Short.valueOf(src.byteValue());
    } else if (Double.class == targetType || double.class == targetType) {
      return (TT) Double.valueOf(src.doubleValue());
    } else if (Float.class.equals(targetType) || float.class == targetType) {
      return (TT) Float.valueOf(src.floatValue());
    } else if (BigInteger.class.equals(targetType)) {
      return (TT) BigInteger.valueOf(src.longValue());
    } else if (BigDecimal.class.equals(targetType)) {
      return (TT) BigDecimal.valueOf(src.doubleValue());
    } else {
      throw new IllegalArgumentException("Not a supported number type: " + targetType);
    }
  }

}

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

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Converts an object to a String by using its toString() method.
 * Null values can be mapped to an individual String.
 * Created: 31.08.2006 18:44:59
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class ToStringConverter extends FormatHolder implements Converter<Object, String>, Cloneable {

  private static final ToStringConverter singletonInstance = new ToStringConverter();

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Default constructor that uses an isEmpty String as null representation
   */
  public ToStringConverter() {
    this(DEFAULT_NULL_STRING);
  }

  /**
   * Constructor that initializes the null replacement to the specified parameter.
   *
   * @param nullString the String to use for replacing null values.
   */
  public ToStringConverter(String nullString) {
    this(nullString, DEFAULT_DATE_PATTERN, DEFAULT_DATETIME_SECONDS_PATTERN + '.');
  }

  /**
   * Instantiates a new To string converter.
   *
   * @param nullString       the null string
   * @param datePattern      the date pattern
   * @param timestampPattern the timestamp pattern
   */
  public ToStringConverter(String nullString, String datePattern, String timestampPattern) {
    super(nullString, datePattern, timestampPattern);
  }

  // Converter interface implementation ------------------------------------------------------------------------------

  /**
   * Can convert boolean.
   *
   * @param sourceValue the source value
   * @return the boolean
   */
  public boolean canConvert(Object sourceValue) {
    return true;
  }

  @Override
  public Class<Object> getSourceType() {
    return Object.class;
  }

  @Override
  public Class<String> getTargetType() {
    return String.class;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String convert(Object source) throws ConversionException {
    if (source == null) {
      return nullString;
    } else if (source instanceof String) {
      if (stringQuote == null) {
        return (String) source;
      } else {
        return stringQuote + source + stringQuote;
      }
    } else if (source instanceof Character) {
      if (charQuote == null) {
        return String.valueOf(source);
      } else {
        return charQuote + source + charQuote;
      }
    }

    Class<?> sourceType = source.getClass();
    if (JavaType.isIntegralType(sourceType)) {
      if (integralConverter != null) {
        return integralConverter.convert((Number) source);
      } else {
        return String.valueOf(source);
      }
    } else if (JavaType.isDecimalType(sourceType)) {
      if (decimalConverter != null) {
        return decimalConverter.convert((Number) source);
      } else if (sourceType == BigDecimal.class) {
        return String.valueOf(source);
      } else {
        Double value = ((Number) source).doubleValue();
        if (value == Math.floor(value)) {
          return String.valueOf(value.longValue());
        } else {
          return String.valueOf(value);
        }
      }
    } else if (source instanceof Timestamp) {
      String result;
      if (timestampPattern != null) {
        result = new TimestampFormatter(timestampPattern).format((Timestamp) source);
      } else {
        result = new TimestampFormatter().format((Timestamp) source);
      }
      return applyCapitalization(timestampCapitalization, result);
    } else if (source instanceof Time) {
      if (timePattern != null) {
        return new SimpleDateFormat(timePattern).format((Date) source);
      } else {
        return new SimpleDateFormat().format((Date) source);
      }
    } else if (source instanceof Date) {
      String result;
      if (datePattern != null) {
        result = new SimpleDateFormat(datePattern).format((Date) source);
      } else {
        result = new SimpleDateFormat().format((Date) source);
      }
      return applyCapitalization(dateCapitalization, result);
    } else if (source instanceof Calendar) {
      String result;
      if (datePattern != null) {
        result = new SimpleDateFormat(datePattern).format(((Calendar) source).getTime());
      } else {
        result = new SimpleDateFormat().format(((Calendar) source).getTime());
      }
      return applyCapitalization(dateCapitalization, result);
    } else if (source instanceof Node) {
      return XMLNode2StringConverter.format(source);
    } else {
      ConverterManager manager = ConverterManager.getInstance();
      Converter converter = manager.createConverter(sourceType, String.class);
      return (String) converter.convert(source);
    }
  }

  private static String applyCapitalization(Capitalization capitalization, String text) {
    if (text == null) {
      return null;
    }
    switch (capitalization) {
      case upper:
        return text.toUpperCase();
      case lower:
        return text.toLowerCase();
      default:
        return text;
    }
  }

  @Override
  public boolean isThreadSafe() {
    return true;
  }

  @Override
  public boolean isParallelizable() {
    return true;
  }

  // utility methods -------------------------------------------------------------------------------------------------

  /**
   * Convert string.
   *
   * @param <TT>       the type parameter
   * @param source     the source
   * @param nullString the null string
   * @return the string
   */
  public static <TT> String convert(TT source, String nullString) {
    if (source == null) {
      return nullString;
    }
    return singletonInstance.convert(source);
  }

}

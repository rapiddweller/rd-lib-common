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

package com.rapiddweller.common;

import com.rapiddweller.common.converter.AnyConverter;
import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * java.lang.text.Format implementation for formatting and parsing arrays.
 * Created: 20.06.2007 07:04:37
 * @author Volker Bergmann
 */
public class ArrayFormat extends Format {

  // defaults --------------------------------------------------------------------------------------------------------

  private static final long serialVersionUID = 290320869220307493L;

  private static final String DEFAULT_SEPARATOR = ", ";
  private static final Converter<Object, String> DEFAULT_ITEM_FORMATTER = new ToStringConverter("null");

  // attributes ------------------------------------------------------------------------------------------------------

  private final Converter<Object, String> itemFormatter;
  private final String separator;

  // constructors ----------------------------------------------------------------------------------------------------

  public ArrayFormat() {
    this(DEFAULT_ITEM_FORMATTER, DEFAULT_SEPARATOR);
  }

  public ArrayFormat(String separator) {
    this(DEFAULT_ITEM_FORMATTER, separator);
  }

  public ArrayFormat(Converter<Object, String> itemFormat) {
    this(itemFormat, DEFAULT_SEPARATOR);
  }

  public ArrayFormat(Converter<Object, String> itemFormatter, String separator) {
    this.itemFormatter = itemFormatter;
    this.separator = separator;
  }

  // java.text.Format interface implementation -----------------------------------------------------------------------

  @Override
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    return formatPart(toAppendTo, itemFormatter, separator, 0, Array.getLength(obj), obj);
  }

  @Override
  public Object parseObject(String source, ParsePosition pos) {
    String[] result = parse(source, separator, String.class);
    pos.setIndex(source.length());
    return result;
  }

  // publicly available utility methods ------------------------------------------------------------------------------

  @SafeVarargs
  public static <T> String format(T... items) {
    return format(", ", items);
  }

  @SafeVarargs
  public static <T> String format(String separator, T... items) {
    if (items == null) {
      return "";
    }
    return formatPart(null, separator, 0, items.length, items);
  }

  @SafeVarargs
  public static <T> String format(Converter<Object, String> formatter, String separator, T... items) {
    return formatPart(formatter, separator, 0, items.length, items);
  }

  @SafeVarargs
  public static <T> String formatPart(int offset, int length, T... items) {
    return formatPart(null, DEFAULT_SEPARATOR, offset, length, items);
  }

  @SafeVarargs
  public static <T> String formatPart(String separator, int offset, int length, T... items) {
    return formatPart(null, separator, offset, length, items);
  }

  @SafeVarargs
  public static <T> String formatPart(Converter<Object, String> formatter, String separator, int offset, int length, T... items) {
    if (items.length == 0) {
      return "";
    }
    return formatPart(new StringBuilder(), formatter, separator, offset, length, items).toString();
  }

  public static <T, E extends Appendable> E formatPart(E toAppendTo, Converter<Object, String> formatter, String separator,
                                                       int offset, int length, Object items) {
    if (Array.getLength(items) == 0) {
      return toAppendTo;
    }
    try {
      if (formatter == null) {
        formatter = DEFAULT_ITEM_FORMATTER;
      }
      toAppendTo.append(formatter.convert(Array.get(items, offset)));
      for (int i = 1; i < length; i++) {
        toAppendTo.append(separator).append(formatter.convert(Array.get(items, offset + i)));
      }
      return toAppendTo;
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError("Error formatting part", e);
    }
  }

  public static String formatInts(String separator, int... items) {
    if (items.length == 0) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    builder.append(items[0]);
    for (int i = 1; i < items.length; i++) {
      builder.append(separator).append(items[i]);
    }
    return builder.toString();
  }

  public static String formatBytes(String separator, byte... items) {
    if (items.length == 0) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    builder.append(items[0]);
    for (int i = 1; i < items.length; i++) {
      builder.append(separator).append(items[i]);
    }
    return builder.toString();
  }

  public static String formatChars(String separator, char... items) {
    if (items.length == 0) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    builder.append(items[0]);
    for (int i = 1; i < items.length; i++) {
      builder.append(separator).append(items[i]);
    }
    return builder.toString();
  }

  public static String formatStrings(String separator, String... items) {
    if (items == null) {
      return "";
    }
    return formatPart(null, separator, 0, items.length, items);
  }

  // parse methods ---------------------------------------------------------------------------------------------------

  public static <T> T[] parse(String source, String separator, Class<T> componentType) {
    ArrayBuilder<T> builder = new ArrayBuilder<>(componentType);
    int i = 0;
    int sepIndex;
    while ((sepIndex = source.indexOf(separator, i)) >= 0) {
      String token = source.substring(i, sepIndex);
      builder.add(AnyConverter.convert(token, componentType));
      i = sepIndex + separator.length();
    }
    builder.add(AnyConverter.convert(source.substring(i), componentType));
    return builder.toArray();
  }

}

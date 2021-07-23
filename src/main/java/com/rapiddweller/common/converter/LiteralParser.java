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
import com.rapiddweller.common.StringCharacterIterator;
import com.rapiddweller.common.TimeUtil;

import java.sql.Time;
import java.util.Date;

/**
 * Parses the literal representation a simple type into an appropriate Java object of type
 * Boolean, Integer, Long, Double, Date or String.
 * Created: 19.03.2008 20:05:25
 *
 * @author Volker Bergmann
 */
public class LiteralParser extends ThreadSafeConverter<String, Object> {

  /**
   * Instantiates a new Literal parser.
   */
  public LiteralParser() {
    super(String.class, Object.class);
  }

  // Converter interface implementation ------------------------------------------------------------------------------

  @Override
  public Object convert(String sourceValue) throws ConversionException {
    return parse(sourceValue);
  }

  // static convenience methods --------------------------------------------------------------------------------------

  /**
   * parses a String into a date, number, boolean or String.
   * <code>
   * content := boolean | date | number | unparsed
   * boolean := 'true' | 'false'
   * date := digit{4} '-' digit{2} '-' digit{2} ['T' digit{2} ':' digit{2} [':' digit{2} ['.' digit{1,3}]]]
   * number := [-] digit* ['.' digit*]
   * </code>
   *
   * @param text the text to parse
   * @return a date, number, boolean or String that is the object representation of the specified text
   */
  public static Object parse(String text) {
    if (text == null || text.length() == 0) {
      return null;
    }
    String trimmed = text.trim();
    if (trimmed.length() == 0) {
      return text;
    }

    // test for boolean
    if ("true".equals(trimmed)) {
      return Boolean.TRUE;
    } else if ("false".equals(trimmed)) {
      return Boolean.FALSE;
    }

    // test for quoted string
    if ((trimmed.startsWith("'") && trimmed.endsWith("'")) || (trimmed.startsWith("\"") && trimmed.endsWith("\""))) {
      return trimmed;
    }

    // precheck for unparsed
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (!(c == ':' || c == '-' || c == 'T' || c == '.' || (c >= '0' && c <= '9') || c == ' ' || c == '\t')) {
        return text;
      }
    }

    // test for number or date
    StringCharacterIterator iterator = new StringCharacterIterator(trimmed);
    char c = iterator.next();
    if (c == '-') {
      Object number = parseNonNegativeNumber(iterator, true);
      return (number != null ? number : text);
    } else if (c >= '0' && c <= '9') {
      iterator.pushBack();
      Object tmp = parseNonNegativeNumber(iterator, false);
      if (tmp != null) {
        return tmp;
      }
      tmp = parseDate(trimmed);
      if (tmp != null) {
        return tmp;
      }
      tmp = parseTime(trimmed);
      if (tmp != null) {
        return tmp;
      }
    }
    return trimmed;
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static Object parseDate(String trimmed) {
    StringCharacterIterator iterator = new StringCharacterIterator(trimmed);
    // parse day
    Long year = parseNonNegativeIntegerPart(iterator, true);
    if (year == null || !iterator.hasNext() || iterator.next() != '-') {
      return null;
    }
    if (!iterator.hasNext()) {
      return null;
    }
    Long month = parseNonNegativeIntegerPart(iterator, true);
    if (month == null || !iterator.hasNext() || iterator.next() != '-') {
      return null;
    }
    if (!iterator.hasNext()) {
      return null;
    }
    month = month - 1;
    Long day = parseNonNegativeIntegerPart(iterator, true);
    if (day == null || day == 0) {
      return null;
    }
    Date baseDate = TimeUtil.date(year.intValue(), month.intValue(), day.intValue());
    if (!iterator.hasNext()) {
      return baseDate;
    }
    if (iterator.next() != 'T') {
      return null;
    }
    if (!iterator.hasNext()) {
      return null;
    }
    Object time = parseTime(iterator.remainingText());
    return (time != null && time instanceof Time ? TimeUtil.add(baseDate, (Time) time) : null);
  }

  private static Object parseTime(String trimmed) {
    StringCharacterIterator iterator = new StringCharacterIterator(trimmed);
    // parse hours:minutes
    Long hours = parseNonNegativeIntegerPart(iterator, true);
    if (hours == null || !iterator.hasNext() || iterator.next() != ':') {
      return null;
    }
    Long minutes = parseNonNegativeIntegerPart(iterator, true);
    if (minutes == null) {
      return null;
    }
    if (!iterator.hasNext()) {
      return TimeUtil.time(hours.intValue(), minutes.intValue(), 0, 0);
    }
    // parse seconds
    if (iterator.next() != ':') {
      return null;
    }
    Long seconds = parseNonNegativeIntegerPart(iterator, true);
    if (seconds == null) {
      return null;
    }
    if (!iterator.hasNext()) {
      return TimeUtil.time(hours.intValue(), minutes.intValue(), seconds.intValue(), 0);
    }
    // parse second fractions
    if (iterator.next() != '.') {
      return null;
    }
    Double f = parseFraction(iterator);
    if (f == null) {
      return null;
    }
    if (!iterator.hasNext()) {
      return TimeUtil.time(hours.intValue(), minutes.intValue(), seconds.intValue(), (int) (f * 1000));
    } else {
      return trimmed;
    }
  }

  private static Double parseFraction(StringCharacterIterator iterator) {
    Double p = null;
    Double base = 0.1;
    while (iterator.hasNext()) {
      char c = iterator.next();
      if (c >= '0' && c <= '9') {
        if (p == null) {
          p = 0.;
        }
        p += base * (c - '0');
      } else {
        iterator.pushBack();
        return p;
      }
      base /= 10;
    }
    return p;
  }

  private static Long parseNonNegativeIntegerPart(StringCharacterIterator iterator, boolean leadingZeros) {
    Long n = null;
    int c = '0';
    int digitCount = 0;
    while (iterator.hasNext() && Character.isDigit(c = iterator.next())) {
      digitCount++;
      if (n == null) {
        n = 0L;
      }
      int d = (c - '0');
      if (!leadingZeros && digitCount == 2 && n == 0) {
        return null; //
      }
      n = n * 10 + d;
      c = -1;
    }
    if (c != -1) {
      iterator.pushBack();
    }
    return n;
  }

  private static Object parseNonNegativeNumber(StringCharacterIterator iterator, boolean negative) {
    // parse integral number (part)
    Long n = parseNonNegativeIntegerPart(iterator, false);
    if (n == null) {
      return null;
    }
    // handle numbers without fraction digits
    if (!iterator.hasNext()) {
      if (n > Integer.MAX_VALUE) {
        return (negative ? -n : n);
      } else {
        return (negative ? -n.intValue() : n.intValue());
      }
    } else {
      if (iterator.next() == '.') {
        if (!iterator.hasNext()) {
          return (negative ? -(double) n : (double) n);
        }
      } else {
        return null;
      }
    }
    // parse fraction
    double p = n;
    double base = 0.1;
    while (iterator.hasNext()) {
      char c = iterator.next();
      if (c >= '0' && c <= '9') {
        p += base * (c - '0');
      } else {
        return null;
      }
      base /= 10;
    }
    return (negative ? -p : p);
  }

}

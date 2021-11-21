/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import com.rapiddweller.common.Patterns;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parses a String as a time value.<br/><br/>
 * Created: 14.03.2008 22:15:58
 * @author Volker Bergmann
 */
public class String2TimeConverter extends ThreadSafeConverter<String, Time> {

  private final String pattern;

  public String2TimeConverter() {
    this(null);
  }

  public String2TimeConverter(String pattern) {
    super(String.class, Time.class);
    this.pattern = pattern;
  }

  @Override
  public Time convert(String sourceValue) {
    return parse(sourceValue, pattern);
  }

  public static Time parse(String value) {
    return parse(value, null);
  }

  public static Time parse(String value, String pattern) {
    if (StringUtil.isEmpty(value)) {
      return null;
    }
    pattern = choosePattern(value, pattern);
    try {
      Date simpleDate = new SimpleDateFormat(pattern).parse(value);
      long millis = simpleDate.getTime();
      return new Time(millis);
    } catch (ParseException e) {
      throw ExceptionFactory.getInstance().conversionFailed("Failed to parse " + value, e);
    }
  }

  private static String choosePattern(String sourceValue, String pattern) {
    if (pattern == null) {
      switch (sourceValue.length()) {
        case 12:
          pattern = Patterns.DEFAULT_TIME_MILLIS_PATTERN;
          break;
        case 8:
          pattern = Patterns.DEFAULT_TIME_SECONDS_PATTERN;
          break;
        case 5:
          pattern = Patterns.DEFAULT_TIME_MINUTES_PATTERN;
          break;
        default:
          throw ExceptionFactory.getInstance().illegalArgument("Not a supported time format: " + sourceValue, null);
      }
    }
    return pattern;
  }

}

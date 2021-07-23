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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utility class for formatting in data in US locale in styles usual for humans
 * (HF = Human Format).<br><br>
 * Created: 13.11.2019 15:05:39
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class HF {

  private static final DecimalFormatSymbols US_SYMBOLS = DecimalFormatSymbols.getInstance(Locale.US);
  private static final DecimalFormat PCT100_FMT = new DecimalFormat("#,##0.0", US_SYMBOLS);
  private static final DecimalFormat DECIMAL_FMT = new DecimalFormat("#,##0.######", US_SYMBOLS);
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  /**
   * Format pct change 100 string.
   *
   * @param value the value
   * @return the string
   */
  public static String formatPctChange100(double value) {
    String result = formatPct100(value);
    if (value > 0) {
      result = "+" + result;
    }
    return result;
  }

  /**
   * Format pct 100 string.
   *
   * @param value the value
   * @return the string
   */
  public static String formatPct100(double value) {
    return (!Double.isNaN(value) ? PCT100_FMT.format(value) + "%" : "NaN");
  }

  /**
   * Format pct 100 string.
   *
   * @param value   the value
   * @param pattern the pattern
   * @return the string
   */
  public static String formatPct100(double value, String pattern) {
    return (!Double.isNaN(value) ? new DecimalFormat(pattern, US_SYMBOLS).format(value) + "%" : "NaN");
  }

  /**
   * Format string.
   *
   * @param value the value
   * @return the string
   */
  public static String format(double value) {
    return (!Double.isNaN(value) ? DECIMAL_FMT.format(value) : "NaN");
  }

  /**
   * Format string.
   *
   * @param time the time
   * @return the string
   */
  public static String format(LocalTime time) {
    return time.format(TIME_FORMATTER);
  }

}

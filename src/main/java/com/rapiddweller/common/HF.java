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
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class HF {

  private static final long KILOBYTE = 1024;
  private static final long MEGABYTE = 1024 * KILOBYTE;
  private static final long GIGABYTE = 1024 * MEGABYTE;

  private static final DecimalFormatSymbols US_SYMBOLS = DecimalFormatSymbols.getInstance(Locale.US);
  private static final DecimalFormat PCT100_FMT = new DecimalFormat("#,##0.0", US_SYMBOLS);
  private static final DecimalFormat DECIMAL_FMT = new DecimalFormat("#,##0.######", US_SYMBOLS);
  private static final DecimalFormat LONG_FMT = new DecimalFormat("#,##0", US_SYMBOLS);
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  private HF() {
    // private constructor to prevent instantiation of this utility class
  }

  public static String formatPctChange100(double value) {
    String result = formatPct100(value);
    if (value > 0) {
      result = "+" + result;
    }
    return result;
  }

  public static String formatPct100(double value) {
    return (!Double.isNaN(value) ? PCT100_FMT.format(value) + "%" : "NaN");
  }

  public static String formatPct100(double value, String pattern) {
    return (!Double.isNaN(value) ? new DecimalFormat(pattern, US_SYMBOLS).format(value) + "%" : "NaN");
  }

  public static String format(double value) {
    return (!Double.isNaN(value) ? DECIMAL_FMT.format(value) : "NaN");
  }

  public static String format(long value) {
    return LONG_FMT.format(value);
  }

  public static String format(LocalTime time) {
    return time.format(TIME_FORMATTER);
  }

  public static String formatDurationSec(int durationSecs) {
    int hours = durationSecs / 3600;
    int hourRest = durationSecs % 3600;
    int minutes = hourRest / 60;
    int seconds = hourRest % 60;
    if (hours >= 10) {
      return hours + " hours";
    } else if (hours > 1) {
      return hours + " hours " + minutes + " minutes";
    } else if (hours == 1) {
      return "1 hour" + (minutes > 0 ? " " + minutes + " minutes" : "");
    } else if (minutes > 10) {
      return minutes + " minutes";
    } else if (minutes > 2) {
      return minutes + " minutes " + seconds + " seconds";
    } else if (minutes == 1) {
      return "1 minute" + (seconds > 0 ? " " + seconds + " seconds" : "");
    } else if (seconds > 1) {
      return seconds + " seconds";
    } else if (seconds == 1) {
      return "1 second";
    } else {
      return "Less than a second";
    }
  }

  public static String pluralize(long count, String name) {
    if (name == null) {
      return name;
    }
    String result = format(count) + ' ';
    if (count == 1) {
      result += name;
    } else if (name.endsWith("y")) {
      result += name.substring(0, name.length() - 1) + "ies";
    } else if (name.endsWith("Y")) {
      result += name.substring(0, name.length() - 1) + "IES";
    } else if (name.endsWith("s")) {
      result += name + "es";
    } else if (name.endsWith("S")) {
      result += name + "ES";
    } else if (Character.isUpperCase(name.charAt(name.length() - 1))) {
      result += name + 'S';
    } else {
      result += name + 's';
    }
    return result;
  }

  public static String formatByteSize(long size) {
    if (size > GIGABYTE) {
      return format(size / MEGABYTE) + " MB";
    } else if (size > MEGABYTE) {
      return format(size / KILOBYTE) + " KB";
    } else {
      return format(size) + " bytes";
    }
  }

}

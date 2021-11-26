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

package com.rapiddweller.common;

import com.rapiddweller.common.exception.ExceptionFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import static com.rapiddweller.common.Patterns.DEFAULT_DATETIME_MILLIS_PATTERN;
import static com.rapiddweller.common.Patterns.DEFAULT_DATETIME_MINUTES_PATTERN;
import static com.rapiddweller.common.Patterns.DEFAULT_DATETIME_SECONDS_PATTERN;
import static com.rapiddweller.common.Patterns.DEFAULT_DATE_PATTERN;
import static com.rapiddweller.common.Patterns.DEFAULT_TIME_PATTERN;

/**
 * Provides utility methods for creating and manipulating Dates and Calendars.
 * Created: 06.06.2004 18:16:26
 * @author Volker Bergmann
 * @since 0.1
 */
public final class TimeUtil {

  public static final int SECOND_MILLIS = 1000;
  public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
  public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
  public static final int DAY_MILLIS = 24 * HOUR_MILLIS;
  public static final int WEEK_MILLIS = 7 * DAY_MILLIS;

  public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
  public static final TimeZone CENTRAL_EUROPEAN_TIME = TimeZone.getTimeZone("CET");
  public static final TimeZone PACIFIC_STANDARD_TIME = TimeZone.getTimeZone("PST");
  public static final TimeZone SINGAPORE_TIME = TimeZone.getTimeZone("SGT");

  private static final int[] MONTH_LENGTHS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  public static final int DECADE = 100;

  private static final ThreadLocal<DateFormat> defaultDateFormat
      = ThreadLocal.withInitial(DateFormat::getDateInstance);
  private static final ThreadLocal<DateFormat> defaultDateTimeSecondsFormat
      = ThreadLocal.withInitial(DateFormat::getDateTimeInstance);
  private static final ThreadLocal<DateFormat> monthDayFormat
      = ThreadLocal.withInitial(() -> new SimpleDateFormat("MM/yy"));
  private static final ThreadLocal<DateFormat> numberDateFormat
      = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));


  private TimeUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static int currentMonth() {
    return new GregorianCalendar().get(Calendar.MONTH);
  }

  public static int currentYear() {
    return new GregorianCalendar().get(Calendar.YEAR);
  }

  public static int monthLength(int month, int year) {
    if (month != Calendar.FEBRUARY) {
      return MONTH_LENGTHS[month];
    }
    return (isLeapYear(year) ? 29 : 28);
  }

  public static int yearLength(int year) {
    return (isLeapYear(year) ? 366 : 365);
  }

  public static boolean isWeekend(Date date) {
    return isWeekend(calendar(date));
  }

  public static boolean isWeekend(Calendar day) {
    int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
    return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
  }

  public static boolean isLeapYear(int year) {
    return Year.isLeap(year);
  }

  public static Date today() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    return calendar.getTime();
  }

  public static Calendar todayCalendar() {
    Calendar today = Calendar.getInstance();
    today.set(Calendar.MILLISECOND, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.HOUR_OF_DAY, 0);
    return today;
  }

  public static Date yesterday() {
    Calendar calendar = todayCalendar();
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    return calendar.getTime();
  }

  public static Date tomorrow() {
    Calendar calendar = todayCalendar();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  public static Date dateTime(String spec) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(spec);
    } catch (ParseException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Error parsing date " + spec, e);
    }
  }

  public static Date date(int year, int month, int day) {
    return calendar(year, month, day).getTime();
  }

  public static Date gmtDate(int year, int month, int day) {
    return calendar(year, month, day, TimeZone.getTimeZone("GMT")).getTime();
  }

  public static Date date(int year, int month, int day, int hours, int minutes, int seconds, int milliseconds) {
    return calendar(year, month, day, hours, minutes, seconds, milliseconds).getTime();
  }

  public static Date date(long millis) {
    Date date = new Date();
    date.setTime(millis);
    return date;
  }

  public static Calendar calendar(int year, int month, int day) {
    return new GregorianCalendar(year, month, day);
  }

  public static Calendar calendar(int year, int month, int day, TimeZone timeZone) {
    GregorianCalendar calendar = new GregorianCalendar(timeZone);
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    return calendar;
  }

  public static Calendar calendar(int year, int month, int day,
                                  int hours, int minutes, int seconds, int milliseconds) {
    GregorianCalendar calendar = new GregorianCalendar(year, month, day, hours, minutes, seconds);
    calendar.set(Calendar.MILLISECOND, milliseconds);
    return calendar;
  }

  public static Calendar calendar(int year, int month, int day,
                                  int hours, int minutes, int seconds, int milliseconds, TimeZone timeZone) {
    GregorianCalendar calendar = new GregorianCalendar(year, month, day, hours, minutes, seconds);
    calendar.setTimeZone(timeZone);
    calendar.set(Calendar.MILLISECOND, milliseconds);
    return calendar;
  }

  public static Calendar calendar(long millis) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    return calendar;
  }

  public static Calendar calendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  public static GregorianCalendar gregorianCalendar(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar;
  }

  public static String formatDateTime(Date dateTime, long resolution) {
    if (resolution <= DAY_MILLIS) {
      return formatDate(dateTime);
    } else {
      return formatDateTime(dateTime);
    }
  }

  public static String formatDateTime(Date date) {
    synchronized (defaultDateTimeSecondsFormat) {
      return defaultDateTimeSecondsFormat.get().format(date);
    }
  }

  public static String formatDate(Date date) {
    synchronized (defaultDateFormat) {
      return defaultDateFormat.get().format(date);
    }
  }

  public static String formatAsNumber(Date date) {
    synchronized (numberDateFormat) {
      return numberDateFormat.get().format(date);
    }
  }

  public static String formatCurrentDateTime(String pattern) {
    return new SimpleDateFormat(pattern).format(new Date());
  }

  public static Date max(Date date1, Date date2) {
    return (date1.before(date2) ? date2 : date1);
  }

  public static Date min(Date date1, Date date2) {
    return (date1.before(date2) ? date1 : date2);
  }

  public static boolean between(long test, long min, long max) {
    return (test >= min && test <= max);
  }

  public static boolean isNow(long time, long tolerance) {
    long now = System.currentTimeMillis();
    return between(time, now - tolerance, now + tolerance);
  }

  public static String formatMillis(long t) {
    Date date = new Date(t);
    return formatDate(date);
  }

  public static String formatMonth(Calendar calendar) {
    return monthDayFormat.get().format(calendar.getTime());
  }

  public static int year(Date date) {
    return dateField(date, Calendar.YEAR);
  }

  public static int month(Date date) {
    return dateField(date, Calendar.MONTH);
  }

  public static int dayOfMonth(Date date) {
    return dateField(date, Calendar.DAY_OF_MONTH);
  }

  public static int dayOfWeek(Date date) {
    return dateField(date, Calendar.DAY_OF_WEEK);
  }

  public static int hour(Date date) {
    return dateField(date, Calendar.HOUR_OF_DAY);
  }

  public static int minute(Date date) {
    return dateField(date, Calendar.MINUTE);
  }

  public static int second(Date date) {
    return dateField(date, Calendar.SECOND);
  }

  public static Date lastDayOfMonth(Date date) {
    return lastDayOfMonth(calendar(date)).getTime();
  }

  public static Calendar lastDayOfMonth(Calendar cal) {
    cal.add(Calendar.MONTH, 1);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.add(Calendar.DATE, -1);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal;
  }

  public static Date firstDayOfWeek(Date date) {
    Calendar cal = calendar(date);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    dayOfWeek = (dayOfWeek == 1 ? 7 : dayOfWeek - 1); // by default, sunday seems to be the first day of a week
    return TimeUtil.addDays(date, 1 - dayOfWeek);
  }

  public static Date lastDayOfWeek(Date date) {
    Calendar cal = calendar(date);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    dayOfWeek = (dayOfWeek == 1 ? 7 : dayOfWeek - 1); // by default, sunday seems to be the first day of a week
    return TimeUtil.addDays(date, 7 - dayOfWeek);
  }

  public static Date firstDayOfMonth(Date date) {
    Calendar cal = calendar(date);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date nthDayOfWeekInMonth(int n, int dayOfWeek, int month, int year) {
    Calendar cal = new GregorianCalendar(year, month, 1);
    cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, n);
    return cal.getTime();
  }

  public static Date addDays(Date date, int delta) {
    Calendar calendar = calendar(date);
    calendar.add(Calendar.DAY_OF_YEAR, delta);
    return calendar.getTime();
  }

  public static Date addMonths(Date date, int delta) {
    Calendar calendar = calendar(date);
    calendar.add(Calendar.MONTH, delta);
    return calendar.getTime();
  }

  public static Date addYears(Date date, int delta) {
    Calendar calendar = calendar(date);
    calendar.add(Calendar.YEAR, delta);
    return calendar.getTime();
  }

  public static Date add(Date date, int field, int i) {
    Calendar calendar = calendar(date);
    calendar.add(field, i);
    return calendar.getTime();
  }

  public static Date add(Date date, Date offset) {
    long millis = millisSinceOwnEpoch(offset);
    if (millis < 0) {
      millis -= millisSinceOwnEpoch(TimeUtil.date(0, 0, 0));
    }
    return new Date(date.getTime() + millis);
  }

  public static Object subtract(Date minuend, Date subtrahend) {
    return new Date(minuend.getTime() - millisSinceOwnEpoch(subtrahend));
  }

  public static int yearsBetween(Date from, Date until) {
    Calendar fromCalendar = calendar(from);
    Calendar untilCalendar = calendar(until);
    int years = untilCalendar.get(Calendar.YEAR) - fromCalendar.get(Calendar.YEAR);
    int month1 = untilCalendar.get(Calendar.MONTH);
    int month2 = fromCalendar.get(Calendar.MONTH);
    if (month1 < month2) { // DAY_OF_YEAR comparison would fail in leap years
      years--;
    } else if (month1 == month2 && untilCalendar.get(Calendar.DAY_OF_MONTH) < fromCalendar.get(Calendar.DAY_OF_MONTH)) {
      years--;
    }
    return years;
  }

  public static int daysBetween(Date from, Date until) {
    Calendar fromCalendar = calendar(from);
    Calendar untilCalendar = calendar(until);
    return daysBetween(fromCalendar, untilCalendar);
  }

  public static int daysBetween(Calendar fromCalendar, Calendar untilCalendar) {
    return julianDay(untilCalendar.get(Calendar.YEAR), untilCalendar.get(Calendar.MONTH) + 1, untilCalendar.get(Calendar.DAY_OF_MONTH)) -
        julianDay(fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH) + 1, fromCalendar.get(Calendar.DAY_OF_MONTH));
  }

  /** Calculates the julian day of a {@link Date}.
   *  See http://en.wikipedia.org/wiki/Julian_day
   *  @param date the date */
  public static int julianDay(Date date) {
    return julianDay(calendar(date));
  }

  /** Calculates the julian day of a {@link Calendar}.
   *  See http://en.wikipedia.org/wiki/Julian_day
   *  @param calendar the date */
  public static int julianDay(Calendar calendar) {
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    return julianDay(year, month + 1, day);
  }

  /** Calculates the julian day of a date.
   *  See http://en.wikipedia.org/wiki/Julian_day */
  public static int julianDay(int year, int month, int day) {
    int a = (14 - month) / 12;
    int y = year + 4800 - a;
    int m = month + 12 * a - 3;
    return day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
  }

  public static long millis(int year, int month, int day, int hour, int minute, int second) {
    GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute, second);
    return calendar.getTimeInMillis();
  }

  public static Time time(int hour, int minute) {
    return time(hour, minute, 0, 0);
  }

  public static Time time(int hour, int minute, int second) {
    return time(hour, minute, second, 0);
  }

  public static Time time(int hour, int minute, int second, int millisecond) {
    GregorianCalendar calendar = new GregorianCalendar(1970, Calendar.JANUARY, 1,
        hour, minute, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
    return new Time(calendar.getTimeInMillis());
  }

  public static Timestamp timestamp(int year, int month, int day, int hour, int minute, int second, int nanosecond) {
    Timestamp result = new Timestamp(millis(year, month, day, hour, minute, second));
    result.setNanos(nanosecond);
    return result;
  }

  public static DateFormat createDefaultDateFormat() {
    return new SimpleDateFormat(Patterns.DEFAULT_DATE_PATTERN);
  }

  public static boolean isMidnight(Date date) {
    // note that a calculation based on the raw millis value is not viable
    // since time zones like Singapore had 7:30 hours delay in 1970-01-01
    // and 8:00 hours now!
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    return (cal.get(Calendar.MILLISECOND) == 0 && cal.get(Calendar.SECOND) == 0
        && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.HOUR_OF_DAY) == 0);
  }

  public static Date midnightOf(Date date) {
    return midnightOf(calendar(date)).getTime();
  }

  public static Calendar midnightOf(Calendar cal) {
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal;
  }

  public static Time currentTime() {
    Calendar now = new GregorianCalendar();
    return time(now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), now.get(Calendar.MILLISECOND));
  }

  public static void runInTimeZone(TimeZone timeZone, Runnable action) {
    TimeZone originalZone = TimeZone.getDefault();
    try {
      TimeZone.setDefault(timeZone);
      action.run();
    } finally {
      TimeZone.setDefault(originalZone);
    }
  }

  public static <T> void callInTimeZone(TimeZone timeZone, Callable<T> action) throws Exception {
    TimeZone originalZone = TimeZone.getDefault();
    try {
      TimeZone.setDefault(timeZone);
      action.call();
    } finally {
      TimeZone.setDefault(originalZone);
    }
  }

  public static long millisSinceOwnEpoch(Date date) {
    return millisSinceOwnEpoch(date.getTime());
  }

  public static long millisSinceOwnEpoch(long millis) {
    return millis + TimeZone.getDefault().getOffset(0);
  }

  public static String formatDuration(long duration, boolean simplify, boolean includeMillies) {
    // calculate parts
    int hours = (int) (duration / 3600000);
    duration -= hours * 3600000L;
    int minutes = (int) (duration / 60000);
    duration -= 60000L * minutes;
    int seconds = (int) (duration / 1000);
    int millis = (int) (duration - seconds * 1000);
    // format string
    if (simplify) {
      return formatSimplified(hours, minutes, seconds, millis, includeMillies);
    } else {
      return formatFixed(hours, minutes, seconds, millis, includeMillies);
    }
  }

  public static Date parse(String dateOrTimeSpec) {
    if (StringUtil.isEmpty(dateOrTimeSpec)) {
      return null;
    }
    try {
      dateOrTimeSpec = dateOrTimeSpec.replace(' ', 'T');
      int sepIndex = dateOrTimeSpec.indexOf('.');
      Integer nanos = null;
      if (sepIndex >= 0) {
        String nanoSpec = StringUtil.padRight(dateOrTimeSpec.substring(sepIndex + 1), 9, '0');
        nanos = Integer.parseInt(nanoSpec);
        dateOrTimeSpec = dateOrTimeSpec.substring(0, sepIndex);
      }
      DateFormat format;
      if (dateOrTimeSpec.indexOf('T') >= 0) {
        switch (dateOrTimeSpec.length()) {
          case 16:
            format = new SimpleDateFormat(DEFAULT_DATETIME_MINUTES_PATTERN);
            break;
          case 19:
            format = new SimpleDateFormat(DEFAULT_DATETIME_SECONDS_PATTERN);
            break;
          case 23:
            format = new SimpleDateFormat(DEFAULT_DATETIME_MILLIS_PATTERN);
            break;
          default:
            throw ExceptionFactory.getInstance().syntaxErrorForText(dateOrTimeSpec, "Not a supported date format");
        }
        Date date = format.parse(dateOrTimeSpec);
        if (nanos == null) {
          return date;
        }
        Timestamp result = new Timestamp(date.getTime());
        result.setNanos(nanos);
        return result;
      } else if (dateOrTimeSpec.contains("-")) {
        format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return format.parse(dateOrTimeSpec);
      } else if (dateOrTimeSpec.contains(":")) {
        format = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        return new Time(format.parse(dateOrTimeSpec).getTime());
      } else {
        throw ExceptionFactory.getInstance().syntaxErrorForText("Not a supported date/time format", dateOrTimeSpec);
      }
    } catch (ParseException e) {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Failed to parse date or time", e, dateOrTimeSpec, -1, -1);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static int dateField(Date date, int field) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar.get(field);
  }

  private static String formatSimplified(int hours, int minutes, int seconds, int millis, boolean includeMillis) {
    StringBuilder builder = new StringBuilder();
    String unit = null;
    if (hours > 0) {
      builder.append(hours);
      unit = " h";
    }
    if (minutes > 0 || seconds > 0 || (includeMillis && millis > 0)) {
      if (unit != null) {
        builder.append(':');
        if (minutes < 10) {
          builder.append('0');
        }
      }
      if (unit != null || minutes > 0) {
        builder.append(minutes);
        if (minutes > 0 && unit == null) {
          unit = " min";
        }
      }
      if (seconds > 0 || (includeMillis && millis > 0)) {
        if (unit != null) {
          builder.append(':');
          if (seconds < 10) {
            builder.append('0');
          }
        }
        builder.append(seconds);
        if (unit == null) {
          unit = " s";
        }
        if (includeMillis) {
          appendMillis(millis, builder);
        }
      }
    } else if (builder.length() == 0) {
      builder.append("0");
      unit = " s";
    }

    builder.append(unit);
    return builder.toString();
  }

  private static String formatFixed(int hours, int minutes, int seconds, int millis, boolean includeMillies) {
    StringBuilder builder = new StringBuilder();
    builder.append(hours).append(':');
    if (minutes < 10) {
      builder.append('0');
    }
    builder.append(minutes);
    builder.append(':');
    if (seconds < 10) {
      builder.append('0');
    }
    builder.append(seconds);
    if (includeMillies) {
      appendMillis(millis, builder);
    }
    builder.append(" h");
    return builder.toString();
  }

  private static void appendMillis(int millis, StringBuilder builder) {
    builder.append(".");
    if (millis < 100) {
      builder.append('0');
    }
    if (millis < 10) {
      builder.append('0');
    }
    builder.append(millis);
  }

  public static int indexOfDate(Date searchedDate, Date[] sortedArray) {
    Assert.notNull(searchedDate, "searchedDate");
    Assert.notNull(sortedArray, "sortedArray");
    int index = Arrays.binarySearch(sortedArray, searchedDate);
    if (index < 0) {
      index = -index - 1;
    }
    index = Math.min(index, sortedArray.length - 1);
    return index;
  }

  public static Month monthOf(Date date) {
    return new Month(month(date), year(date));
  }

  public static Month thisMonth() {
    return new Month(currentMonth(), currentYear());
  }

  public static Month nextMonth() {
    int month = currentMonth();
    int year = currentYear();
    if (month <= Calendar.NOVEMBER) {
      return new Month(month + 1, year);
    } else {
      return new Month(Calendar.JANUARY, year + 1);
    }
  }

}

/* (c) Copyright 2019 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Provides utility methods for the classes from the java.time package and their conversion
 * from and to the classes of the java.util package.<br/><br/>
 * Created: 18.02.2019 18:57:43
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class JavaTimeUtil {

  private static final DateTimeFormatter HM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter HMS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


  /**
   * The constant WEEKDAY_NAMES.
   */
  @SuppressWarnings("unchecked")
  static final Map<String, DayOfWeek> WEEKDAY_NAMES = CollectionUtil.buildMap(
      "SUN", DayOfWeek.SUNDAY,
      "MON", DayOfWeek.MONDAY,
      "TUE", DayOfWeek.TUESDAY,
      "WED", DayOfWeek.WEDNESDAY,
      "THU", DayOfWeek.THURSDAY,
      "FRI", DayOfWeek.FRIDAY,
      "SAT", DayOfWeek.SATURDAY);

  private JavaTimeUtil() {
  }

  /**
   * Is last weekday of type in month boolean.
   *
   * @param dayOfWeek the day of week
   * @param date      the date
   * @return the boolean
   */
  public static boolean isLastWeekdayOfTypeInMonth(DayOfWeek dayOfWeek, LocalDate date) {
    if (date.getDayOfWeek() != dayOfWeek) {
      return false;
    }
    LocalDate check = date.plus(7, ChronoUnit.DAYS);
    return (check.getMonth() != date.getMonth());
  }

  /**
   * Parse day of week day of week.
   *
   * @param weekdaySpec the weekday spec
   * @return the day of week
   */
  public static DayOfWeek parseDayOfWeek(String weekdaySpec) {
    DayOfWeek dayOfWeek = WEEKDAY_NAMES.get(weekdaySpec.toUpperCase());
    if (dayOfWeek == null) {
      throw new IllegalArgumentException("Not a supported day abbreviation: " + weekdaySpec);
    }
    return dayOfWeek;
  }

  /**
   * Julian day int.
   *
   * @param date the date
   * @return the int
   */
  public static int julianDay(LocalDate date) {
    return TimeUtil.julianDay(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }

  /**
   * Millis since epoch long.
   *
   * @param dateTime the date time
   * @return the long
   */
  public static long millisSinceEpoch(ZonedDateTime dateTime) {
    return dateTime.toInstant().toEpochMilli();
  }

  /**
   * Millis since epoch long.
   *
   * @param date the date
   * @return the long
   */
  public static long millisSinceEpoch(LocalDate date) {
    return date.toEpochDay() * TimeUtil.DAY_MILLIS;
  }

  /**
   * To date date.
   *
   * @param dateTime the date time
   * @return the date
   */
  public static Date toDate(ZonedDateTime dateTime) {
    return (dateTime != null ? Date.from(dateTime.toInstant()) : null);
  }

  /**
   * To calendar calendar.
   *
   * @param localDate the local date
   * @return the calendar
   */
  public static Calendar toCalendar(LocalDate localDate) {
    Date date = toDate(localDate);
    return TimeUtil.calendar(date.getTime());
  }

  /**
   * To date date.
   *
   * @param localDate the local date
   * @return the date
   */
  public static Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   * To local date local date.
   *
   * @param calendar the calendar
   * @return the local date
   */
  public static LocalDate toLocalDate(Calendar calendar) {
    return toLocalDate(calendar.getTime());
  }

  /**
   * To local date local date.
   *
   * @param date the date
   * @return the local date
   */
  public static LocalDate toLocalDate(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * To local date local date.
   *
   * @param dateTime the date time
   * @return the local date
   */
  public static LocalDate toLocalDate(ZonedDateTime dateTime) {
    return dateTime.toLocalDate();
  }

  /**
   * To local date time local date time.
   *
   * @param dateToConvert the date to convert
   * @return the local date time
   */
  public static LocalDateTime toLocalDateTime(Date dateToConvert) {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * To zoned date time zoned date time.
   *
   * @param date the date
   * @return the zoned date time
   */
  public static ZonedDateTime toZonedDateTime(Date date) {
    return (date != null ? toZonedDateTime(date.getTime(), ZoneId.systemDefault()) : null);
  }

  /**
   * To zoned date time zoned date time.
   *
   * @param millis the millis
   * @param zone   the zone
   * @return the zoned date time
   */
  public static ZonedDateTime toZonedDateTime(long millis, ZoneId zone) {
    return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).withZoneSameInstant(zone);
  }

  /**
   * To zoned date time zoned date time.
   *
   * @param date the date
   * @param zone the zone
   * @return the zoned date time
   */
  public static ZonedDateTime toZonedDateTime(LocalDate date, ZoneId zone) {
    return ZonedDateTime.of(date, LocalTime.of(0, 0), zone);
  }

  /**
   * Granularity millis int.
   *
   * @param field the field
   * @return the int
   */
  public static int granularityMillis(ChronoUnit field) {
    switch (field) {
      case DAYS:
        return TimeUtil.DAY_MILLIS;
      case HOURS:
        return TimeUtil.HOUR_MILLIS;
      case MINUTES:
        return TimeUtil.MINUTE_MILLIS;
      case SECONDS:
        return TimeUtil.SECOND_MILLIS;
      default:
        throw new UnsupportedTemporalTypeException(field + " is not supported");
    }
  }

  /**
   * Round down zoned date time.
   *
   * @param dateTime the date time
   * @param field    the field
   * @return the zoned date time
   */
  public static ZonedDateTime roundDown(ZonedDateTime dateTime, ChronoField field) {
    ChronoUnit chronoUnit;
    switch (field) {
      case DAY_OF_MONTH:
      case DAY_OF_WEEK:
      case DAY_OF_YEAR:
        chronoUnit = ChronoUnit.DAYS;
        break;
      case HOUR_OF_AMPM:
      case HOUR_OF_DAY:
        chronoUnit = ChronoUnit.HOURS;
        break;
      case MINUTE_OF_HOUR:
      case MINUTE_OF_DAY:
        chronoUnit = ChronoUnit.MINUTES;
        break;
      case SECOND_OF_MINUTE:
      case SECOND_OF_DAY:
        chronoUnit = ChronoUnit.SECONDS;
        break;
      default:
        throw new UnsupportedTemporalTypeException(field + " is not supported");
    }
    return dateTime.truncatedTo(chronoUnit);
  }

  /**
   * Time to int int.
   *
   * @param time the time
   * @return the int
   */
  public static int timeToInt(LocalTime time) {
    return time.getHour() * 100 + time.getMinute();
  }

  /**
   * Days between int.
   *
   * @param fromZdt  the from zdt
   * @param untilZdt the until zdt
   * @return the int
   */
  public static int daysBetween(ZonedDateTime fromZdt, ZonedDateTime untilZdt) {
    LocalDate from = fromZdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
    LocalDate until = untilZdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
    return daysBetween(from, until);
  }

  /**
   * Days between int.
   *
   * @param from  the from
   * @param until the until
   * @return the int
   */
  public static int daysBetween(LocalDate from, LocalDate until) {
    return TimeUtil.julianDay(until.getYear(), until.getMonthValue(), until.getDayOfMonth() -
        TimeUtil.julianDay(from.getYear(), from.getMonthValue(), from.getDayOfMonth()));
  }

  /**
   * Format string.
   *
   * @param date    the date
   * @param pattern the pattern
   * @return the string
   */
  public static String format(LocalDate date, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return date.format(formatter);
  }

  /**
   * Format string.
   *
   * @param dateTime the date time
   * @param pattern  the pattern
   * @return the string
   */
  public static String format(LocalDateTime dateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return dateTime.format(formatter);
  }

  /**
   * Parse date local date.
   *
   * @param dateSpec the date spec
   * @param pattern  the pattern
   * @return the local date
   */
  public static LocalDate parseDate(String dateSpec, String pattern) {
    return parseDate(dateSpec, pattern, Locale.US);
  }

  /**
   * Parse date local date.
   *
   * @param dateSpec the date spec
   * @param pattern  the pattern
   * @param locale   the locale
   * @return the local date
   */
  public static LocalDate parseDate(String dateSpec, String pattern, Locale locale) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
    return LocalDate.parse(dateSpec, formatter);
  }

  /**
   * Parse date time local date time.
   *
   * @param dateSpec the date spec
   * @param pattern  the pattern
   * @return the local date time
   */
  public static LocalDateTime parseDateTime(String dateSpec, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateSpec, formatter);
  }

  /**
   * Parse zoned date time zoned date time.
   *
   * @param spec    the spec
   * @param pattern the pattern
   * @return the zoned date time
   */
  public static ZonedDateTime parseZonedDateTime(String spec, String pattern) {
    return ZonedDateTime.parse(spec, DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * Parse zoned date time zoned date time.
   *
   * @param spec    the spec
   * @param pattern the pattern
   * @param zone    the zone
   * @return the zoned date time
   */
  public static ZonedDateTime parseZonedDateTime(String spec, String pattern, ZoneId zone) {
    return ZonedDateTime.parse(spec, DateTimeFormatter.ofPattern(pattern).withZone(zone));
  }

  /**
   * Millis between long.
   *
   * @param zdt1 the zdt 1
   * @param zdt2 the zdt 2
   * @return the long
   */
  public static long millisBetween(ZonedDateTime zdt1, ZonedDateTime zdt2) {
    return zdt1.toInstant().toEpochMilli() - zdt2.toInstant().toEpochMilli();
  }

  /**
   * Number of day of week in month int.
   *
   * @param date the date
   * @return the int
   */
  public static int numberOfDayOfWeekInMonth(LocalDate date) {
    return (date.getDayOfMonth() - date.get(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)) / 7 + 1;
  }

  /**
   * Day of week in month id int.
   *
   * @param date the date
   * @return the int
   */
  public static int dayOfWeekInMonthId(LocalDate date) {
    int dayOfWeek = date.getDayOfWeek().getValue();
    return numberOfDayOfWeekInMonth(date) * 10 + dayOfWeek;
  }

  /**
   * Nth of weekday in month local date.
   *
   * @param number    the number of the weekday in the month. For negative numbers it is counted backwards,                  so -1 is the last occurrence of the weekday in the month.
   * @param dayOfWeek the day of the week
   * @param month     the month
   * @param year      the year
   * @return a LocalDate object that satisfies the requirements
   */
  public static LocalDate nthOfWeekdayInMonth(int number, DayOfWeek dayOfWeek, int month, int year) {
    LocalDate date = LocalDate.of(year, month, 1);
    return date.with(TemporalAdjusters.dayOfWeekInMonth(number, dayOfWeek));
  }

  /**
   * Relative day of month int.
   *
   * @param date the date to analyze
   * @return the day of the month relative to the month change: 1..15 represent the regular calendar days, -15..0 the number of days before the 1st day of the next month.
   */
  public static int relativeDayOfMonth(LocalDate date) {
    int day = date.get(ChronoField.DAY_OF_MONTH);
    if (day <= 15) {
      return day;
    } else {
      Month month = date.getMonth();
      return day - month.length(date.isLeapYear());
    }
  }

  /**
   * Min local date.
   *
   * @param d1 the d 1
   * @param d2 the d 2
   * @return the local date
   */
  public static LocalDate min(LocalDate d1, LocalDate d2) {
    return (d2.isAfter(d1) ? d1 : d2);
  }

  /**
   * Max local date.
   *
   * @param d1 the d 1
   * @param d2 the d 2
   * @return the local date
   */
  public static LocalDate max(LocalDate d1, LocalDate d2) {
    return (d2.isAfter(d1) ? d2 : d1);
  }

  /**
   * Parse local time local time.
   *
   * @param dateSpec the date spec
   * @param pattern  the pattern
   * @return the local time
   */
  public static LocalTime parseLocalTime(String dateSpec, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalTime.parse(dateSpec, formatter);
  }

  /**
   * Parse local date local date.
   *
   * @param spec the spec
   * @return the local date
   */
  public static LocalDate parseLocalDate(String spec) {
    return (spec != null ? LocalDate.parse(spec) : null);
  }

  /**
   * Parse local time local time.
   *
   * @param spec the spec
   * @return the local time
   */
  public static LocalTime parseLocalTime(String spec) {
    return LocalTime.parse(spec, parserFor(spec));
  }

  /**
   * Format string.
   *
   * @param time the time
   * @return the string
   */
  public static String format(LocalTime time) {
    return time.format(formatterFor(time));
  }

  private static DateTimeFormatter parserFor(String spec) {
    return (spec.length() == 8 ? HMS_FORMATTER : HM_FORMATTER);
  }

  private static DateTimeFormatter formatterFor(LocalTime time) {
    return (time.getSecond() != 0 ? HMS_FORMATTER : HM_FORMATTER);
  }

}

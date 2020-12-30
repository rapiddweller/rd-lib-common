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
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class JavaTimeUtil {
	
	private static final DateTimeFormatter HM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	private static final DateTimeFormatter HMS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	
	@SuppressWarnings("unchecked")
	static final Map<String, DayOfWeek> WEEKDAY_NAMES = CollectionUtil.buildMap(
			"SUN", DayOfWeek.SUNDAY,
			"MON", DayOfWeek.MONDAY,
			"TUE", DayOfWeek.TUESDAY,
			"WED", DayOfWeek.WEDNESDAY,
			"THU", DayOfWeek.THURSDAY,
			"FRI", DayOfWeek.FRIDAY,
			"SAT", DayOfWeek.SATURDAY); 
	
	private JavaTimeUtil() {}
	
	public static boolean isLastWeekdayOfTypeInMonth(DayOfWeek dayOfWeek, LocalDate date) {
		if (date.getDayOfWeek() != dayOfWeek)
			return false;
    	LocalDate check = date.plus(7, ChronoUnit.DAYS);
		return (check.getMonth() != date.getMonth());
	}

	public static DayOfWeek parseDayOfWeek(String weekdaySpec) {
		DayOfWeek dayOfWeek = WEEKDAY_NAMES.get(weekdaySpec.toUpperCase());
		if (dayOfWeek == null)
			throw new IllegalArgumentException("Not a supported day abbreviation: " + weekdaySpec);
		return dayOfWeek;
	}
	
	public static int julianDay(LocalDate date) {
		return TimeUtil.julianDay(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
	}

	public static long millisSinceEpoch(ZonedDateTime dateTime) {
		return dateTime.toInstant().toEpochMilli();
	}
	
	public static long millisSinceEpoch(LocalDate date) {
		return date.toEpochDay() * TimeUtil.DAY_MILLIS;
	}
	
	public static Date toDate(ZonedDateTime dateTime) {
		return (dateTime != null ? Date.from(dateTime.toInstant()) : null);
	}
	
	public static Calendar toCalendar(LocalDate localDate) {
		Date date = toDate(localDate);
		return TimeUtil.calendar(date.getTime());
	}

	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static LocalDate toLocalDate(Calendar calendar) {
		return toLocalDate(calendar.getTime());
	}
	
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDate toLocalDate(ZonedDateTime dateTime) {
		return dateTime.toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(Date dateToConvert) {
	    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static ZonedDateTime toZonedDateTime(Date date) {
    	return (date != null ? toZonedDateTime(date.getTime(), ZoneId.systemDefault()) : null);
	}

	public static ZonedDateTime toZonedDateTime(long millis, ZoneId zone) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).withZoneSameInstant(zone);
	}
	
	public static ZonedDateTime toZonedDateTime(LocalDate date, ZoneId zone) {
    	return ZonedDateTime.of(date,  LocalTime.of(0, 0), zone);
	}
	
	public static int granularityMillis(ChronoUnit field) {
		switch (field) {
			case DAYS: return TimeUtil.DAY_MILLIS;
			case HOURS: return TimeUtil.HOUR_MILLIS;
			case MINUTES: return TimeUtil.MINUTE_MILLIS;
			case SECONDS: return TimeUtil.SECOND_MILLIS;
			default: throw new UnsupportedTemporalTypeException(field + " is not supported");
		}
	}

	public static ZonedDateTime roundDown(ZonedDateTime dateTime, ChronoField field) {
		ChronoUnit chronoUnit;
		switch (field) {
			case DAY_OF_MONTH: case DAY_OF_WEEK: case DAY_OF_YEAR: chronoUnit = ChronoUnit.DAYS; break;
			case HOUR_OF_AMPM: case HOUR_OF_DAY: chronoUnit = ChronoUnit.HOURS; break;
			case MINUTE_OF_HOUR: case MINUTE_OF_DAY: chronoUnit = ChronoUnit.MINUTES; break;
			case SECOND_OF_MINUTE: case SECOND_OF_DAY: chronoUnit = ChronoUnit.SECONDS; break;
			default: throw new UnsupportedTemporalTypeException(field + " is not supported");
		}
		return dateTime.truncatedTo(chronoUnit);
	}
	
	public static int timeToInt(LocalTime time) {
		return time.getHour() * 100 + time.getMinute();
	}
	
    public static int daysBetween(ZonedDateTime fromZdt, ZonedDateTime untilZdt) {
    	LocalDate from = fromZdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
    	LocalDate until = untilZdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
		return daysBetween(from, until);
	}

    public static int daysBetween(LocalDate from, LocalDate until) {
		return TimeUtil.julianDay(until.getYear(), until.getMonthValue(), until.getDayOfMonth() -
				TimeUtil.julianDay(from.getYear(), from.getMonthValue(), from.getDayOfMonth()));
	}

	public static String format(LocalDate date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	public static String format(LocalDateTime dateTime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(formatter);
	}

	public static LocalDate parseDate(String dateSpec, String pattern) {
		return parseDate(dateSpec, pattern, Locale.US);
	}

	public static LocalDate parseDate(String dateSpec, String pattern, Locale locale) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
		return LocalDate.parse(dateSpec, formatter);
	}

	public static LocalDateTime parseDateTime(String dateSpec, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateSpec, formatter);
	}

	public static ZonedDateTime parseZonedDateTime(String spec, String pattern) {
		return ZonedDateTime.parse(spec, DateTimeFormatter.ofPattern(pattern));
	}
	
	public static ZonedDateTime parseZonedDateTime(String spec, String pattern, ZoneId zone) {
		return ZonedDateTime.parse(spec, DateTimeFormatter.ofPattern(pattern).withZone(zone));
	}
	
	public static long millisBetween(ZonedDateTime zdt1, ZonedDateTime zdt2) {
		return zdt1.toInstant().toEpochMilli() - zdt2.toInstant().toEpochMilli();
	}
	
	public static int numberOfDayOfWeekInMonth(LocalDate date) {
		return (date.getDayOfMonth() - date.get(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)) / 7 + 1;
	}
	
	public static int dayOfWeekInMonthId(LocalDate date) {
		int dayOfWeek = date.getDayOfWeek().getValue();
		return numberOfDayOfWeekInMonth(date) * 10 + dayOfWeek;
	}
	
	/** @param number the number of the weekday in the month. For negative numbers it is counted backwards, 
	 *  	so -1 is the last occurrence of the weekday in the month. 
	 * @param dayOfWeek the day of the week
	 * @param month the month
	 * @param year the year
	 * @return a LocalDate object that satisfies the requirements */
	public static LocalDate nthOfWeekdayInMonth(int number, DayOfWeek dayOfWeek, int month, int year) {
		LocalDate date = LocalDate.of(year, month, 1);
		return date.with(TemporalAdjusters.dayOfWeekInMonth(number, dayOfWeek));
	}
	
	/** @param date the date to analyze
	 *  @return the day of the month relative to the month change: 
	 *		1..15 represent the regular calendar days, -15..0 the number 
	 * 		of days before the 1st day of the next month. */
	public static int relativeDayOfMonth(LocalDate date) {
		int day = date.get(ChronoField.DAY_OF_MONTH);
		if (day <= 15) {
			return day;
		} else {
			Month month = date.getMonth();
			return day - month.length(date.isLeapYear());
		}
	}
	
	public static LocalDate min(LocalDate d1, LocalDate d2) {
		return (d2.isAfter(d1) ? d1 : d2);
	}

	public static LocalDate max(LocalDate d1, LocalDate d2) {
		return (d2.isAfter(d1) ? d2 : d1);
	}
	
	public static LocalTime parseLocalTime(String dateSpec, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalTime.parse(dateSpec, formatter);
	}

	public static LocalDate parseLocalDate(String spec) {
		return (spec != null ? LocalDate.parse(spec) : null);
	}
	
	public static LocalTime parseLocalTime(String spec) {
		return LocalTime.parse(spec, parserFor(spec));
	}
	
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

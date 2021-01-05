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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Tests the {@link TimeUtil} class.
 * Created: 17.02.2005 21:19:01
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class TimeUtilTest {

    @Test
    public void testCurrentMonth() {
        assertEquals(0, TimeUtil.currentMonth());
    }

    @Test
    public void testCurrentYear() {
        assertEquals(2021, TimeUtil.currentYear());
    }

    @Test
    public void testMonthLength() {
        assertEquals(31, TimeUtil.monthLength(Calendar.JANUARY, 2013));
        assertEquals(28, TimeUtil.monthLength(Calendar.FEBRUARY, 2013));
        assertEquals(29, TimeUtil.monthLength(Calendar.FEBRUARY, 2012));
        assertEquals(31, TimeUtil.monthLength(Calendar.MARCH, 2013));
        assertEquals(30, TimeUtil.monthLength(Calendar.APRIL, 2013));
        assertEquals(31, TimeUtil.monthLength(Calendar.MAY, 2013));
        assertEquals(30, TimeUtil.monthLength(Calendar.JUNE, 2013));
        assertEquals(31, TimeUtil.monthLength(Calendar.JULY, 2013));
        assertEquals(31, TimeUtil.monthLength(Calendar.AUGUST, 2013));
        assertEquals(30, TimeUtil.monthLength(Calendar.SEPTEMBER, 2013));
        assertEquals(31, TimeUtil.monthLength(Calendar.OCTOBER, 2013));
        assertEquals(30, TimeUtil.monthLength(Calendar.NOVEMBER, 2013));
        assertEquals(31, TimeUtil.monthLength(Calendar.DECEMBER, 2013));
        assertEquals(28, TimeUtil.monthLength(1, 1));
        assertEquals(31, TimeUtil.monthLength(0, 1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> TimeUtil.monthLength(28, 1));
        assertEquals(29, TimeUtil.monthLength(1, 0));
    }

    @Test
    public void testYearLength() {
        assertEquals(365, TimeUtil.yearLength(1));
        assertEquals(366, TimeUtil.yearLength(0));
    }

    @Test
    public void testIsWeekend() {
        assertFalse(TimeUtil.isWeekend(new GregorianCalendar(1, 1, 1)));
        assertTrue(TimeUtil.isWeekend(new GregorianCalendar(0, 1, 1)));
        assertTrue(TimeUtil.isWeekend(new GregorianCalendar(-1, 1, 1)));
        assertFalse(TimeUtil.isWeekend(new GregorianCalendar(0, 0, 1)));
    }

    @Test
    public void testIsWeekend2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertFalse(TimeUtil.isWeekend(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testIsWeekend3() {
        LocalDateTime atStartOfDayResult = LocalDate.of(0, 1, 1).atStartOfDay();
        assertTrue(TimeUtil.isWeekend(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testIsWeekend4() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 4).atStartOfDay();
        assertTrue(TimeUtil.isWeekend(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testIsLeapYear() {
        assertFalse(TimeUtil.isLeapYear(1));
        assertTrue(TimeUtil.isLeapYear(0));
    }

    @Test
    public void testTodayCalendar() {
        assertTrue(TimeUtil.todayCalendar() instanceof java.util.GregorianCalendar);
    }

    @Test
    public void testFirstDayOfWeek() {
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 26)));
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 27)));
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 28)));
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 29)));
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 30)));
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 31)));
        assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 8, 1)));
    }

    @Test
    public void testLastDayOfWeek() {
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 26)));
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 27)));
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 28)));
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 29)));
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 30)));
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 31)));
        assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 8, 1)));
    }

    @Test
    public void testFirstDayOfMonth() {
        assertEquals(TimeUtil.date(1970, 0, 1), TimeUtil.firstDayOfMonth(TimeUtil.date(0)));
        assertEquals(TimeUtil.date(2008, 1, 1), TimeUtil.firstDayOfMonth(TimeUtil.date(2008, 1, 15)));
        assertEquals(TimeUtil.date(2009, 1, 1), TimeUtil.firstDayOfMonth(TimeUtil.date(2009, 1, 28)));
    }

    @Test
    public void testLastDayOfMonth() {
        assertEquals(TimeUtil.date(1970, 0, 31), TimeUtil.lastDayOfMonth(TimeUtil.date(0)));
        assertEquals(TimeUtil.date(2008, 1, 29), TimeUtil.lastDayOfMonth(TimeUtil.date(2008, 1, 15)));
        assertEquals(TimeUtil.date(2009, 1, 28), TimeUtil.lastDayOfMonth(TimeUtil.date(2009, 1, 28)));
    }

    @Test
    public void testLastDayOfMonth2() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(1, 1, 1);
        assertSame(gregorianCalendar, TimeUtil.lastDayOfMonth(gregorianCalendar));
    }


    @Test
    public void testAddDays() {
        // adding 0
        assertEquals(TimeUtil.date(1970, 0, 1), TimeUtil.addDays(TimeUtil.date(1970, 0, 1), 0));
        // simple case
        assertEquals(TimeUtil.date(1970, 0, 3), TimeUtil.addDays(TimeUtil.date(1970, 0, 1), 2));
        // over month's end
        assertEquals(TimeUtil.date(2008, 3, 15), TimeUtil.addDays(TimeUtil.date(2008, 2, 15), 31));
        // over year's end
        assertEquals(TimeUtil.date(2010, 1, 28), TimeUtil.addDays(TimeUtil.date(2009, 1, 28), 365));
        // over year's end + leap year
        assertEquals(TimeUtil.date(2008, 2, 1), TimeUtil.addDays(TimeUtil.date(2007, 1, 28), 367));
    }

    @Test
    public void testAddMonths() {
        // adding 0
        assertEquals(TimeUtil.date(1970, 0, 1), TimeUtil.addMonths(TimeUtil.date(1970, 0, 1), 0));
        // simple addition
        assertEquals(TimeUtil.date(1970, 3, 1), TimeUtil.addMonths(TimeUtil.date(1970, 1, 1), 2));
        // 31. of the moth + n months -> month's end (31/30/29/28)
        assertEquals(TimeUtil.date(2008, 3, 30), TimeUtil.addMonths(TimeUtil.date(2008, 0, 31), 3));
        // over year's end
        assertEquals(TimeUtil.date(2011, 1, 28), TimeUtil.addMonths(TimeUtil.date(2009, 1, 28), 24));
        // over year's end + leap year
        assertEquals(TimeUtil.date(2009, 1, 28), TimeUtil.addMonths(TimeUtil.date(2008, 1, 29), 12));
    }

    @Test
    public void testAddYears() {
        // adding 0
        assertEquals(TimeUtil.date(1970, 0, 1), TimeUtil.addYears(TimeUtil.date(1970, 0, 1), 0));
        // simple addition
        assertEquals(TimeUtil.date(1972, 1, 1), TimeUtil.addYears(TimeUtil.date(1970, 1, 1), 2));
        // Add ing to Feb, 29 in leap year -> Feb. 28/29
        assertEquals(TimeUtil.date(2011, 1, 28), TimeUtil.addYears(TimeUtil.date(2008, 1, 29), 3));
    }

    @Test
    public void testMax() {
        Date now = new Date();
        Date later = new Date(now.getTime() + Period.DAY.getMillis());
        assertEquals(later, TimeUtil.max(now, later));
        assertEquals(later, TimeUtil.max(later, now));
    }

    @Test
    public void testMin() {
        Date now = new Date();
        Date later = new Date(now.getTime() + Period.DAY.getMillis());
        assertEquals(now, TimeUtil.min(now, later));
        assertEquals(now, TimeUtil.min(later, now));
    }

    @Test
    public void testBetween() {
        assertTrue(TimeUtil.between(1L, 1L, 1L));
        assertFalse(TimeUtil.between(0L, 1L, 1L));
        assertFalse(TimeUtil.between(86400000L, 1L, 1L));
    }

    @Test
    public void testIsNow() {
        assertFalse(TimeUtil.isNow(10L, 1L));
        assertFalse(TimeUtil.isNow(9223372036854775807L, 1L));
    }

    @Test
    public void testFormatMillis() {
        assertEquals("Jan 1, 1970", TimeUtil.formatMillis(1L));
    }

    @Test
    public void testFormatMonth() {
        assertEquals("02/01", TimeUtil.formatMonth(new GregorianCalendar(1, Calendar.FEBRUARY, 1)));
    }

    @Test
    public void testYear() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(1970, TimeUtil.year(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testMonth() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0, TimeUtil.month(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testDayOfMonth() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(1, TimeUtil.dayOfMonth(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testDayOfWeek() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(5, TimeUtil.dayOfWeek(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testHour() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0, TimeUtil.hour(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testMinute() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0, TimeUtil.minute(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testSecond() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0, TimeUtil.second(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testIsMidnight() {
        TimeZone zone = TimeZone.getDefault();
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            assertTrue(TimeUtil.isMidnight(new Date(0)));
        } finally {
            TimeZone.setDefault(zone);
        }
    }

    @Test
    public void testIsMidnight2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertTrue(TimeUtil.isMidnight(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testIsMidnight3() {
        LocalDateTime atStartOfDayResult = LocalDate.of(0, 1, 1).atStartOfDay();
        assertFalse(TimeUtil.isMidnight(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testIsMidnight4() {
        assertFalse(TimeUtil.isMidnight(new Date(1L)));
    }

    @Test
    public void testIsMidnight5() {
        assertFalse(TimeUtil.isMidnight(new Date(0L)));
    }

    @Test
    public void testMidnightOf() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(1, 1, 1);
        assertSame(gregorianCalendar, TimeUtil.midnightOf(gregorianCalendar));
    }

    @Test
    public void testMillisSinceOwnEpoch() {
        assertEquals(3600001L, TimeUtil.millisSinceOwnEpoch(1L));
    }

    @Test
    public void testMillisSinceOwnEpoch2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0L,
                TimeUtil.millisSinceOwnEpoch(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testDate() {
        Date date = TimeUtil.date(2009, 6, 19);
        Calendar calendar = new GregorianCalendar(2009, 6, 19);
        assertEquals(calendar.getTime(), date);
    }

    @Test
    public void testCalendar() {
        assertTrue(TimeUtil.calendar(1, 1, 1) instanceof java.util.GregorianCalendar);
        assertTrue(TimeUtil.calendar(1, 1, 1, 1, 1, 1, 1000) instanceof java.util.GregorianCalendar);
        assertTrue(
                TimeUtil.calendar(1, 1, 1, 1, 1, 1, 1000, new SimpleTimeZone(1, "ID")) instanceof java.util.GregorianCalendar);
        assertTrue(TimeUtil.calendar(1, 1, 1, new SimpleTimeZone(1, "ID")) instanceof java.util.GregorianCalendar);
        assertTrue(TimeUtil.calendar(7, 1, 1, new SimpleTimeZone(1, "ID")) instanceof java.util.GregorianCalendar);
        assertTrue(TimeUtil.calendar(1L) instanceof java.util.GregorianCalendar);
    }

    @Test
    public void testCalendar2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertTrue(TimeUtil.calendar(Date
                .from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())) instanceof java.util.GregorianCalendar);
    }

    @Test
    public void testFormatCurrentDateTime() {
        assertEquals("-", TimeUtil.formatCurrentDateTime("-"));
    }

    @Test
    public void testTimestamp() {
        Timestamp timestamp = TimeUtil.timestamp(2009, 6, 19, 1, 34, 45, 123456789);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date(timestamp.getTime()));
        assertEquals(2009, cal.get(Calendar.YEAR));
        assertEquals(6, cal.get(Calendar.MONTH));
        assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(1, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(34, cal.get(Calendar.MINUTE));
        assertEquals(45, cal.get(Calendar.SECOND));
        assertEquals(123, cal.get(Calendar.MILLISECOND));
    }

    @Test
    public void testCreateDefaultDateFormat() {
        assertTrue(TimeUtil.createDefaultDateFormat() instanceof java.text.SimpleDateFormat);
    }

    @Test
    public void testYearsBetween() {
        Date base = TimeUtil.date(2008, 2, 24);
        Date threeLater = TimeUtil.date(2011, 2, 24);
        assertEquals(3, TimeUtil.yearsBetween(base, threeLater));
        Date lessThanThreeLater = TimeUtil.date(2011, 2, 23);
        assertEquals(2, TimeUtil.yearsBetween(base, lessThanThreeLater));
    }

    @Test
    public void testYearsBetween2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date from = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0,
                TimeUtil.yearsBetween(from, Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testYearsBetween3() {
        LocalDateTime atStartOfDayResult = LocalDate.of(0, 1, 1).atStartOfDay();
        Date from = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(1968,
                TimeUtil.yearsBetween(from, Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testYearsBetween4() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 2, 1).atStartOfDay();
        Date from = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(-1,
                TimeUtil.yearsBetween(from, Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testYearsBetween5() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date from = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 2, 1).atStartOfDay();
        assertEquals(0,
                TimeUtil.yearsBetween(from, Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testAdd_epoche() {
        assertEquals(TimeUtil.date(2010, 7, 31), TimeUtil.add(TimeUtil.date(2010, 7, 30), TimeUtil.date(1970, 0, 2)));
    }

    @Test
    public void testAdd_AC() {
        assertEquals(TimeUtil.date(2010, 7, 31), TimeUtil.add(TimeUtil.date(2010, 7, 30), TimeUtil.date(0, 0, 1)));
    }

    @Test
    public void testSubtract() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date minuend = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertTrue(TimeUtil.subtract(minuend,
                Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())) instanceof Date);
    }

    @Test
    public void testFormatDuration() {
        assertEquals("0.001 s", TimeUtil.formatDuration(1L, true, true));
        assertEquals("0 s", TimeUtil.formatDuration(0L, true, true));
        assertEquals("1 h", TimeUtil.formatDuration(3600000L, true, true));
        assertEquals("1 min", TimeUtil.formatDuration(60000L, true, true));
        assertEquals("1.000 s", TimeUtil.formatDuration(1000L, true, true));
        assertEquals("0.100 s", TimeUtil.formatDuration(100L, true, true));
        assertEquals("2047687697:0-909387756:55.808 h", TimeUtil.formatDuration(-9223372036854775808L, true, true));
        assertEquals("0:00:00.001 h", TimeUtil.formatDuration(1L, false, true));
        assertEquals("0 s", TimeUtil.formatDuration(1L, true, false));
        assertEquals("1 min", TimeUtil.formatDuration(60000L, true, false));
        assertEquals("1 s", TimeUtil.formatDuration(1000L, true, false));
        assertEquals("2047687697:0-909387756:55.808 h", TimeUtil.formatDuration(-9223372036854775808L, false, true));
        assertEquals("-2047687697:909387756:0-55.00-809 h", TimeUtil.formatDuration(9223372036854775807L, false, true));
        assertEquals("0:00:00 h", TimeUtil.formatDuration(1L, false, false));
    }

    @Test
    public void testFormatDurationFixed() {
        // 1 ms
        assertEquals("0:00:00 h", TimeUtil.formatDuration(1, false, false));
        assertEquals("0:00:00.001 h", TimeUtil.formatDuration(1, false, true));
        // 1 s
        assertEquals("0:00:01 h", TimeUtil.formatDuration(1000, false, false));
        assertEquals("0:00:01.000 h", TimeUtil.formatDuration(1000, false, true));
        // 1 min
        assertEquals("0:01:00 h", TimeUtil.formatDuration(60000, false, false));
        assertEquals("0:01:00.000 h", TimeUtil.formatDuration(60000, false, true));
        // 1 h
        assertEquals("1:00:00 h", TimeUtil.formatDuration(3600000, false, false));
        assertEquals("1:00:00.000 h", TimeUtil.formatDuration(3600000, false, true));
        // 25 h
        assertEquals("25:00:00 h", TimeUtil.formatDuration(25 * 3600000, false, false));
        assertEquals("25:00:00.000 h", TimeUtil.formatDuration(25 * 3600000, false, true));

        assertEquals("0:00:00.009 h", TimeUtil.formatDuration(9, false, true));
        assertEquals("0:00:00.012 h", TimeUtil.formatDuration(12, false, true));
        assertEquals("0:00:00.123 h", TimeUtil.formatDuration(123, false, true));
        assertEquals("0:00:02.345 h", TimeUtil.formatDuration(2345, false, true));
        assertEquals("0:01:12.345 h", TimeUtil.formatDuration(72345, false, true));
        assertEquals("0:11:01.000 h", TimeUtil.formatDuration(661000, false, true));
        assertEquals("2:59:00.001 h", TimeUtil.formatDuration(10740001, false, true));
        assertEquals("27:59:59.999 h", TimeUtil.formatDuration(100799999, false, true));
    }

    @Test
    public void testFormatDurationSimplified() {
        // 1 ms
        assertEquals("0 s", TimeUtil.formatDuration(1, true, false));
        assertEquals("0.001 s", TimeUtil.formatDuration(1, true, true));
        // 1 s
        assertEquals("1 s", TimeUtil.formatDuration(1000, true, false));
        assertEquals("1.000 s", TimeUtil.formatDuration(1000, true, true));
        // 1 min
        assertEquals("1 min", TimeUtil.formatDuration(60999, true, false));
        assertEquals("1:01 min", TimeUtil.formatDuration(61000, true, false));
        assertEquals("1:59 min", TimeUtil.formatDuration(119000, true, false));
        assertEquals("1 min", TimeUtil.formatDuration(60000, true, true));
        // 1 h
        assertEquals("1 h", TimeUtil.formatDuration(3600000, true, false));
        assertEquals("1 h", TimeUtil.formatDuration(3600000, true, true));
        assertEquals("1:59 h", TimeUtil.formatDuration(7140000, true, true));
        // 25 h
        assertEquals("25 h", TimeUtil.formatDuration(90000000, true, false));
        assertEquals("25 h", TimeUtil.formatDuration(90000000, true, true));

        assertEquals("1:00.001 min", TimeUtil.formatDuration(60001, true, true));
        assertEquals("1:02:03 h", TimeUtil.formatDuration(3723000, true, false));
        assertEquals("1:02:03.009 h", TimeUtil.formatDuration(3723009, true, true));
    }

    @Test
    public void testParse() {
        assertEquals(TimeUtil.date(2012, 3, 26), TimeUtil.parse("2012-04-26"));
        assertEquals(TimeUtil.date(2012, 3, 26, 8, 30, 23, 0), TimeUtil.parse("2012-04-26 08:30:23"));
        assertEquals(TimeUtil.time(8, 30, 23, 0), TimeUtil.parse("08:30:23"));
        assertEquals(TimeUtil.timestamp(2012, 3, 26, 8, 30, 23, 123000000), TimeUtil.parse("2012-04-26 08:30:23.123"));
        assertThrows(SyntaxError.class, () -> TimeUtil.parse("2020/03/01"));
        assertThrows(ConversionException.class, () -> TimeUtil.parse("-"));
        assertThrows(IllegalArgumentException.class, () -> TimeUtil.parse("Date Or Time Spec"));
        assertNull(TimeUtil.parse(""));
        assertThrows(SyntaxError.class, () -> TimeUtil.parse("."));
        assertThrows(ConversionException.class, () -> TimeUtil.parse(":"));
    }

    @Test
    public void testIndexOfDate() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date searchedDate = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0, TimeUtil.indexOfDate(searchedDate,
                new Date[]{Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())}));
    }

    @Test
    public void testIndexOfDate2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(0, 1, 1).atStartOfDay();
        Date searchedDate = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0, TimeUtil.indexOfDate(searchedDate,
                new Date[]{Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())}));
    }

    @Test
    public void testMonthOf() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Month actualMonthOfResult = TimeUtil
                .monthOf(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(0, actualMonthOfResult.month);
        assertEquals(1970, actualMonthOfResult.year);
    }

    @Test
    public void testThisMonth() {
        Month actualThisMonthResult = TimeUtil.thisMonth();
        assertEquals(0, actualThisMonthResult.month);
        assertEquals(2021, actualThisMonthResult.year);
    }

    @Test
    public void testNextMonth() {
        Month actualNextMonthResult = TimeUtil.nextMonth();
        assertEquals(1, actualNextMonthResult.month);
        assertEquals(2021, actualNextMonthResult.year);
    }

    @Test
    public void testDaysBetween() {
        assertEquals(1, TimeUtil.daysBetween(TimeUtil.date(1975, 6, 31), TimeUtil.date(1975, 7, 1)));
    }

    @Test
    public void testDaysBetween2() {
        GregorianCalendar fromCalendar = new GregorianCalendar(1, 1, 1);
        assertEquals(0, TimeUtil.daysBetween(fromCalendar, new GregorianCalendar(1, 1, 1)));
    }

    @Test
    public void testDaysBetween3() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date from = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(0,
                TimeUtil.daysBetween(from, Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testJulianDay() {
        // simple test
        assertEquals(TimeUtil.julianDay(2014, 1, 1) + 1, TimeUtil.julianDay(2014, 1, 2));
        // year end
        assertEquals(TimeUtil.julianDay(2013, 12, 31) + 1, TimeUtil.julianDay(2014, 1, 1));
        // Feb 29 in a non-leap year
        assertEquals(TimeUtil.julianDay(2014, 2, 28) + 1, TimeUtil.julianDay(2014, 3, 1));
        // Feb 29 in a leap year
        assertEquals(TimeUtil.julianDay(2012, 2, 28) + 1, TimeUtil.julianDay(2012, 2, 29));
        assertEquals(TimeUtil.julianDay(2012, 2, 28) + 2, TimeUtil.julianDay(2012, 3, 1));
        // Feb 29 in 100-rule-non-leap year
        assertEquals(TimeUtil.julianDay(1900, 2, 28) + 1, TimeUtil.julianDay(1900, 3, 1));
        // Feb 29 in 400-rule-leap year
        assertEquals(TimeUtil.julianDay(2000, 2, 28) + 1, TimeUtil.julianDay(2000, 2, 29));
        assertEquals(TimeUtil.julianDay(2000, 2, 28) + 2, TimeUtil.julianDay(2000, 3, 1));
        assertEquals(1721426, TimeUtil.julianDay(1, 1, 1));
        assertEquals(1721457, TimeUtil.julianDay(new GregorianCalendar(1, 1, 1)));
        assertEquals(1854405, TimeUtil.julianDay(new GregorianCalendar(365, 1, 1)));
    }

    @Test
    public void testJulianDay2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(2440588, TimeUtil.julianDay(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testJulianDay3() {
        LocalDateTime atStartOfDayResult = LocalDate.of(365, 1, 1).atStartOfDay();
        assertEquals(1854373, TimeUtil.julianDay(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testMillis() {
        assertEquals(-62133091139000L, TimeUtil.millis(1, 1, 1, 1, 1, 1));
    }

    @Test
    public void testIndexOf() {
        Date date0 = TimeUtil.date(2015, 3, 5);
        Date date1 = TimeUtil.date(2015, 3, 7);
        Date date2 = TimeUtil.date(2015, 3, 9);
        Date[] array = new Date[]{date0, date1, date2};
        // test existing dates
        assertEquals(0, TimeUtil.indexOfDate(date0, array));
        assertEquals(1, TimeUtil.indexOfDate(date1, array));
        assertEquals(2, TimeUtil.indexOfDate(date2, array));
        // test earlier dates
        assertEquals(0, TimeUtil.indexOfDate(TimeUtil.date(2015, 3, 4), array));
        // test later dates
        assertEquals(2, TimeUtil.indexOfDate(TimeUtil.date(2015, 3, 10), array));
        // test date gaps
        assertEquals(1, TimeUtil.indexOfDate(TimeUtil.date(2015, 3, 6), array));
        assertEquals(2, TimeUtil.indexOfDate(TimeUtil.date(2015, 3, 8), array));
    }

    @Test
    public void testNthDayOfWeekInMonth() {
        assertEquals(TimeUtil.date(2017, 11, 15), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 11, 2017));
        assertEquals(TimeUtil.date(2018, 0, 19), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 0, 2018));
        assertEquals(TimeUtil.date(2018, 1, 16), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 1, 2018));
        assertEquals(TimeUtil.date(2018, 2, 16), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 2, 2018));
        assertEquals(TimeUtil.date(2018, 3, 20), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 3, 2018));
        assertEquals(TimeUtil.date(2018, 4, 18), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 4, 2018));
        assertEquals(TimeUtil.date(2018, 5, 15), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 5, 2018));
        assertEquals(TimeUtil.date(2018, 6, 20), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 6, 2018));
        assertEquals(TimeUtil.date(2018, 7, 17), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 7, 2018));
        assertEquals(TimeUtil.date(2018, 8, 21), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 8, 2018));
        assertEquals(TimeUtil.date(2018, 9, 19), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 9, 2018));
        assertEquals(TimeUtil.date(2018, 10, 16), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 10, 2018));
        assertEquals(TimeUtil.date(2018, 11, 21), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 11, 2018));
    }

    // helpers ---------------------------------------------------------------------------------------------------------

}

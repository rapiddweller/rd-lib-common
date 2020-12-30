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
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Tests the {@link TimeUtil} class.
 * Created: 17.02.2005 21:19:01
 * @since 0.1
 * @author Volker Bergmann
 */
public class TimeUtilTest {
	
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
	}
	
	@Test
	public void testFirstDayOfWeek() {
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 26)));
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 27)));
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 28)));
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 29)));
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 30)));
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 7, 31)));
		assertEquals(TimeUtil.date(2013, 7, 26), TimeUtil.firstDayOfWeek(TimeUtil.date(2013, 8,  1)));
	}
	
	@Test
	public void testLastDayOfWeek() {
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 26)));
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 27)));
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 28)));
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 29)));
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 30)));
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 7, 31)));
		assertEquals(TimeUtil.date(2013, 8, 1), TimeUtil.lastDayOfWeek(TimeUtil.date(2013, 8,  1)));
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
		assertEquals(TimeUtil.date(1970, 0,  1), TimeUtil.addMonths(TimeUtil.date(1970, 0,  1),  0));
		// simple addition
		assertEquals(TimeUtil.date(1970, 3,  1), TimeUtil.addMonths(TimeUtil.date(1970, 1,  1),  2));
		// 31. of the moth + n months -> month's end (31/30/29/28)
		assertEquals(TimeUtil.date(2008, 3, 30), TimeUtil.addMonths(TimeUtil.date(2008, 0, 31),  3));
		// over year's end
		assertEquals(TimeUtil.date(2011, 1, 28), TimeUtil.addMonths(TimeUtil.date(2009, 1, 28), 24));
		// over year's end + leap year
		assertEquals(TimeUtil.date(2009, 1, 28), TimeUtil.addMonths(TimeUtil.date(2008, 1, 29), 12));
	}
	
	@Test
	public void testAddYears() {
		// adding 0
		assertEquals(TimeUtil.date(1970, 0,  1), TimeUtil.addYears(TimeUtil.date(1970, 0,  1),  0));
		// simple addition
		assertEquals(TimeUtil.date(1972, 1,  1), TimeUtil.addYears(TimeUtil.date(1970, 1,  1),  2));
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
    public void testDate() {
    	Date date = TimeUtil.date(2009, 6, 19);
    	Calendar calendar = new GregorianCalendar(2009, 6, 19);
    	assertEquals(calendar.getTime(), date);
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
	public void testYearsBetween() {
		Date base = TimeUtil.date(2008, 2, 24);
		Date threeLater = TimeUtil.date(2011, 2, 24);
		assertEquals(3, TimeUtil.yearsBetween(base, threeLater));
		Date lessThanThreeLater = TimeUtil.date(2011, 2, 23);
		assertEquals(2, TimeUtil.yearsBetween(base, lessThanThreeLater));
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
		assertEquals("25:00:00 h", TimeUtil.formatDuration(25*3600000, false, false));
		assertEquals("25:00:00.000 h", TimeUtil.formatDuration(25*3600000, false, true));
		
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
	}
	
	@Test
	public void testDaysBetween() {
		assertEquals(1, TimeUtil.daysBetween(TimeUtil.date(1975, 6, 31), TimeUtil.date(1975, 7, 1)));
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
		assertEquals(TimeUtil.julianDay(1900, 2, 28) + 1, TimeUtil.julianDay(1900, 3,  1));
		// Feb 29 in 400-rule-leap year
		assertEquals(TimeUtil.julianDay(2000, 2, 28) + 1, TimeUtil.julianDay(2000, 2, 29));
		assertEquals(TimeUtil.julianDay(2000, 2, 28) + 2, TimeUtil.julianDay(2000, 3,  1));
	}
	
	@Test
	public void testIndexOf() {
		Date date0 = TimeUtil.date(2015, 3, 5);
		Date date1 = TimeUtil.date(2015, 3, 7);
		Date date2 = TimeUtil.date(2015, 3, 9);
		Date[] array = new Date[] { date0, date1, date2 };
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
		assertEquals(TimeUtil.date(2018,  0, 19), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  0, 2018));
		assertEquals(TimeUtil.date(2018,  1, 16), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  1, 2018));
		assertEquals(TimeUtil.date(2018,  2, 16), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  2, 2018));
		assertEquals(TimeUtil.date(2018,  3, 20), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  3, 2018));
		assertEquals(TimeUtil.date(2018,  4, 18), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  4, 2018));
		assertEquals(TimeUtil.date(2018,  5, 15), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  5, 2018));
		assertEquals(TimeUtil.date(2018,  6, 20), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  6, 2018));
		assertEquals(TimeUtil.date(2018,  7, 17), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  7, 2018));
		assertEquals(TimeUtil.date(2018,  8, 21), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  8, 2018));
		assertEquals(TimeUtil.date(2018,  9, 19), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY,  9, 2018));
		assertEquals(TimeUtil.date(2018, 10, 16), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 10, 2018));
		assertEquals(TimeUtil.date(2018, 11, 21), TimeUtil.nthDayOfWeekInMonth(3, Calendar.FRIDAY, 11, 2018));
	}
	
	// helpers ---------------------------------------------------------------------------------------------------------
	
}

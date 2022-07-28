/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.time;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Tests the DateDuration.<br/><br/>
 * Created: 28.07.2022 10:02:40
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DateDurationTest {

	@Test
	public void testParsing_non_zero() {
		assertYMD(1, 2, 3, DateDuration.of("0001-02-03"));
	}

	@Test
	public void testParsing_zero_month_and_day() {
		assertYMD(2, 0, 0, DateDuration.of("0002-00-00"));
	}

	@Test
	public void testParsing_many_days() {
		assertYMD(0, 0, 189, DateDuration.of("0000-00-189"));
	}

	@Test
	public void testCountBetween_days() {
		LocalDate date0 = LocalDate.of(2020, 1, 1);
		LocalDate date1 = LocalDate.of(2020, 1, 2);
		LocalDate date2 = LocalDate.of(2020, 2, 2);
		DateDuration duration1 = DateDuration.of("0000-00-01");
		assertEquals(1, duration1.countBetween(date0, date1));
		assertEquals(32, duration1.countBetween(date0, date2));
		assertEquals(31, duration1.countBetween(date1, date2));
		DateDuration duration2 = DateDuration.of("0000-00-02");
		assertEquals(0, duration2.countBetween(date0, date1));
		assertEquals(16, duration2.countBetween(date0, date2));
		assertEquals(15, duration2.countBetween(date1, date2));
	}

	@Test
	public void testCountBetween_months_and_days() {
		LocalDate date0 = LocalDate.of(2020, 1, 1);
		LocalDate date1 = LocalDate.of(2020, 3, 1);
		LocalDate date2 = LocalDate.of(2020, 3, 2);
		LocalDate date3 = LocalDate.of(2020, 7, 4);
		DateDuration duration1 = DateDuration.of("0000-02-00");
		assertEquals(1, duration1.countBetween(date0, date1));
		assertEquals(1, duration1.countBetween(date0, date2));
		assertEquals(0, duration1.countBetween(date1, date2));
		assertEquals(3, duration1.countBetween(date0, date3));
		DateDuration duration2 = DateDuration.of("0000-02-01");
		assertEquals(0, duration2.countBetween(date0, date1));
		assertEquals(1, duration2.countBetween(date0, date2));
		assertEquals(0, duration2.countBetween(date1, date2));
		assertEquals(3, duration2.countBetween(date0, date3));
	}

	@Test
	public void testCountBetween_all_fields() {
		LocalDate d0 = LocalDate.of(2020, 1, 1);
		LocalDate d1 = LocalDate.of(2021, 3, 3);
		LocalDate d2 = LocalDate.of(2021, 3, 4);
		LocalDate d3 = LocalDate.of(2022, 5, 7);
		DateDuration dd = DateDuration.of("0001-02-03");
		assertEquals(0, dd.countBetween(d0, d1));
		assertEquals(1, dd.countBetween(d0, d2));
		assertEquals(0, dd.countBetween(d1, d2));
		assertEquals(2, dd.countBetween(d0, d3));
	}

	@Test
	public void testToString() {
		assertEquals("DateDuration{}", DateDuration.of(0, 0, 0).toString());
		assertEquals("DateDuration{1 day}", DateDuration.of(0, 0, 1).toString());
		assertEquals("DateDuration{2 days}", DateDuration.of(0, 0, 2).toString());
		assertEquals("DateDuration{1 month}", DateDuration.of(0, 1, 0).toString());
		assertEquals("DateDuration{2 months}", DateDuration.of(0, 2, 0).toString());
		assertEquals("DateDuration{2 months, 1 day}", DateDuration.of(0, 2, 1).toString());
		assertEquals("DateDuration{1 year}", DateDuration.of(1, 0, 0).toString());
		assertEquals("DateDuration{3 years}", DateDuration.of(3, 0, 0).toString());
		assertEquals("DateDuration{3 years, 1 day}", DateDuration.of(3, 0, 1).toString());
		assertEquals("DateDuration{3 years, 2 months}", DateDuration.of(3, 2, 0).toString());
		assertEquals("DateDuration{3 years, 2 months, 1 day}", DateDuration.of(3, 2, 1).toString());
	}

	private void assertYMD(int expYears, int expMonths, int expDays, DateDuration actDuration) {
		assertEquals(expYears, actDuration.getYears());
		assertEquals(expMonths, actDuration.getMonths());
		assertEquals(expDays, actDuration.getDays());
	}

}

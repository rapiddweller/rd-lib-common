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

package com.rapiddweller.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.Test;

/**
 * Tests the {@link JavaTimeUtil}.<br><br>
 * Created: 19.11.2019 12:53:42
 * @since 1.0
 * @author Volker Bergmann
 */

public class JavaTimeUtilTest {
	
	@Test
	public void test() {
		assertEquals(3, JavaTimeUtil.numberOfDayOfWeekInMonth(ld("2019-11-19")));
		assertEquals(1, JavaTimeUtil.numberOfDayOfWeekInMonth(ld("2019-11-05")));
		assertEquals(4, JavaTimeUtil.numberOfDayOfWeekInMonth(ld("2019-11-26")));
		assertEquals(1, JavaTimeUtil.numberOfDayOfWeekInMonth(ld("2019-11-01")));
		assertEquals(5, JavaTimeUtil.numberOfDayOfWeekInMonth(ld("2019-11-30")));
	}
	
	@Test
	public void testRelativeDayOfMonth() {
		assertEquals( 13, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 13)));
		assertEquals( 14, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 14)));
		assertEquals( 15, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 15)));
		assertEquals(-12, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 16)));
		assertEquals( -1, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 27)));
		assertEquals(  0, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 28)));
		assertEquals(  1, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3,  1)));
		assertEquals(  4, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3,  4)));
		assertEquals( 14, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 14)));
		assertEquals( 15, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 15)));
		assertEquals(-15, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 16)));
	}
	
	@Test
	public void testIsLastWeekdayOfTypeInMonth() {
		assertFalse(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, ld("2013-05-19"))); // Sunday
		assertFalse(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, ld("2013-05-20"))); // 2nd last Monday
		assertTrue(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, ld("2013-05-27")));  // last Monday
	}
	
	private static LocalDate ld(String spec) {
		return LocalDate.parse(spec);
	}
	
}

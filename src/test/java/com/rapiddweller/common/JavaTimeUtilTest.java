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

import com.rapiddweller.common.exception.IllegalArgumentError;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link JavaTimeUtil}.<br><br>
 * Created: 19.11.2019 12:53:42
 *
 * @author Volker Bergmann
 * @since 1.0
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
    assertEquals(13, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 13)));
    assertEquals(14, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 14)));
    assertEquals(15, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 15)));
    assertEquals(-12, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 16)));
    assertEquals(-1, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 27)));
    assertEquals(0, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 2, 28)));
    assertEquals(1, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 1)));
    assertEquals(4, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 4)));
    assertEquals(14, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 14)));
    assertEquals(15, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 15)));
    assertEquals(-15, JavaTimeUtil.relativeDayOfMonth(LocalDate.of(2019, 3, 16)));
    assertEquals(2, JavaTimeUtil.relativeDayOfMonth(LocalDate.ofEpochDay(1L)));
    assertEquals(-15, JavaTimeUtil.relativeDayOfMonth(LocalDate.ofEpochDay(15L)));
  }

  @Test
  public void testParseLocalDate() {
    assertNull(JavaTimeUtil.parseLocalDate(null));
  }

  @Test
  public void testIsLastWeekdayOfTypeInMonth() {
    assertFalse(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, ld("2013-05-19"))); // Sunday
    assertFalse(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, ld("2013-05-20"))); // 2nd last Monday
    assertTrue(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, ld("2013-05-27")));  // last Monday
    assertFalse(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.MONDAY, LocalDate.ofEpochDay(1L)));
    assertFalse(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.FRIDAY, LocalDate.ofEpochDay(1L)));
    assertTrue(JavaTimeUtil.isLastWeekdayOfTypeInMonth(DayOfWeek.WEDNESDAY, LocalDate.ofEpochDay(-1L)));
  }

  @Test
  public void testParseDayOfWeek() {
    assertThrows(IllegalArgumentError.class, () -> JavaTimeUtil.parseDayOfWeek("Weekday Spec"));
    assertEquals(DayOfWeek.SATURDAY, JavaTimeUtil.parseDayOfWeek("SAT"));
  }

  @Test
  public void testJulianDay() {
    assertEquals(2440589, JavaTimeUtil.julianDay(LocalDate.ofEpochDay(1L)));
    assertEquals(2440592, JavaTimeUtil.julianDay(LocalDate.ofEpochDay(4L)));
  }

  @Test
  public void testMillisSinceEpoch() {
    assertEquals(86400000L, JavaTimeUtil.millisSinceEpoch(LocalDate.ofEpochDay(1L)));
  }

  @Test
  public void testMillisSinceEpoch2() {
    LocalDateTime localDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
    ZoneOffset offset = ZoneOffset.ofTotalSeconds(1);
    assertEquals(-62135593141000L, JavaTimeUtil.millisSinceEpoch(
        ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(1)))));
  }

  @Test
  public void testMillisSinceEpoch3() {
    LocalDateTime localDateTime = LocalDateTime.of(0, 1, 1, 1, 1);
    ZoneOffset offset = ZoneOffset.ofTotalSeconds(1);
    assertEquals(-62167215541000L, JavaTimeUtil.millisSinceEpoch(
        ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(1)))));
  }

  @Test
  public void testToDate() {
    assertNull(JavaTimeUtil.toDate((ZonedDateTime) null));
  }

  @Test
  public void testToZonedDateTime() {
    assertNull(JavaTimeUtil.toZonedDateTime(null));
  }

  @Test
  public void testToCalendar() {
    assertTrue(JavaTimeUtil.toCalendar(LocalDate.ofEpochDay(1L)) instanceof java.util.GregorianCalendar);
  }

  @Test
  public void testGranularityMillis() {
    assertThrows(IllegalArgumentError.class, () -> JavaTimeUtil.granularityMillis(ChronoUnit.NANOS));
    assertEquals(1000, JavaTimeUtil.granularityMillis(ChronoUnit.SECONDS));
    assertEquals(60000, JavaTimeUtil.granularityMillis(ChronoUnit.MINUTES));
    assertEquals(3600000, JavaTimeUtil.granularityMillis(ChronoUnit.HOURS));
    assertEquals(86400000, JavaTimeUtil.granularityMillis(ChronoUnit.DAYS));
  }

  @Test
  public void testTimeToInt() {
    assertEquals(101, JavaTimeUtil.timeToInt(LocalTime.of(1, 1)));
  }

  @Test
  public void testDaysBetween() {
    assertEquals(0, JavaTimeUtil.daysBetween(LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L)));
    assertEquals(-1, JavaTimeUtil.daysBetween(LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(0L)));
  }

  @Test
  public void testFormat() {
    assertEquals("", JavaTimeUtil.format(LocalDate.ofEpochDay(1L), ""));
    assertEquals("", JavaTimeUtil.format(LocalDateTime.of(1, 1, 1, 1, 1), ""));
    assertEquals("01:01", JavaTimeUtil.format(LocalTime.of(1, 1)));
  }

  @Test
  public void testNumberOfDayOfWeekInMonth() {
    assertEquals(1, JavaTimeUtil.numberOfDayOfWeekInMonth(LocalDate.ofEpochDay(1L)));
  }

  @Test
  public void testDayOfWeekInMonthId() {
    assertEquals(15, JavaTimeUtil.dayOfWeekInMonthId(LocalDate.ofEpochDay(1L)));
    assertEquals(24, JavaTimeUtil.dayOfWeekInMonthId(LocalDate.ofEpochDay(7L)));
  }

  private static LocalDate ld(String spec) {
    return LocalDate.parse(spec);
  }

}

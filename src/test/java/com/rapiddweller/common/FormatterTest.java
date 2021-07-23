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

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the Formatter.
 * Created: 23.12.2013 09:07:11
 *
 * @author Volker Bergmann
 * @since 0.5.25
 */

public class FormatterTest {

  @Test
  public void testFormatPercentage() {
    assertNotNull(Formatter.formatPercentage(10.0));
  }

  @Test
  public void testFormatPercentalChange() {
    assertNotNull(Formatter.formatPercentalChange(10.0));
    assertNotNull(Formatter.formatPercentalChange(10.0, 1));
  }

  @Test
  public void testFormat() {
    assertNotNull(Formatter.format(10.0));
    assertNotNull(Formatter.format(Double.NaN));
    assertEquals("10", Formatter.format(10.0, 3));
    assertNotNull(Formatter.format(10.0, new Locale("en")));
    assertEquals("NaN", Formatter.format(Double.NaN, new Locale("en")));
    assertEquals("NaN", Formatter.format(Double.NaN, new Locale("NaN")));
    assertNotNull(Formatter.format(LocalDate.ofEpochDay(1L)));
    assertNull(Formatter.format((LocalDate) null));
    assertEquals("null", Formatter.format((Date) null));
    assertEquals("null", Formatter.format(null, "Pattern"));
  }

  @Test
  public void testFormat2() {
    LocalDateTime localDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
    ZoneOffset offset = ZoneOffset.ofTotalSeconds(1);
    assertEquals("0001-01-01 01:01:00", Formatter
        .format(ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(1)))));
  }

  @Test
  public void testFormat3() {
    LocalDateTime localDateTime = LocalDateTime.of(0, 1, 1, 1, 1);
    ZoneOffset offset = ZoneOffset.ofTotalSeconds(1);
    assertEquals("0001-01-01 01:01:00", Formatter
        .format(ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(1)))));
  }

  @Test
  public void testFormat4() {
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    assertEquals("1970-01-01",
        Formatter.format(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
  }

  @Test
  public void testFormat5() {
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    assertEquals("#,##0.00",
        Formatter.format(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()), "#,##0.00"));
  }

  @Test
  public void testFormatLocal() {
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    assertNotNull(Formatter.formatLocal(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
  }

  @Test
  public void testFormatLocal2() {
    assertEquals("null", Formatter.formatLocal(null));
  }


  @Test
  public void testFormatDate() {
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    assertEquals("1970-01-01",
        Formatter.formatDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
  }

  @Test
  public void testFormatDate2() {
    assertEquals("null", Formatter.formatDate(null));
  }

  @Test
  public void testFormatDateTime() {
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    assertEquals("1970-01-01 00:00:00",
        Formatter.formatDateTime(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
  }

  @Test
  public void testFormatDateTime2() {
    assertEquals("null", Formatter.formatDateTime(null));
  }

  @Test
  public void testFormatTime() {
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    assertEquals("00:00:00",
        Formatter.formatTime(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
  }

  @Test
  public void testFormatTime2() {
    assertEquals("null", Formatter.formatTime(null));
  }

  @Test
  public void test_DE() {
    LocaleUtil.runInLocale(Locale.GERMAN, () -> {
      assertEquals("morgen", Formatter.formatDaysFromNow(TimeUtil.tomorrow()));
      assertEquals("gestern", Formatter.formatDaysFromNow(TimeUtil.yesterday()));
      assertEquals("heute", Formatter.formatDaysFromNow(TimeUtil.today()));
      assertEquals("übermorgen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 2)));
      assertEquals("vorgestern", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -2)));
      assertEquals("vor 3 Tagen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -3)));
      assertEquals("in 3 Tagen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 3)));
    });
  }

  @Test
  public void test_EN() {
    LocaleUtil.runInLocale(Locale.ENGLISH, () -> {
      assertEquals("tomorrow", Formatter.formatDaysFromNow(TimeUtil.tomorrow()));
      assertEquals("yesterday", Formatter.formatDaysFromNow(TimeUtil.yesterday()));
      assertEquals("today", Formatter.formatDaysFromNow(TimeUtil.today()));
      assertEquals("the day after tomorrow", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 2)));
      assertEquals("the day before yesterday", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -2)));
      assertEquals("3 days ago", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -3)));
      assertEquals("in 3 days", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 3)));
    });
  }

}

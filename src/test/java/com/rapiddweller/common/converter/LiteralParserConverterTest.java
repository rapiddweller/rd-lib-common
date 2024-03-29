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

package com.rapiddweller.common.converter;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LiteralParserConverter}.
 * Created: 20.03.2008 07:18:30
 *
 * @author Volker Bergmann
 */
public class LiteralParserConverterTest extends AbstractConverterTest {

  public LiteralParserConverterTest() {
    super(LiteralParserConverter.class);
  }

  @Test
  public void testNull() {
    assertNull(LiteralParserConverter.parse(null));
    assertNull(LiteralParserConverter.parse(""));
    assertEquals(" \t ", LiteralParserConverter.parse(" \t "));
  }

  @Test
  public void testBoolean() {
    assertEquals(Boolean.TRUE, LiteralParserConverter.parse("true"));
    assertEquals(Boolean.TRUE, LiteralParserConverter.parse(" true "));
    assertEquals(Boolean.FALSE, LiteralParserConverter.parse("false"));
    assertEquals(Boolean.FALSE, LiteralParserConverter.parse(" false "));
  }

  @Test
  public void testString() {
    checkText("Alpha");
    checkText("'1'");
    checkText("\"2\"");
    checkText("7 days");
    checkText("1.2.3.4");
    checkText("2000:");
    checkText("1 2 3 4");
    checkText("01234");
  }

  @Test
  public void testIllegalBooleansAsString() {
    checkText("true or false");
    checkText("True");
    checkText("TRUE");
  }

  @Test
  public void testIllegalDateTimesAsString() {
    checkText("01.02.");
    checkText("2000-");
    checkText("2000-00");
    checkText("2000-00-");
    checkText("2000-01");
    checkText("2000-01:");
    checkText("2000-01-");
    checkText("2000-01-00");
    checkText("2000-01-01-");
    checkText("2000-01-01T");
    checkText("2000-01-01TT");
    checkText("2000-01-01T00");
    checkText("2000-01-01T00-");
    checkText("2000-01-01T00:T");
    checkText("2000-01-01T00:00-");
    checkText("2000-01-01T00:00:-");
    checkText("2000-01-01T00:00:00T");
    checkText("2000-01-01T00:00:00.");
    checkText("2000-01-01T00:00:00.T");
    checkText("2000-01-01T00:00:00.123T");
  }

  @Test
  public void testInteger() {
    assertEquals(0, LiteralParserConverter.parse("0"));
    assertEquals(1, LiteralParserConverter.parse("1"));
    assertEquals(-1, LiteralParserConverter.parse("-1"));
  }

  @Test
  public void testLong() {
    checkLong(Long.MAX_VALUE);
    checkLong(((long) Integer.MAX_VALUE) + 1);
    checkLong(((long) Integer.MIN_VALUE) - 1);
    checkLong(Long.MIN_VALUE + 1);
    assertEquals(20211101154530L, LiteralParserConverter.parse("20211101154530"));
  }

  @Test
  public void testDouble() {
    assertEquals(0., LiteralParserConverter.parse("0.0"));
    assertEquals(1., LiteralParserConverter.parse("1."));
    assertEquals(1., LiteralParserConverter.parse("1.0"));
    assertEquals(-1., LiteralParserConverter.parse("-1."));
    assertEquals(-1., LiteralParserConverter.parse("-1.0"));
  }

  @Test
  public void testDate() throws ParseException {
    checkDate("1970-3-2", "yyyy-MM-dd");
    checkDate("1970-01-01", "yyyy-MM-dd");
    checkDate("1970-01-02", "yyyy-MM-dd");
    checkDate("1969-12-31", "yyyy-MM-dd");
    checkDate("1970-01-01T00:01", "yyyy-MM-dd'T'HH:mm");
    checkDate("1970-01-02T00:01", "yyyy-MM-dd'T'HH:mm");
    checkDate("1969-12-31T23:59", "yyyy-MM-dd'T'HH:mm");
    checkDate("1970-01-01T00:01:01", "yyyy-MM-dd'T'HH:mm:ss");
    checkDate("1970-01-02T00:01:01", "yyyy-MM-dd'T'HH:mm:ss");
    checkDate("1969-12-31T23:59:59", "yyyy-MM-dd'T'HH:mm:ss");
    checkDate("1970-01-01T00:01:01.123", "yyyy-MM-dd'T'HH:mm:ss.SSS");
    checkDate("1970-01-02T00:01:01.123", "yyyy-MM-dd'T'HH:mm:ss.SSS");
    checkDate("1969-12-31T23:59:59.123", "yyyy-MM-dd'T'HH:mm:ss.SSS");
    checkDate("1969-3-2T1:3:4.123", "yyyy-MM-dd'T'HH:mm:ss.SSS");
  }

  @Test
  public void testTime() throws ParseException {
    checkTime("00:01", "HH:mm");
    checkTime("00:01", "HH:mm");
    checkTime("23:59", "HH:mm");
    checkTime("00:01:01", "HH:mm:ss");
    checkTime("00:01:01", "HH:mm:ss");
    checkTime("23:59:59", "HH:mm:ss");
    checkTime("00:01:01.123", "HH:mm:ss.SSS");
    checkTime("00:01:01.123", "HH:mm:ss.SSS");
    checkTime("23:59:59.123", "HH:mm:ss.SSS");
    checkTime("1:3:4.123", "HH:mm:ss.SSS");
  }

  // private helper methods ------------------------------------------------------------------------------------------

  private static void checkText(String text) {
    assertEquals(text, LiteralParserConverter.parse(text));
  }

  private static void checkLong(long value) {
    assertEquals(value, LiteralParserConverter.parse(String.valueOf(value)));
  }

  private static void checkDate(String dateString, String pattern) throws ParseException {
    SimpleDateFormat f = new SimpleDateFormat(pattern);
    Date date = f.parse(dateString);
    assertEquals(date, LiteralParserConverter.parse(dateString));
  }

  private static void checkTime(String dateString, String pattern) throws ParseException {
    SimpleDateFormat f = new SimpleDateFormat(pattern);
    Date date = f.parse(dateString);
    assertEquals(date, LiteralParserConverter.parse(dateString));
  }

}

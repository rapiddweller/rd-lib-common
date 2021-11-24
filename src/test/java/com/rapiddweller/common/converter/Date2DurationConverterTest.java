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

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Period;
import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link Date2DurationConverter}.
 * Created at 11.01.2009 06:44:42
 *
 * @author Volker Bergmann
 * @since 0.5.7
 */

public class Date2DurationConverterTest extends AbstractConverterTest {

  public Date2DurationConverterTest() {
    super(Date2DurationConverter.class);
  }

  @Test
  public void testUK() throws Exception {
    check(TimeUtil.GMT);
  }

  @Test
  public void testGermany() throws Exception {
    check(TimeUtil.CENTRAL_EUROPEAN_TIME);
  }

  @Test
  public void testSingapore() throws Exception {
    check(TimeUtil.SINGAPORE_TIME);
  }

  @Test
  public void testCalifornia() throws Exception {
    check(TimeUtil.PACIFIC_STANDARD_TIME);
  }

  private static void check(TimeZone timeZone) throws Exception {
    TimeUtil.callInTimeZone(timeZone, () -> {
      assertEquals(1L, convert("1970-01-01T00:00:00.001").longValue());
      assertEquals(0L, convert("0000-00-00T00:00:00.000").longValue());
      assertEquals(1L, convert("0000-00-00T00:00:00.001").longValue());
      assertEquals(Period.DAY.getMillis(), convert("0000-00-01T00:00:00.000").longValue());
      assertEquals(Period.DAY.getMillis() * 50, convert("0000-00-50T00:00:00.000").longValue());
      return null;
    });
  }

  public static Long convert(String string) throws ConversionException, ParseException {
    return new Date2DurationConverter().convert(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(string));
  }

}

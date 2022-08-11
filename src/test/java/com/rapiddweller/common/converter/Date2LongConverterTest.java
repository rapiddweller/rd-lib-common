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
import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Date2LongConverter}.
 * Created at 05.01.2009 18:22:11
 * @author Volker Bergmann
 * @since 0.4.7
 */

public class Date2LongConverterTest extends AbstractDateConverterTest {

  public Date2LongConverterTest() {
    super(Date2LongConverter.class);
  }

  @Test
  public void testSetTimeZone() {
    Date2LongConverter date2LongConverter = new Date2LongConverter();
    date2LongConverter.setTimeZone(BERLIN_TZ);
    assertEquals(BERLIN_TZ, date2LongConverter.getTimeZone());
    date2LongConverter.setTimeZone(CHICAGO_TZ);
    assertEquals(CHICAGO_TZ, date2LongConverter.getTimeZone());
  }

  @Test
  public void testNullValueConversion() {
    assertNull(new Date2LongConverter().convert(null));
  }

  @Test
  public void testNullZoneConversion() throws ConversionException {
    TimeUtil.runInTimeZone(BERLIN_TZ,
        () -> assertEquals(EPOCH_MILLIS_BERLIN, (long) new Date2LongConverter(null).convert(DATE_BERLIN_DTZ)));
    TimeUtil.runInTimeZone(CHICAGO_TZ, () -> {
      Date DATE_CHICAGO = TimeUtil.date(2022, 6, 28, 6, 44, 58, 123);
      assertEquals(EPOCH_MILLIS_CHICAGO, (long) new Date2LongConverter(null).convert(DATE_CHICAGO));
    });
  }

  @Test
  public void testZonedConversion_berlin() throws ConversionException {
    assertEquals(EPOCH_MILLIS_BERLIN, (long) new Date2LongConverter(BERLIN).convert(DATE_BERLIN_DTZ));
  }

  @Test
  public void testZonedConversion_chicago() throws ConversionException {
    assertEquals(EPOCH_MILLIS_BERLIN, (long) new Date2LongConverter(CHICAGO).convert(DATE_CHICAGO_DTZ));
  }

}

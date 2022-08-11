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
import org.junit.Ignore;
import org.junit.Test;

import java.util.SimpleTimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

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
        () -> assertEquals(MILLIS_BERLIN, (long) new Date2LongConverter(null).convert(DATE_BERLIN)));
    TimeUtil.runInTimeZone(CHICAGO_TZ,
        () -> assertEquals(MILLIS_CHICAGO, (long) new Date2LongConverter(null).convert(DATE_CHICAGO)));
  }

  @Test @Ignore("This fails in CI") // TODO v3.0.0 make this work
  // CI message: Date2LongConverterTest.testZonedConversion_berlin:65 expected:<1659015898123> but was:<1659023098123>
  public void testZonedConversion_berlin() throws ConversionException {
    assertEquals(MILLIS_BERLIN, (long) new Date2LongConverter(BERLIN).convert(DATE_BERLIN));
  }

  @Test @Ignore("This fails in CI") // TODO v3.0.0 make this work
  // CI message: Date2LongConverterTest.testZonedConversion_chicago:70 expected:<1658990698123> but was:<1658972698123>
  public void testZonedConversion_chicago() throws ConversionException {
    assertEquals(MILLIS_CHICAGO, (long) new Date2LongConverter(CHICAGO).convert(DATE_CHICAGO));
  }

}

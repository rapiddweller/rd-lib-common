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
package com.rapiddweller.commons.converter;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.rapiddweller.commons.TimeUtil;
import com.rapiddweller.commons.converter.String2DateConverter;

/**
 * Tests the {@link String2DateConverter}.
 * Created: 07.09.2007 18:00:32
 * @author Volker Bergmann
 */
public class String2DateConverterTest extends AbstractConverterTest {

	public String2DateConverterTest() {
	    super(String2DateConverter.class);
    }

	@Test
    public void testExplicitConfig() {
		String2DateConverter<Date> usConverter = new String2DateConverter<>("dd-MMM-yyyy", Locale.US);
		assertEquals(TimeUtil.date(2010, 11, 31), usConverter.convert("31-DEC-2010"));
		String2DateConverter<Date> deConverter = new String2DateConverter<>("dd-MMM-yyyy", Locale.GERMANY);
		assertEquals(TimeUtil.date(2010, 11, 31), deConverter.convert("31-DEZ-2010"));
    }

	@Test
    public void testStandardDates() {
        String stringValue = "2007-09-06";
		assertEquals(TimeUtil.date(2007, 8, 6), convert(stringValue));
        assertEquals(TimeUtil.date(2007, 8, 6, 13, 28, 0, 0), convert("2007-09-06T13:28"));
        assertEquals(TimeUtil.date(2007, 8, 6, 13, 28, 56, 0), convert("2007-09-06T13:28:56"));
        assertEquals(TimeUtil.date(2007, 8, 6, 13, 28, 56, 123), convert("2007-09-06T13:28:56.123"));
    }

	@Test
    public void testStrangeDates() {
        assertEquals(null, convert(null));
        assertEquals(null, convert(""));
        assertEquals(TimeUtil.date(1234, 2, 5), convert("1234-3-5"));
        assertEquals(TimeUtil.date(12345, 11, 1), convert("12345-12-1"));
        assertEquals(TimeUtil.date(-10000, 3, 1), convert("-10000-4-1"));
    }

	private static Date convert(String stringValue) {
		return new String2DateConverter<>().convert(stringValue);
	}

}

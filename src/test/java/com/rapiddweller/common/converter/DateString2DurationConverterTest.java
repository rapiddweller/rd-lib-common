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

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import com.rapiddweller.common.Period;

import org.junit.Test;

/**
 * Tests the {@link DateString2DurationConverter}.
 * Created at 05.01.2009 18:39:46
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class DateString2DurationConverterTest extends AbstractConverterTest {
	
	public DateString2DurationConverterTest() {
	    super(DateString2DurationConverter.class);
    }

	@Test
	public void testSimple() {
		assertEquals(1L, convert("1970-01-01T00:00:00.001"));
		assertEquals(1L, convert("0000-00-00T00:00:00.001"));
		assertEquals(Period.DAY.getMillis(), convert("0000-00-01"));
		assertEquals(Period.DAY.getMillis() * 50, convert("0000-00-50"));
	}

	@Test
	public void testSG() throws Exception {
		TimeZone timeZone = TimeZone.getDefault();
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
			assertEquals(1L, convert("1970-01-01T00:00:00.001"));
			assertEquals(1L, convert("0000-00-00T00:00:00.001"));
			assertEquals(Period.DAY.getMillis(), convert("0000-00-01"));
			assertEquals(Period.DAY.getMillis() * 50, convert("0000-00-50"));
		} finally {
			TimeZone.setDefault(timeZone);
		}
	}

	private static long convert(String string) {
		return new DateString2DurationConverter().convert(string);
	}
	
}

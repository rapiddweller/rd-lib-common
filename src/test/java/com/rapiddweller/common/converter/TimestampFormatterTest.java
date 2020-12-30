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

import static org.junit.Assert.*;

import java.sql.Timestamp;

import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

/**
 * Tests the {@link TimestampFormatter}.
 * Created: 18.02.2010 17:54:24
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class TimestampFormatterTest extends AbstractConverterTest {
	
	Timestamp timestamp = TimeUtil.timestamp(1971, 1, 3, 13, 14, 15, 123456789);

	public TimestampFormatterTest() {
		super(TimestampFormatter.class);
	}

	@Test
	public void testDefaultFormat() {
		assertEquals("1971-02-03T13:14:15.123456789", new TimestampFormatter().format(timestamp));
	}

	@Test
	public void testMillisFormat() {
		assertEquals("1971-02-03 13:14:15.123", new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS").format(timestamp));
	}
	
	@Test
	public void testCentisFormat() {
		assertEquals("1971-02-03 13:14:15.123", new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS").format(timestamp));
	}
	
	@Test
	public void testNanosFormat() {
		assertEquals("1971-02-03 13:14:15.123456789", new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSSSSSSSS").format(timestamp));
	}
	
	@Test
	public void testSecondsFormat() {
		assertEquals("1971-02-03 13:14:15", new TimestampFormatter("yyyy-MM-dd HH:mm:ss").format(timestamp));
	}
	
	@Test
	public void testNull() {
		assertEquals(null, new TimestampFormatter().format(null));
	}
	
}

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
package com.rapiddweller.common.time;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

/**
 * Tests the {@link ElapsedTimeFormatter}.
 * Created: 14.12.2010 13:50:35
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class ElapsedTimeFormatterTest {

	@Test
	public void testGerman() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.GERMAN, " ", true);
		assertEquals("123 ms", format.convert(123L));
		assertEquals("1,2 s",  format.convert(1234L));
		assertEquals("1,3 s",  format.convert(1256L));
		assertEquals("3 s",    format.convert(2999L));
		assertEquals("12 s",   format.convert(12345L));
		assertEquals("2,1 min",  format.convert(123456L));
		assertEquals("3 min",    format.convert(180000L));
		assertEquals("3,4 Std",  format.convert(12345678L));
		assertEquals("1,4 d",  format.convert(123456789L));
	}
	
	@Test
	public void testUS() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.US, " ", true);
		assertEquals("123 ms", format.convert(123L));
		assertEquals("1.2 s",  format.convert(1234L));
		assertEquals("3.4 h",  format.convert(12345678L));
		assertEquals("1.4 d",  format.convert(123456789L));
	}
	
	@Test
	public void testHtml() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.US, "&nbsp;", true);
		assertEquals("123&nbsp;ms", format.convert(123L));
		assertEquals("1.2&nbsp;s",  format.convert(1234L));
	}
	
	@Test
	public void testGermanWithInternationalUnits() {
		ElapsedTimeFormatter format = new ElapsedTimeFormatter(Locale.GERMAN, " ", false);
		assertEquals("123 ms", format.convert(123L));
		assertEquals("3,4 h",  format.convert(12345678L));
	}
	
}

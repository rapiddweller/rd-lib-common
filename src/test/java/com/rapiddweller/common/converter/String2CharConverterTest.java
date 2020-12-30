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

import org.junit.Test;

/**
 * Tests the {@link String2CharConverter}.
 * Created: 29.07.2010 17:24:32
 * @since 0.6.3
 * @author Volker Bergmann
 */
public class String2CharConverterTest extends AbstractConverterTest {

	private static final String2CharConverter CONVERTER = new String2CharConverter();

	public String2CharConverterTest() {
		super(String2CharConverter.class);
	}

	@Test
	public void testTypes() {
		assertEquals(String.class, CONVERTER.getSourceType());
		assertEquals(Character.class, CONVERTER.getTargetType());
	}
	
	@Test
	public void testStandardConversions() {
		assertEquals('A', CONVERTER.convert("A").charValue());
		assertEquals('1', CONVERTER.convert("1").charValue());
	}
	
	@Test
	public void testNullConversion() {
		assertNull(CONVERTER.convert(null));
	}
	
}

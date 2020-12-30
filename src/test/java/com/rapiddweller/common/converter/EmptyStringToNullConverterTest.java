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

import com.rapiddweller.common.iterator.EmptyStringToNullConverter;
import org.junit.Test;

/**
 * Tests the {@link EmptyStringToNullConverter}.
 * Created: 08.03.2011 14:55:56
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class EmptyStringToNullConverterTest extends AbstractConverterTest {

	public EmptyStringToNullConverterTest() {
		super(EmptyStringToNullConverter.class);
	}

	@Test
	public void test() {
		EmptyStringToNullConverter converter = new EmptyStringToNullConverter();
		assertEquals(null, converter.convert(null));
		assertEquals(null, converter.convert(""));
		assertEquals("x", converter.convert("x"));
	}
	
}

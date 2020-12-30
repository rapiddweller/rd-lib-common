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
 * Tests the {@link RegexReplacer}.
 * Created: 22.02.2010 07:18:56
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class RegexReplacerTest extends AbstractConverterTest {

	public RegexReplacerTest() {
	    super(RegexReplacer.class);
    }

	@Test
	public void testConvertOneArg() {
		RegexReplacer replacer = new RegexReplacer("\\d", "x");
		assertEquals("AxBxCxD", replacer.convert("A1B2C3D"));
	}
	
	@Test
	public void testConvertTwoArg() {
		RegexReplacer replacer = new RegexReplacer("\\d", null);
		assertEquals("AxBxCxD", replacer.convert("A1B2C3D", "x"));
	}
	
	@Test
	public void testConvertStatic() {
		assertEquals("AxBxCxD", RegexReplacer.convert("A1B2C3D", "\\d", "x"));
	}
	
}

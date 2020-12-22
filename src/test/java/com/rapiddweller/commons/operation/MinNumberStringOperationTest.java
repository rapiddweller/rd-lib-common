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
package com.rapiddweller.commons.operation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link MinNumberStringOperation}.
 * Created: 26.02.2010 09:49:46
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class MinNumberStringOperationTest {

	private MinNumberStringOperation min;

	@Before
	public void setUp() {
		min = new MinNumberStringOperation();
	}
	
	@Test
	public void testInt() {
		assertEquals("1", min.perform("1", "2", "3"));
	}
	
	@Test
	public void testDouble() {
		assertEquals("1.", min.perform("1.", "2.", "3."));
	}
	
	@Test
	public void testFormatSustained() {
		assertEquals("1.000", min.perform("1.000", "2.", "3."));
	}
	
	@Test
	public void testMixed() {
		assertEquals("1.000", min.perform("1.000", "2"));
	}
	
}

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
package com.rapiddweller.common;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the {@link Assert} class.
 * Created at 06.05.2008 12:43:45
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class AssertTest {
	
	@Test
	public void testAssertEqualsStringArray() {
		String[] a1 = new String[] { "Alpha", "Beta" };
		expectNotEquals(a1, null);
		Assert.equals(a1, a1);
		Assert.equals(a1, new String[] { "Alpha", "Beta" });
		String[] a2 = new String[] { "Alpha" };
		expectNotEquals(a1, a2);
		expectNotEquals(a2, a1);
	}

	@Test
	public void testAssertEqualsByteArray() {
		byte[] a1 = new byte[] { 1, 2 };
		expectNotEquals(a1, null);
		Assert.equals(a1, a1);
		Assert.equals(a1, new byte[] { 1, 2 });
		byte[] a2 = new byte[] { 1 };
		expectNotEquals(a1, a2);
		expectNotEquals(a2, a1);
	}
	
	@Test
	public void testLessOrEqual() {
		Assert.lessOrEqual(0, 1, "number");
		Assert.lessOrEqual(1, 1, "number");
		try {
			Assert.lessOrEqual(2, 1, "number");
			fail("AssertionError expected");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private static void expectNotEquals(String[] a1, String[] a2) {
		try {
			Assert.equals(a1, a2);
			fail("AssertionError expected");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	private static void expectNotEquals(byte[] a1, byte[] a2) {
		try {
			Assert.equals(a1, a2);
			fail("AssertionError expected");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

}

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
package com.rapiddweller.commons.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * Tests the {@link NumberComparator}.
 * Created at 29.04.2008 18:30:06
 * @since 0.4.2
 * @author Volker Bergmann
 */
public class NumberComparatorTest {
	
	NumberComparator<Number> comparator = new NumberComparator<>();

	@Test
	public void testNull() {
		expectExceptionFor(null, 0);
		expectExceptionFor(0, null);
		expectExceptionFor(null, null);
	}
	
	@Test
	public void testByte() {
		expectEquality((byte)1, (byte)1);
		expectLess((byte)1, (byte)2);
		expectGreater((byte)2, (byte)1);
	}

	@Test
	public void testShort() {
		expectEquality((short)1, (short)1);
		expectLess((short)1, (short)2);
		expectGreater((short)2, (short)1);
	}

	@Test
	public void testInteger() {
		expectEquality(1, 1);
		expectLess(1, 2);
		expectGreater(2, 1);
	}

	@Test
	public void testLong() {
		expectEquality(1L, 1L);
		expectLess(1L, 2L);
		expectGreater(2L, 1L);
	}
	
	@Test
	public void testFloat() {
		expectEquality((float)1, (float)1);
		expectLess((float)1, (float)2);
		expectGreater((float)2, (float)1);
	}
	
	@Test
	public void testDouble() {
		expectEquality(1., 1.);
		expectLess(1., 2.);
		expectGreater(2., 1.);
	}
	
	@Test
	public void testBigInteger() {
		expectEquality(new BigInteger("1"), new BigInteger("1"));
		expectLess(new BigInteger("1"), new BigInteger("2"));
		expectGreater(new BigInteger("2"), new BigInteger("1"));
	}
	
	@Test
	public void testBigDecimal() {
		BigDecimal one = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		expectEquality(one, one);
		expectLess(one, two);
		expectGreater(two, one);
	}
	
	@Test
	public void testMixed() {
		expectEquality(1, 1.);
		expectLess(1., 2);
		expectGreater(2, 1.);
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private void expectEquality(Number n1, Number n2) {
		assertEquals(0, comparator.compare(n1, n2));
	}

	private void expectLess(Number n1, Number n2) {
		assertEquals(-1, comparator.compare(n1, n2));
	}

	private void expectGreater(Number n1, Number n2) {
		assertEquals(1, comparator.compare(n1, n2));
	}

	private void expectExceptionFor(Number n1, Number n2) {
		try {
			comparator.compare(n1, n2);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			// this is the expected behavior
		}
	}
}

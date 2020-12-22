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

import org.junit.Test;

/**
 * Tests the {@link ArrayComparator}.
 * Created at 04.05.2008 09:04:36
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class ArrayComparatorTest {
	
	private static final Integer[] EMPTY = new Integer[0];
	private static final Integer[] I123 = new Integer[] {1, 2, 3};
	private static final Integer[] I321 = new Integer[] {3, 2, 1};
	private static final Integer[] I12 = new Integer[] {1, 2};
	
	ArrayComparator<Integer> c = new ArrayComparator<Integer>();

	@Test
	public void testNull() {
		assertEquals( 0, c.compare(null, null));
		assertEquals(-1, c.compare(null, EMPTY));
		assertEquals( 1, c.compare(EMPTY, null));
	}
	
	@Test
	public void testEqual() {
		assertEquals(0, c.compare(EMPTY, EMPTY));
		assertEquals(0, c.compare(I123, I123));
	}
	
	@Test
	public void testEqualLength() {
		assertEquals(-1, c.compare(I123, I321));
		assertEquals( 1, c.compare(I321, I123));
	}
	
	@Test
	public void testDifferentLength() {
		assertEquals(-1, c.compare( I12, I123));
		assertEquals( 1, c.compare(I123,  I12));

		assertEquals(-1, c.compare( I12, I321));
		assertEquals( 1, c.compare(I321,  I12));
	}

}

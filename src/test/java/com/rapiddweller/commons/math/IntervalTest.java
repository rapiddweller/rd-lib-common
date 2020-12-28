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
package com.rapiddweller.commons.math;

import static org.junit.Assert.*;

import com.rapiddweller.commons.comparator.IntComparator;
import org.junit.Test;

/**
 * Tests the {@link Interval} class.
 * Created: 10.03.2011 15:28:39
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalTest {

	@Test
	public void testClosedInterval() {
		Interval<Integer> interval = new Interval<>(1, true, 2, true, new IntComparator());
		assertFalse(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("[1,2]", interval.toString());
	}

	@Test
	public void testRightUnboundedInterval() {
		Interval<Integer> interval = new Interval<>(1, true, null, true, new IntComparator());
		assertFalse(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertTrue(interval.contains(3));
		assertEquals("[1,null]", interval.toString());
	}

	@Test
	public void testLeftUnboundedInterval() {
		Interval<Integer> interval = new Interval<>(null, true, 2, true, new IntComparator());
		assertTrue(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("[null,2]", interval.toString());
	}

	@Test
	public void testRightOpenInterval() {
		Interval<Integer> interval = new Interval<>(1, true, 2, false, new IntComparator());
		assertFalse(interval.contains(0));
		assertTrue(interval.contains(1));
		assertFalse(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("[1,2[", interval.toString());
	}

	@Test
	public void testLeftOpenInterval() {
		Interval<Integer> interval = new Interval<>(1, false, 2, true, new IntComparator());
		assertFalse(interval.contains(0));
		assertFalse(interval.contains(1));
		assertTrue(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("]1,2]", interval.toString());
	}

	@Test
	public void testOpenInterval() {
		Interval<Integer> interval = new Interval<>(1, false, 2, false, new IntComparator());
		assertFalse(interval.contains(0));
		assertFalse(interval.contains(1));
		assertFalse(interval.contains(2));
		assertFalse(interval.contains(3));
		assertEquals("]1,2[", interval.toString());
	}

	@Test
	public void testInfiniteInterval() {
		Interval<Integer> interval = new Interval<>(null, false, null, false, new IntComparator());
		assertTrue(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertTrue(interval.contains(3));
		assertEquals("]null,null[", interval.toString());
	}

}

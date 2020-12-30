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
package com.rapiddweller.common.math;

import static org.junit.Assert.*;

import com.rapiddweller.common.ComparableComparator;
import org.junit.Test;

/**
 * Tests the {@link Intervals} class.
 * Created: 10.03.2011 17:36:21
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalsTest {

	@Test
	public void test() {
		Intervals<Integer> collection = new Intervals<>();
		ComparableComparator<Integer> comparator = new ComparableComparator<>();
		collection.add(new Interval<>(1, true, 2, true, comparator));
		collection.add(new Interval<>(3, false, 5, false, comparator));
		assertFalse(collection.contains(0));
		assertTrue( collection.contains(1));
		assertTrue( collection.contains(2));
		assertFalse(collection.contains(3));
		assertTrue( collection.contains(4));
		assertFalse(collection.contains(5));
		assertEquals("[1,2], ]3,5[", collection.toString());
	}
	
}

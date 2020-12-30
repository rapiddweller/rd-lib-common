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
package com.rapiddweller.common.collection;

import static org.junit.Assert.*;

import com.rapiddweller.common.iterator.IteratorTestCase;
import com.rapiddweller.common.math.IntRange;
import org.junit.Test;

/**
 * Tests the {@link CompressedIntSet}.
 * Created: 05.10.2010 19:53:23
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class CompressedIntSetTest extends IteratorTestCase {

	@Test
	public void testAdd_11() {
		CompressedIntSet set = new CompressedIntSet();
		assertTrue(set.isEmpty());
		set.add(1);
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		set.add(1);
		assertEquals(new IntRange(1, 1), set.numbers.get(1));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertEquals(1, set.size());
		expectNextElements(set.iterator(), 1).withNoNext();
	}
	
	@Test
	public void testAdd_12() {
		CompressedIntSet set = new CompressedIntSet();
		assertTrue(set.isEmpty());
		
		set.add(1);
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertFalse(set.contains(2));
		assertEquals(1, set.size());
		
		set.add(2);
		assertEquals(new IntRange(1, 2), set.numbers.get(1));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 2).withNoNext();
	}

	@Test
	public void testAdd_21() {
		CompressedIntSet set = new CompressedIntSet();

		set.add(2);
		assertFalse(set.isEmpty());
		assertFalse(set.contains(1));
		assertTrue(set.contains(2));
		assertEquals(1, set.size());
		
		set.add(1);
		assertEquals(new IntRange(1, 2), set.numbers.get(1));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 2).withNoNext();
	}

	@Test
	public void testAdd_13() {
		CompressedIntSet set = new CompressedIntSet();

		set.add(1);
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertFalse(set.contains(3));
		
		set.add(3);
		assertEquals(new IntRange(1, 1), set.numbers.get(1));
		assertEquals(new IntRange(3, 3), set.numbers.get(3));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(3));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 3).withNoNext();
	}

	@Test
	public void testAdd_31() {
		CompressedIntSet set = new CompressedIntSet();

		set.add(3);
		assertFalse(set.isEmpty());
		assertFalse(set.contains(1));
		assertTrue(set.contains(3));
		
		set.add(1);
		assertEquals(new IntRange(1, 1), set.numbers.get(1));
		assertEquals(new IntRange(3, 3), set.numbers.get(3));
		assertFalse(set.isEmpty());
		assertTrue(set.contains(1));
		assertTrue(set.contains(3));
		
		assertEquals(2, set.size());
		expectNextElements(set.iterator(), 1, 3).withNoNext();
	}

	@Test
	public void testAdd_132() {
		CompressedIntSet set = new CompressedIntSet();
		set.add(1);
		set.add(3);
		set.add(2);
		assertEquals(new IntRange(1, 3), set.numbers.get(1));
		assertEquals(3, set.size());
		expectNextElements(set.iterator(), 1, 2, 3).withNoNext();
	}
	
	@Test
	public void testAdd_1to10() {
		CompressedIntSet set = new CompressedIntSet();
		set.add(1);
		set.add(3);
		set.add(6);
		set.add(9);
		set.add(2);
		set.add(7);
		set.add(10);
		set.add(4);
		set.add(8);
		set.add(5);
		assertEquals(new IntRange(1, 10), set.numbers.get(1));
		assertEquals(10, set.size());
		expectNextElements(set.iterator(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).withNoNext();
	}

	@Test
	public void testRemove_not() {
		CompressedIntSet set = new CompressedIntSet();
		assertFalse(set.remove(3));
		set.add(2);
		assertFalse(set.remove(3));
		set.add(4);
		assertFalse(set.remove(3));
	}
	
	@Test
	public void testRemove_exactly() {
		CompressedIntSet set = new CompressedIntSet();
		set.add(2);
		assertTrue(set.remove(2));
		assertEquals(0, set.numbers.size());
		assertTrue(set.isEmpty());
	}
	
	@Test
	public void testRemove_min() {
		CompressedIntSet set = new CompressedIntSet();
		set.addAll(2, 3);
		assertTrue(set.remove(2));
		assertEquals(1, set.numbers.size());
		assertEquals(new IntRange(3, 3), set.numbers.get(3));
	}
	
	@Test
	public void testRemove_max() {
		CompressedIntSet set = new CompressedIntSet();
		set.addAll(4, 5);
		assertTrue(set.remove(5));
		assertEquals(1, set.numbers.size());
		assertEquals(new IntRange(4, 4), set.numbers.get(4));
	}
	
	@Test
	public void testRemove_mid() {
		CompressedIntSet set = new CompressedIntSet();
		set.addAll(6, 7, 8);
		assertTrue(set.remove(7));
		assertEquals(2, set.numbers.size());
		assertEquals(new IntRange(6, 6), set.numbers.get(6));
		assertEquals(new IntRange(8, 8), set.numbers.get(8));
	}
	
}

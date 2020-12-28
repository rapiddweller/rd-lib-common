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
package com.rapiddweller.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

/**
 * Test the {@link OrderedSet}.
 * Created at 28.02.2009 13:52:28
 * @since 0.4.8
 * @author Volker Bergmann
 */

public class OrderedSetTest {

	@Test
	public void testAdd() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.add(1));
		assertEquals(3, set.size());
		assertTrue(set.add(4));
		assertEquals(4, set.size());
	}

	@Test
	public void testAddAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.addAll(CollectionUtil.toList(1, 2, 3)));
		assertEquals(3, set.size());
		assertTrue(set.addAll(CollectionUtil.toList(3, 4, 5)));
		assertEquals(5, set.size());
	}

	@Test
	public void testContains() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.contains(0));
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(3));
		assertFalse(set.contains(4));
		set.clear();
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}

	@Test
	public void testContainsAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.containsAll(CollectionUtil.toList(0, 4)));
		assertTrue(set.containsAll(CollectionUtil.toList(1, 2)));
		assertFalse(set.containsAll(CollectionUtil.toList(0, 1)));
	}

	@Test
	public void testClearAndIsEmpty() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.isEmpty());
		set.clear();
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}
	
	@Test
	public void testIterator() {
		OrderedSet<Integer> set = create123();
		Iterator<Integer> iterator = set.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(1, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(3, iterator.next().intValue());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testRemove() {
		OrderedSet<Integer> set = create123();
		assertTrue(set.remove(1));
		assertFalse(set.remove(0));
		assertEquals(2, set.size());
	}
	
	@Test
	public void testRemoveAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.removeAll(CollectionUtil.toList(0, 4)));
		assertTrue(set.removeAll(CollectionUtil.toList(0, 1)));
		assertEquals(2, set.size());
		assertTrue(set.removeAll(CollectionUtil.toList(2, 3)));
		assertEquals(0, set.size());
	}
	
	@Test
	public void testRetainAll() {
		OrderedSet<Integer> set = create123();
		assertFalse(set.retainAll(CollectionUtil.toList(0, 1, 2, 3, 4)));
		assertEquals(3, set.size());
		assertFalse(set.retainAll(CollectionUtil.toList(1, 2, 3)));
		assertEquals(3, set.size());
		assertTrue(set.retainAll(CollectionUtil.toList(2, 3)));
		assertEquals(2, set.size());
		assertFalse(set.contains(1));
	}
	
	@Test
	public void testToArrayDefault() {
		OrderedSet<Integer> set = create123();
		assertTrue(Arrays.equals(new Integer[] { 1, 2, 3}, set.toArray()));
	}
	
	@Test
	public void testToArrayParametrized() {
		OrderedSet<Integer> set = create123();
		assertTrue(Arrays.equals(new Integer[] { 1, 2, 3}, set.toArray(new Integer[3])));
	}
	
	// helper methods --------------------------------------------------------------------------------------------------

	private static OrderedSet<Integer> create123() {
	    return new OrderedSet<>(CollectionUtil.toList(1, 2, 3));
    }
	
}

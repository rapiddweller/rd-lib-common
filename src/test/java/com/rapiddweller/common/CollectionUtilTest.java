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

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Tests the {@link CollectionUtil} class.
 * Created: 21.06.2007 08:29:32
 * @author Volker Bergmann
 */
public class CollectionUtilTest {

	@Test
    public void testToList() {
        List<Number> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(3);
        assertEquals(expectedList, CollectionUtil.toList(1, 2, 3));
    }

	@Test
    public void testToSet() {
        Set<Integer> expectedSet = new HashSet<>();
        expectedSet.add(1);
        expectedSet.add(2);
        expectedSet.add(3);
        assertEquals(expectedSet, CollectionUtil.toSet(1, 2, 3));
    }

	@Test
    public void testToSortedSet() {
        Set<Integer> expectedSet = new TreeSet<>();
        expectedSet.add(1);
        expectedSet.add(2);
        expectedSet.add(3);
        assertEquals(expectedSet, CollectionUtil.toSortedSet(3, 2, 1));
    }

	@Test
    public void testAdd() {
        List<Integer> list = new ArrayList<>();
        CollectionUtil.add(list, 1);
        assertEquals(Arrays.asList(1), list);
        CollectionUtil.add(list, 2);
        assertEquals(Arrays.asList(1, 2), list);
    }

	@Test
    public void testCopy() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(Arrays.asList(), CollectionUtil.copy(list, 0, 0));
        assertEquals(Arrays.asList(1), CollectionUtil.copy(list, 0, 1));
        assertEquals(Arrays.asList(1, 2), CollectionUtil.copy(list, 0, 2));
        assertEquals(Arrays.asList(2, 3), CollectionUtil.copy(list, 1, 2));
    }

	@Test
    public void testEmpty() {
        assertTrue(CollectionUtil.isEmpty(null));
        assertTrue(CollectionUtil.isEmpty(new HashSet<String>()));
        assertTrue(CollectionUtil.isEmpty(new ArrayList<String>()));
        assertFalse(CollectionUtil.isEmpty(Arrays.asList(1)));
    }

	@Test
    public void testToArray() {
        assertTrue(Arrays.equals(new Integer[] { 1 }, CollectionUtil.toArray(Arrays.asList(1), Integer.class)));
        assertTrue(Arrays.equals(new Integer[] { 1, 2, 3 }, CollectionUtil.toArray(Arrays.asList(1, 2, 3), Integer.class)));
    }
	
	@Test
	public void testFormatCommaSeparatedList() {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		assertEquals("A, B", CollectionUtil.formatCommaSeparatedList(list, null));
		assertEquals("'A', 'B'", CollectionUtil.formatCommaSeparatedList(list, '\''));
	}
	
}

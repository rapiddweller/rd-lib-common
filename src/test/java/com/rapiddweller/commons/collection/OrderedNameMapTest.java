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
package com.rapiddweller.commons.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link OrderedNameMap}.
 * Created at 02.05.2008 14:02:41
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class OrderedNameMapTest {
	
	@Test
	public void testCaseSensitive() {
		OrderedNameMap<Integer> map = OrderedNameMap.createCaseSensitiveMap();
		map.put("a", 1);
		assertTrue(map.containsKey("a"));
		assertEquals(1, (int) map.get("a"));
		assertFalse(map.containsKey("A"));
		assertNull(map.get("A"));
		assertEquals(new MapEntry<>("a", 1), map.getEntry("a"));
		assertEquals(null, map.get("A"));
		assertEquals(1, (int) map.remove("a"));
	}
	
	@Test
	public void testCaseInsensitive() {
		OrderedNameMap<Integer> map = OrderedNameMap.createCaseInsensitiveMap();
		map.put("a", 1);
		assertTrue(map.containsKey("a"));
		assertEquals(1, (int) map.get("a"));
		assertTrue(map.containsKey("A"));
		assertEquals(1, (int) map.get("A"));
		assertEquals(new MapEntry<>("a", 1), map.getEntry("a"));
		assertEquals(new MapEntry<>("a", 1), map.getEntry("A"));
		assertEquals(1, (int) map.remove("a"));
	}
	
	@Test
	public void testCaseIgnorant() {
		OrderedNameMap<Integer> map = OrderedNameMap.createCaseIgnorantMap();
		map.put("a", 1);
		assertTrue(map.containsKey("a"));
		assertEquals(1, (int) map.get("a"));
		assertTrue(map.containsKey("A"));
		assertEquals(1, (int) map.get("A"));
		assertEquals(new MapEntry<>("a", 1), map.getEntry("a"));
		assertEquals(new MapEntry<>("a", 1), map.getEntry("a"));
		assertEquals(1, (int) map.remove("a"));
	}

}

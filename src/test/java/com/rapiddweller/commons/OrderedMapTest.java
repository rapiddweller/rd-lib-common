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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

/**
 * Tests the {@link OrderedMap}.
 * Created: 21.06.2007 08:32:53
 * @author Volker Bergmann
 */
public class OrderedMapTest {

	@Test
    public void testDefaultConstructor() {
        OrderedMap<Integer, Integer> map = createMap123();
        check(map, 1, 11, 2, 22, 3, 33);
    }

	@Test
    public void testCopyConstructor() {
        OrderedMap<Integer, Integer> map = createMap123();
        OrderedMap<Integer, Integer> copy = new OrderedMap<>(map);
        check(copy, 1, 11, 2, 22, 3, 33);
    }

	@Test
    public void testEmptyMap() {
        OrderedMap<Integer, Integer> map = new OrderedMap<>();
        check(map);
    }

	@Test
    public void testClear() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.clear();
        check(map);
    }

	@Test
    public void testAppend() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.put(4, 44);
        check(map, 1, 11, 2, 22, 3, 33, 4, 44);
    }

	@Test
    public void testOverwrite() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.put(2, 222);
        check(map, 1, 11, 2, 222, 3, 33);
    }

	@Test
    public void testRemoveAtStart() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.remove(1);
        check(map, 2, 22, 3, 33);
    }

	@Test
    public void testRemoveInMiddle() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.remove(2);
        check(map, 1, 11, 3, 33);
    }

	@Test
    public void testRemoveAtEnd() {
        OrderedMap<Integer, Integer> map = createMap123();
        map.remove(3);
        check(map, 1, 11, 2, 22);
    }

	@Test
    public void testPutAll() {
        OrderedMap<Integer, Integer> map = createMap123();
        OrderedMap<Integer, Integer> map2 = new OrderedMap<>();
        map2.put(0, 0);
        map2.put(4, 44);
        map.putAll(map2);
        check(map, 1, 11, 2, 22, 3, 33, 0, 0, 4, 44);
    }

	@Test
    public void testEquals() {
        assertTrue(new OrderedMap<Integer, Integer>().equals(new OrderedMap<Integer, Integer>()));
        OrderedMap<Integer, Integer> map1 = createMap123();
        OrderedMap<Integer, Integer> map2 = createMap123();
        assertTrue(map1.equals(map2));
        map2.put(4, 44);
        assertFalse(map1.equals(map2));
        assertFalse(map1.equals(new OrderedMap<Integer, Integer>()));
    }

	@Test
    public void testHashCode() {
        assertEquals(new OrderedMap<Integer, Integer>().hashCode(), new OrderedMap<Integer, Integer>().hashCode());
        OrderedMap<Integer, Integer> map1 = createMap123();
        OrderedMap<Integer, Integer> map2 = createMap123();
        assertEquals(map1.hashCode(), map2.hashCode());
        map2.put(4, 44);
        assertTrue(map1.hashCode() != map2.hashCode());
        assertTrue(map1.hashCode() != new OrderedMap<Integer, Integer>().hashCode());
    }

	@Test
    public void testToString() {
        assertEquals("{1=11, 2=22, 3=33}", createMap123().toString());
    }
    
	@Test
    public void testEqualsIgnoreOrder() {
    	OrderedMap<Integer, Integer> map123 = createMap123();
    	OrderedMap<Integer, Integer> map321 = new OrderedMap<>();
    	map321.put(3, 33);
    	map321.put(2, 22);
    	assertFalse(map123.equalsIgnoreOrder(map321));
    	assertFalse(map321.equalsIgnoreOrder(map123));
    	map321.put(1, 11);
    	assertTrue(map123.equalsIgnoreOrder(map321));
    	assertTrue(map321.equalsIgnoreOrder(map123));
    }
    
	@Test
    public void testUpdateEntry() {
    	OrderedMap<Integer, Integer> map = createMap123();
    	for (Map.Entry<Integer, Integer> entry : map.entrySet())
    		entry.setValue(4);
    	for (Integer value : map.values)
    		assertEquals(4, (int)value);
    }

    // private helpers -------------------------------------------------------------------------------------------------

    @SafeVarargs
    private static <T> void check(OrderedMap<T, T> map, T ... expectedKeyValuePairs) {
        if (expectedKeyValuePairs.length == 0)
            assertTrue("Map is expected to be empty", map.isEmpty());
        assertEquals("Unexpected size", expectedKeyValuePairs.length / 2, map.size());
        Object[] expectedValues = new Object[expectedKeyValuePairs.length / 2];
        Object[] expectedKeys = new Object[expectedKeyValuePairs.length / 2];
        for (int i = 0; i < expectedKeyValuePairs.length; i += 2) {
            expectedKeys[i / 2] = expectedKeyValuePairs[i];
            expectedValues[i / 2] = expectedKeyValuePairs[i + 1];
        }
        assertTrue(Arrays.equals(expectedValues, map.toArray()));
        Set<T> keys = map.keySet();
        Iterator<T> keyIterator = keys.iterator();
        List<T> values = map.values();
        Iterator<T> valueIterator = values.iterator();
        Set<Map.Entry<T, T>> entries = map.entrySet();
        Iterator<Map.Entry<T, T>> entryIterator = entries.iterator();
        for (int i = 0; i < expectedKeys.length; i++) {
            assertTrue(map.containsKey(expectedKeys[i]));
            assertTrue(map.containsValue(expectedValues[i]));
            assertEquals(expectedValues[i], map.get(expectedKeys[i]));
            assertTrue(keyIterator.hasNext());
            assertEquals(expectedKeys[i], keyIterator.next());
            assertTrue(valueIterator.hasNext());
            assertEquals(expectedValues[i], valueIterator.next());
            assertEquals(expectedValues[i], values.get(i));
            assertEquals(expectedValues[i], map.valueAt(i));
            assertTrue(entryIterator.hasNext());
            Map.Entry<T, T> entry = entryIterator.next();
            assertEquals(expectedKeys[i], entry.getKey());
            assertEquals(expectedValues[i], entry.getValue());
        }
    }

    private static OrderedMap<Integer, Integer> createMap123() {
        OrderedMap<Integer, Integer> map = new OrderedMap<>();
        map.put(1, 11);
        map.put(2, 22);
        map.put(3, 33);
        return map;
    }
}

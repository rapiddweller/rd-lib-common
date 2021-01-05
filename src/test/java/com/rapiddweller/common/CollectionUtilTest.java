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

import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.common.iterator.FilteringIterator;
import com.rapiddweller.common.iterator.JDKIteratorWrapper;
import com.rapiddweller.common.iterator.RecursiveMapValueIterator;
import com.rapiddweller.common.iterator.ReverseIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.core.util.ObjectArrayIterator;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link CollectionUtil} class.
 * Created: 21.06.2007 08:29:32
 *
 * @author Volker Bergmann
 */
public class CollectionUtilTest {

    @Test
    public void testIsEmpty() {
        assertTrue(CollectionUtil.isEmpty(new ArrayList<Integer>()));
    }

    @Test
    public void testToList() {
        List<Number> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(3);
        assertEquals(expectedList, CollectionUtil.toList(1, 2, 3));
    }

    @Test
    public void testToList2() {
        assertEquals(1, CollectionUtil.<Object>toList("array").size());
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
    public void testToSet2() {
        assertEquals(1, CollectionUtil.<Object>toSet("elements").size());
    }

    @Test
    public void testToSet3() {
        assertTrue(CollectionUtil.<Object>toSet(null).isEmpty());
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
    public void testToCharSet() {
        Set<Character> actualToCharSetResult = CollectionUtil.toCharSet(new char[]{'A', 'A', 'A', 'A'});
        assertEquals(1, actualToCharSetResult.size());
        assertTrue(actualToCharSetResult.contains('A'));
    }

    @Test
    public void testToCharSet2() {
        assertTrue(CollectionUtil.toCharSet(null).isEmpty());
    }

    @Test
    public void testAdd() {
        List<Integer> list = new ArrayList<>();
        CollectionUtil.add(list, 1);
        assertEquals(Collections.singletonList(1), list);
        CollectionUtil.add(list, 2);
        assertEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void testCopy() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(Collections.emptyList(), CollectionUtil.copy(list, 0, 0));
        assertEquals(Collections.singletonList(1), CollectionUtil.copy(list, 0, 1));
        assertEquals(Arrays.asList(1, 2), CollectionUtil.copy(list, 0, 2));
        assertEquals(Arrays.asList(2, 3), CollectionUtil.copy(list, 1, 2));
    }

    @Test
    public void testCopy2() {
        RecursiveMapValueIterator<Object> src = new RecursiveMapValueIterator<Object>(new HashMap<Object, Object>());
        ArrayList<Object> objectList = new ArrayList<Object>();
        Collection<Object> actualCopyResult = CollectionUtil.<Object>copy(src, objectList);
        assertSame(objectList, actualCopyResult);
        assertTrue(actualCopyResult.isEmpty());
    }

    @Test
    public void testCopy3() {
        ReverseIterator<Object> realIterator = new ReverseIterator<Object>(new ReverseIterator<Object>(
                new JDKIteratorWrapper<Object>(new RecursiveMapValueIterator<Object>(new HashMap<Object, Object>()))));
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        FilteringIterator<Object> src = new FilteringIterator<Object>(realIterator,
                new OrFilter<Object>(orFilter, orFilter1, new OrFilter<Object>(null, null, null)));
        ArrayList<Object> objectList = new ArrayList<Object>();
        Collection<Object> actualCopyResult = CollectionUtil.<Object>copy(src, objectList);
        assertSame(objectList, actualCopyResult);
        assertTrue(actualCopyResult.isEmpty());
    }

    @Test
    public void testCopy4() {
        ObjectArrayIterator<Object> objectArrayIterator = new ObjectArrayIterator<Object>("foo", "foo", "foo");
        ArrayList<Object> objectList = new ArrayList<Object>();
        Collection<Object> actualCopyResult = CollectionUtil.<Object>copy(objectArrayIterator, objectList);
        assertSame(objectList, actualCopyResult);
        assertEquals(3, actualCopyResult.size());
        assertFalse(objectArrayIterator.hasNext());
    }

    @Test
    public void testCopy5() {
        assertTrue(CollectionUtil.<Object>copy(new ArrayList<Integer>(), 2, 0).isEmpty());
    }

    @Test
    public void testRevert() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        List<Object> actualRevertResult = CollectionUtil.<Object>revert(objectList);
        assertSame(objectList, actualRevertResult);
        assertTrue(actualRevertResult.isEmpty());
    }

    @Test
    public void testRevert2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        List<Object> actualRevertResult = CollectionUtil.<Object>revert(objectList);
        assertSame(objectList, actualRevertResult);
        assertEquals(2, actualRevertResult.size());
    }

    @Test
    public void testEmpty() {
        assertTrue(CollectionUtil.isEmpty(null));
        assertTrue(CollectionUtil.isEmpty(new HashSet<String>()));
        assertTrue(CollectionUtil.isEmpty(new ArrayList<String>()));
        assertFalse(CollectionUtil.isEmpty(Collections.singletonList(1)));
    }

    @Test
    public void testToArray() {
        assertTrue(Arrays.equals(new Integer[]{1}, CollectionUtil.toArray(Collections.singletonList(1), Integer.class)));
        assertTrue(Arrays.equals(new Integer[]{1, 2, 3}, CollectionUtil.toArray(Arrays.asList(1, 2, 3), Integer.class)));
        assertThrows(IllegalArgumentException.class, () -> CollectionUtil.<Object>toArray(new ArrayList<Integer>()));
    }

    @Test
    public void testToArray2() {
        Class<Object> componentType = Object.class;
        assertEquals(0, CollectionUtil.<Object>toArray(new ArrayList<Integer>(), componentType).length);
    }

    @Test
    public void testToDoubleArray() {
        assertEquals(0, CollectionUtil.toDoubleArray(new ArrayList<Double>()).length);
    }

    @Test
    public void testToDoubleArray2() {
        ArrayList<Double> resultDoubleList = new ArrayList<Double>();
        resultDoubleList.add(10.0);
        assertEquals(1, CollectionUtil.toDoubleArray(resultDoubleList).length);
    }

    @Test
    public void testToCharArray() {
        assertEquals(0, CollectionUtil.toCharArray(new ArrayList<Character>()).length);
    }

    @Test
    public void testToCharArray2() {
        ArrayList<Character> characterList = new ArrayList<Character>();
        characterList.add('\u0000');
        assertEquals(1, CollectionUtil.toCharArray(characterList).length);
    }

    @Test
    public void testBuildMap() {
        assertEquals(1, CollectionUtil.<Object, Object>buildMap("key", "value").size());
        assertThrows(IllegalArgumentException.class, () -> CollectionUtil.buildMap("keyValuePairs"));
        assertTrue(CollectionUtil.buildMap().isEmpty());
        assertEquals(1, CollectionUtil.buildMap("keyValuePairs", "keyValuePairs").size());
    }

    @Test
    public void testBuildOrderedMap() {
        assertThrows(IllegalArgumentException.class, () -> CollectionUtil.buildOrderedMap("keyValuePairs"));
        assertTrue(CollectionUtil.buildOrderedMap().isEmpty());
        assertEquals(1, CollectionUtil.buildOrderedMap("keyValuePairs", "keyValuePairs").size());
    }

    @Test
    public void testEqualsIgnoreOrder() {
        ArrayList<Object> a1 = new ArrayList<Object>();
        assertTrue(CollectionUtil.<Object>equalsIgnoreOrder(a1, new ArrayList<Object>()));
    }

    @Test
    public void testEqualsIgnoreOrder2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(null);
        assertFalse(CollectionUtil.<Object>equalsIgnoreOrder(objectList, new ArrayList<Object>()));
    }

    @Test
    public void testEqualsIgnoreOrder3() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(null);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add(null);
        assertTrue(CollectionUtil.<Object>equalsIgnoreOrder(objectList, objectList1));
    }

    @Test
    public void testEqualsIgnoreOrder4() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(2);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add(null);
        assertFalse(CollectionUtil.<Object>equalsIgnoreOrder(objectList, objectList1));
    }

    @Test
    public void testGetCaseInsensitive() {
        assertNull(CollectionUtil.<Object>getCaseInsensitive("Key", new HashMap<String, Object>()));
        assertNull(CollectionUtil.<Object>getCaseInsensitive(null, new HashMap<String, Object>()));
        assertNull(CollectionUtil.<Object>getCaseInsensitive("Key", new HashMap<String, Object>(1)));
    }

    @Test
    public void testContainsCaseInsensitive() {
        assertFalse(CollectionUtil.<Object>containsCaseInsensitive("Key", new HashMap<String, Object>()));
        assertFalse(CollectionUtil.<Object>containsCaseInsensitive("java.util.SortedSet", new HashMap<String, Object>()));
    }

    @Test
    public void testOfEqualContent() {
        assertFalse(CollectionUtil.<Object>ofEqualContent(new ArrayList<Object>(), new Object[]{"array"}));
        assertTrue(CollectionUtil.<Object>ofEqualContent(new ArrayList<Object>(), null));
        assertTrue(CollectionUtil.<Object>ofEqualContent(new ArrayList<Object>(), new Object[]{}));
    }

    @Test
    public void testOfEqualContent2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(null);
        assertFalse(CollectionUtil.<Object>ofEqualContent(objectList, new Object[]{"array"}));
    }

    @Test
    public void testOfEqualContent3() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(null);
        objectList.add(null);
        assertFalse(CollectionUtil.<Object>ofEqualContent(objectList, new Object[]{"array"}));
    }

    @Test
    public void testOfEqualContent4() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(null);
        assertTrue(CollectionUtil.<Object>ofEqualContent(objectList, new Object[]{null}));
    }

    @Test
    public void testOfEqualContent5() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(null);
        assertFalse(CollectionUtil.<Object>ofEqualContent(objectList, null));
    }

    @Test
    public void testLastElement() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertEquals("e", CollectionUtil.<Object>lastElement(objectList));
    }

    @Test
    public void testEmptyList() {
        assertTrue(CollectionUtil.<Object>emptyList().isEmpty());
    }

    @Test
    public void testFormatCommaSeparatedList() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        assertEquals("A, B", CollectionUtil.formatCommaSeparatedList(list, null));
        assertEquals("'A', 'B'", CollectionUtil.formatCommaSeparatedList(list, '\''));
    }

    @Test
    public void testFormatCommaSeparatedList2() {
        assertEquals("", CollectionUtil.formatCommaSeparatedList(new ArrayList<Integer>(), 'A'));
    }

}

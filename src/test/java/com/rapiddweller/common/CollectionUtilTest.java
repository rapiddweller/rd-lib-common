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

import com.rapiddweller.common.exception.IllegalArgumentError;
import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.common.iterator.FilteringIterator;
import com.rapiddweller.common.iterator.JDKIteratorWrapper;
import com.rapiddweller.common.iterator.RecursiveMapValueIterator;
import com.rapiddweller.common.iterator.ReverseIterator;
import org.apache.logging.log4j.core.util.ObjectArrayIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link CollectionUtil} class.
 * Created: 21.06.2007 08:29:32
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
    assertTrue(CollectionUtil.toSet().isEmpty());
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
    Set<Character> actualToCharSetResult = CollectionUtil.toCharSet(new char[] {'A', 'A', 'A', 'A'});
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
    RecursiveMapValueIterator<Object> src = new RecursiveMapValueIterator<>(new HashMap<>());
    ArrayList<Object> objectList = new ArrayList<>();
    Collection<Object> actualCopyResult = CollectionUtil.copy(src, objectList);
    assertSame(objectList, actualCopyResult);
    assertTrue(actualCopyResult.isEmpty());
  }

  @Test
  public void testCopy3() {
    ReverseIterator<Object> realIterator = new ReverseIterator<>(new ReverseIterator<>(
        new JDKIteratorWrapper<>(new RecursiveMapValueIterator<>(new HashMap<>()))));
    OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
    OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
    FilteringIterator<Object> src = new FilteringIterator<>(realIterator,
        new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null)));
    ArrayList<Object> objectList = new ArrayList<>();
    Collection<Object> actualCopyResult = CollectionUtil.copy(src, objectList);
    assertSame(objectList, actualCopyResult);
    assertTrue(actualCopyResult.isEmpty());
  }

  @Test
  public void testCopy4() {
    ObjectArrayIterator<Object> objectArrayIterator = new ObjectArrayIterator<>("foo", "foo", "foo");
    ArrayList<Object> objectList = new ArrayList<>();
    Collection<Object> actualCopyResult = CollectionUtil.copy(objectArrayIterator, objectList);
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
    ArrayList<Object> objectList = new ArrayList<>();
    List<Object> actualRevertResult = CollectionUtil.revert(objectList);
    assertSame(objectList, actualRevertResult);
    assertTrue(actualRevertResult.isEmpty());
  }

  @Test
  public void testRevert2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    List<Object> actualRevertResult = CollectionUtil.revert(objectList);
    assertSame(objectList, actualRevertResult);
    assertEquals(2, actualRevertResult.size());
  }

  @Test
  public void testEmpty() {
    assertTrue(CollectionUtil.isEmpty((Collection) null));
    assertTrue(CollectionUtil.isEmpty(new HashSet<String>()));
    assertTrue(CollectionUtil.isEmpty(new ArrayList<String>()));
    assertFalse(CollectionUtil.isEmpty(Collections.singletonList(1)));
  }

  @Test
  public void testToArray() {
    assertArrayEquals(new Integer[] {1}, CollectionUtil.toArray(Collections.singletonList(1), Integer.class));
    assertArrayEquals(new Integer[] {1, 2, 3}, CollectionUtil.toArray(Arrays.asList(1, 2, 3), Integer.class));
  }

  @Test
  public void testToArray_empty() {
    List<Object> emptyList = new ArrayList<>();
    assertThrows(IllegalArgumentError.class, () -> CollectionUtil.toArray(emptyList));
  }

  @Test
  public void testToArray2() {
    Class<Object> componentType = Object.class;
    assertEquals(0, CollectionUtil.toArray(new ArrayList<Integer>(), componentType).length);
  }

  @Test
  public void testToDoubleArray() {
    assertEquals(0, CollectionUtil.toDoubleArray(new ArrayList<>()).length);
  }

  @Test
  public void testToDoubleArray2() {
    ArrayList<Double> resultDoubleList = new ArrayList<>();
    resultDoubleList.add(10.0);
    assertEquals(1, CollectionUtil.toDoubleArray(resultDoubleList).length);
  }

  @Test
  public void testToCharArray() {
    assertEquals(0, CollectionUtil.toCharArray(new ArrayList<>()).length);
  }

  @Test
  public void testToCharArray2() {
    ArrayList<Character> characterList = new ArrayList<>();
    characterList.add('\u0000');
    assertEquals(1, CollectionUtil.toCharArray(characterList).length);
  }

  @Test
  public void testBuildMap() {
    assertEquals(1, CollectionUtil.<Object, Object>buildMap("key", "value").size());
    assertThrows(IllegalArgumentError.class, () -> CollectionUtil.buildMap("keyValuePairs"));
    assertTrue(CollectionUtil.buildMap().isEmpty());
    assertEquals(1, CollectionUtil.buildMap("keyValuePairs", "keyValuePairs").size());
  }

  @Test
  public void testBuildOrderedMap() {
    assertThrows(IllegalArgumentError.class, () -> CollectionUtil.buildOrderedMap("keyValuePairs"));
    assertTrue(CollectionUtil.buildOrderedMap().isEmpty());
    assertEquals(1, CollectionUtil.buildOrderedMap("keyValuePairs", "keyValuePairs").size());
  }

  @Test
  public void testEqualsIgnoreOrder() {
    ArrayList<Object> a1 = new ArrayList<>();
    assertTrue(CollectionUtil.equalsIgnoreOrder(a1, new ArrayList<>()));
  }

  @Test
  public void testEqualsIgnoreOrder2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    assertFalse(CollectionUtil.equalsIgnoreOrder(objectList, new ArrayList<>()));
  }

  @Test
  public void testEqualsIgnoreOrder3() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add(null);
    assertTrue(CollectionUtil.equalsIgnoreOrder(objectList, objectList1));
  }

  @Test
  public void testEqualsIgnoreOrder4() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(2);
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add(null);
    assertFalse(CollectionUtil.equalsIgnoreOrder(objectList, objectList1));
  }

  @Test
  public void testGetCaseInsensitive() {
    assertNull(CollectionUtil.getCaseInsensitive("Key", new HashMap<>()));
    assertNull(CollectionUtil.getCaseInsensitive(null, new HashMap<>()));
    assertNull(CollectionUtil.getCaseInsensitive("Key", new HashMap<>(1)));
  }

  @Test
  public void testContainsCaseInsensitive() {
    assertFalse(CollectionUtil.containsCaseInsensitive("Key", new HashMap<>()));
    assertFalse(CollectionUtil.containsCaseInsensitive("java.util.SortedSet", new HashMap<>()));
  }

  @Test
  public void testOfEqualContent() {
    assertFalse(CollectionUtil.ofEqualContent(new ArrayList<>(), new Object[] {"array"}));
    assertTrue(CollectionUtil.ofEqualContent(new ArrayList<>(), null));
    assertTrue(CollectionUtil.ofEqualContent(new ArrayList<>(), new Object[] {}));
  }

  @Test
  public void testOfEqualContent2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    assertFalse(CollectionUtil.ofEqualContent(objectList, new Object[] {"array"}));
  }

  @Test
  public void testOfEqualContent3() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    objectList.add(null);
    assertFalse(CollectionUtil.ofEqualContent(objectList, new Object[] {"array"}));
  }

  @Test
  public void testOfEqualContent4() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    assertTrue(CollectionUtil.ofEqualContent(objectList, new Object[] {null}));
  }

  @Test
  public void testOfEqualContent5() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    assertFalse(CollectionUtil.ofEqualContent(objectList, null));
  }

  @Test
  public void testLastElement() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    assertEquals("e", CollectionUtil.lastElement(objectList));
  }

  @Test
  public void testEmptyList() {
    assertTrue(CollectionUtil.emptyList().isEmpty());
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

  @Test
  public void testStripOffPrefixes_no_graph() {
    Map<String, Map<String, String>> result = CollectionUtil.stripOffPrefixes(CollectionUtil.buildMap("a", "b"));
    assertEquals(1, result.size());
    assertEquals("b", result.get("a").get(""));
  }

  @Test
  public void testStripOffPrefixes_two_levels() {
    Map<String, Map<String, String>> result = CollectionUtil.stripOffPrefixes(CollectionUtil.buildMap("a.b", "c"));
    assertEquals(1, result.size());
    assertEquals("c", result.get("a").get("b"));
  }

  @Test
  public void testStripOffPrefixes_two_sub_component() {
    HashMap<String, String> src = new HashMap<>();
    src.put("a.b", "c");
    src.put("a.d", "e");
    Map<String, Map<String, String>> result = CollectionUtil.stripOffPrefixes(src);
    assertEquals(1, result.size());
    assertEquals("c", result.get("a").get("b"));
    assertEquals("e", result.get("a").get("d"));
  }

}

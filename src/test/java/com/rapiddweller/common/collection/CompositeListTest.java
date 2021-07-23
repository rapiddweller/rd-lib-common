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

import com.rapiddweller.common.CollectionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Combines several {@link List} into one, providing a common access interface.
 * Created: 10.09.2012 20:09:13
 *
 * @author Volker Bergmann
 * @since 0.5.18
 */
public class CompositeListTest {

  @Test
  public void testDefaultConstruction() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>();
    assertEquals(0, list.size());
    assertEquals("[]", list.toString());
    assertTrue(list.isEmpty());
  }

  @Test
  public void testVarArgsConstruction() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<>());
    assertEquals(3, list.size());
    assertEquals("[[1, 2], [3], []]", list.toString());
    assertFalse(list.isEmpty());
  }

  @Test
  public void testAppendingAdd() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<>());
    list.add(4);
    assertEquals(4, list.size());
    assertEquals("[[1, 2], [3], [4]]", list.toString());
  }

  @Test
  public void testInsertingAdd() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<>());
    list.add(1, 4);
    assertEquals(4, list.size());
    assertEquals("[[1, 4, 2], [3], []]", list.toString());
    list.add(4, 5);
    assertEquals(5, list.size());
    assertEquals("[[1, 4, 2], [3], [5]]", list.toString());
  }

  @Test
  public void testAppendingAddAll() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), new ArrayList<>());
    list.addAll(CollectionUtil.toList(3, 4));
    assertEquals(4, list.size());
    assertEquals("[[1, 2], [3, 4]]", list.toString());
  }

  @Test
  public void testInsertingAddAll() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), new ArrayList<>());
    list.addAll(1, CollectionUtil.toList(3, 4));
    assertEquals(4, list.size());
    assertEquals("[[1, 3, 4, 2], []]", list.toString());
  }

  @Test
  public void testClear() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2));
    assertEquals(2, list.size());
    assertEquals("[[1, 2]]", list.toString());
    list.clear();
    assertEquals(0, list.size());
    assertEquals("[]", list.toString());
  }

  @Test
  public void testContains() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    assertFalse(list.contains(0));
    assertTrue(list.contains(1));
    assertTrue(list.contains(2));
    assertTrue(list.contains(3));
    assertTrue(list.contains(4));
    assertFalse(list.contains(5));
  }

  @Test
  public void testContainsAll() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    assertFalse(list.containsAll(CollectionUtil.toList(0)));
    assertTrue(list.containsAll(CollectionUtil.toList(1)));
    assertTrue(list.containsAll(CollectionUtil.toList(2, 3, 4)));
    assertFalse(list.containsAll(CollectionUtil.toList(1, 2, 3, 4, 5)));
  }

  @Test
  public void testGet_legalIndex() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    assertEquals(1, list.get(0).intValue());
    assertEquals(2, list.get(1).intValue());
    assertEquals(3, list.get(2).intValue());
    assertEquals(4, list.get(3).intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGet_index_minus_1() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    list.get(-1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGet_indexN() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    list.get(4);
  }

  @Test
  public void testIndexOf() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    assertEquals(-1, list.indexOf(0));
    assertEquals(0, list.indexOf(1));
    assertEquals(1, list.indexOf(2));
    assertEquals(2, list.indexOf(3));
    assertEquals(-1, list.indexOf(4));
  }

  @Test
  public void testIterator() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<>());
    Iterator<Integer> iterator = list.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(1, iterator.next().intValue());
    assertTrue(iterator.hasNext());
    assertEquals(2, iterator.next().intValue());
    assertTrue(iterator.hasNext());
    assertEquals(3, iterator.next().intValue());
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testListIterator() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<>());
    ListIterator<Integer> iterator = list.listIterator();
    assertFalse(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(1, iterator.next().intValue());
    assertTrue(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(2, iterator.next().intValue());
    assertTrue(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(3, iterator.next().intValue());
    assertTrue(iterator.hasPrevious());
    assertFalse(iterator.hasNext());
    assertEquals(3, iterator.previous().intValue());
    assertTrue(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(2, iterator.previous().intValue());
    assertTrue(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(1, iterator.previous().intValue());
    assertFalse(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
  }

  @Test
  public void testSubListIterator() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3), new ArrayList<>());
    ListIterator<Integer> iterator = list.listIterator(1);
    assertTrue(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(2, iterator.next().intValue());
    assertTrue(iterator.hasPrevious());
    assertTrue(iterator.hasNext());
    assertEquals(3, iterator.next().intValue());
    assertTrue(iterator.hasPrevious());
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testLastIndexOf() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    assertEquals(-1, list.lastIndexOf(0));
    assertEquals(3, list.lastIndexOf(1));
    assertEquals(2, list.lastIndexOf(3));
    assertEquals(-1, list.lastIndexOf(4));
  }

  @Test
  public void testRemove_object() {
    @SuppressWarnings("unchecked")
    CompositeList<String> list = new CompositeList<>(CollectionUtil.toList("1", "2"), CollectionUtil.toList("3", "4"));
    list.remove("3");
    assertEquals("[[1, 2], [4]]", list.toString());
    list.remove("2");
    assertEquals("[[1], [4]]", list.toString());
    list.remove("4");
    assertEquals("[[1], []]", list.toString());
    list.remove("1");
    assertEquals("[[], []]", list.toString());
  }

  @Test
  public void testRemove_index() {
    @SuppressWarnings("unchecked")
    CompositeList<String> list = new CompositeList<>(CollectionUtil.toList("1", "2"), CollectionUtil.toList("3", "4"));
    list.remove(2);
    assertEquals("[[1, 2], [4]]", list.toString());
    list.remove(1);
    assertEquals("[[1], [4]]", list.toString());
    list.remove(1);
    assertEquals("[[1], []]", list.toString());
    list.remove(0);
    assertEquals("[[], []]", list.toString());
  }

  @Test
  public void testRemoveAll() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    list.removeAll(CollectionUtil.toList(1, 4));
    assertEquals("[[2], [3]]", list.toString());
    list.removeAll(CollectionUtil.toList(2, 3));
    assertEquals("[[], []]", list.toString());
  }

  @Test
  public void testRetainAll() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    list.retainAll(CollectionUtil.toList(1, 2));
    assertEquals("[[1, 2], [1]]", list.toString());
    list.retainAll(CollectionUtil.toList(4));
    assertEquals("[[], []]", list.toString());
  }

  @Test
  public void testSet_legal() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    list.set(1, 5);
    assertEquals("[[1, 5], [3, 1]]", list.toString());
    list.set(3, 6);
    assertEquals("[[1, 5], [3, 6]]", list.toString());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSet_minus1() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    list.set(-1, 5);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSet_N() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 1));
    list.set(4, 5);
  }

  @Test
  public void testSubList() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    assertEquals("[2, 3]", list.subList(1, 3).toString());
  }

  @Test
  public void testToArray_noarg() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    assertArrayEquals(new Object[] {1, 2, 3, 4}, list.toArray());
  }

  @Test
  public void testToArray_arrayArg() {
    @SuppressWarnings("unchecked")
    CompositeList<Integer> list = new CompositeList<>(CollectionUtil.toList(1, 2), CollectionUtil.toList(3, 4));
    assertArrayEquals(new Integer[] {1, 2, 3, 4}, list.toArray(new Integer[4]));
  }


}

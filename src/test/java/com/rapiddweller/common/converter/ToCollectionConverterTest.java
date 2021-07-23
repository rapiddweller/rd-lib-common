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

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the ToCollectionConverter.
 * Created: 28.08.2007 17:35:50
 *
 * @author Volker Bergmann
 */
public class ToCollectionConverterTest extends AbstractConverterTest {

  // tests -----------------------------------------------------------------------------------------------------------

  public ToCollectionConverterTest() {
    super(ToCollectionConverter.class);
  }

  @Test
  public void testNull() {
    assertNull(ToCollectionConverter.convert(null, List.class));
  }

  @Test
  @SuppressWarnings({"rawtypes", "unchecked"})
  public void testToList() throws ConversionException {
    Converter<Object, ArrayList> toArrayListConverter = new ToCollectionConverter<>(ArrayList.class);
    Converter<Object, List> toListConverter = new ToCollectionConverter<>(List.class);
    Integer[] array = createArray();
    List<Integer> list = createList();
    Set<Integer> set = createSet();
    Set<Integer> sortedSet = createSortedSet();
    assertEquals(list, toArrayListConverter.convert(array));
    assertEquals(list, toListConverter.convert(array));
    assertEquals(list, toArrayListConverter.convert(list));
    assertEquals(list, toListConverter.convert(list));
    assertTrue(CollectionUtil.equalsIgnoreOrder(list, toArrayListConverter.convert(set)));
    assertTrue(CollectionUtil.equalsIgnoreOrder(list, toListConverter.convert(set)));
    assertEquals(list, toArrayListConverter.convert(sortedSet));
    assertEquals(list, toListConverter.convert(sortedSet));
  }


  @Test
  @SuppressWarnings("rawtypes")
  public void testToSet() throws ConversionException {
    Converter<Object, HashSet> toHashSetConverter = new ToCollectionConverter<>(HashSet.class);
    Converter<Object, Set> toSetConverter = new ToCollectionConverter<>(Set.class);
    Integer[] array = createArray();
    Set<Integer> set = createSet();
    Set<Integer> sortedSet = createSortedSet();
    List<Integer> list = createList();
    assertEquals(set, toHashSetConverter.convert(array));
    assertEquals(set, toSetConverter.convert(array));
    assertEquals(set, toHashSetConverter.convert(list));
    assertEquals(set, toSetConverter.convert(list));
    assertEquals(set, toHashSetConverter.convert(set));
    assertEquals(set, toSetConverter.convert(set));
    assertEquals(set, toHashSetConverter.convert(sortedSet));
    assertEquals(set, toSetConverter.convert(sortedSet));
  }

  @Test
  @SuppressWarnings("rawtypes")
  public void testToSortedSet() throws ConversionException {
    Converter<Object, TreeSet> toTreeSetConverter = new ToCollectionConverter<>(TreeSet.class);
    Converter<Object, SortedSet> toSortedSetConverter = new ToCollectionConverter<>(SortedSet.class);
    List<Integer> list = createList();
    Set<Integer> set = createSet();
    Set<Integer> sortedSet = createSortedSet();
    Integer[] array = createArray();
    assertEquals(sortedSet, toTreeSetConverter.convert(array));
    assertEquals(sortedSet, toSortedSetConverter.convert(array));
    assertEquals(sortedSet, toTreeSetConverter.convert(list));
    assertEquals(sortedSet, toSortedSetConverter.convert(list));
    assertEquals(sortedSet, toTreeSetConverter.convert(set));
    assertEquals(sortedSet, toSortedSetConverter.convert(set));
    assertEquals(sortedSet, toTreeSetConverter.convert(sortedSet));
    assertEquals(sortedSet, toSortedSetConverter.convert(sortedSet));
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static Integer[] createArray() {
    return ArrayUtil.toArray(1, 2, 3);
  }

  private static List<Integer> createList() {
    return CollectionUtil.toList(1, 2, 3);
  }

  private static Set<Integer> createSet() {
    return CollectionUtil.toSet(1, 2, 3);
  }

  private static Set<Integer> createSortedSet() {
    SortedSet<Integer> set = new TreeSet<>();
    set.add(1);
    set.add(2);
    set.add(3);
    return set;
  }

}

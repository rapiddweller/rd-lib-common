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

import com.rapiddweller.common.iterator.IteratorTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link CompressedLongSet}.
 * Created: 18.10.2010 08:35:59
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class CompressedLongSetTest extends IteratorTestCase {

  @Test
  public void testAdd_11() {
    CompressedLongSet set = new CompressedLongSet();
    assertTrue(set.isEmpty());
    set.add(1);
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    set.add(1);
    assertEquals(new LongRange(1L, 1L), set.numbers.get(1L));
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertEquals(1, set.size());
    expectNextElements(set.iterator(), 1L).withNoNext();
  }

  @Test
  public void testAdd_12() {
    CompressedLongSet set = new CompressedLongSet();
    assertTrue(set.isEmpty());

    set.add(1);
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertFalse(set.contains(2L));
    assertEquals(1, set.size());

    set.add(2);
    assertEquals(new LongRange(1L, 2L), set.numbers.get(1L));
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertTrue(set.contains(2L));

    assertEquals(2, set.size());
    expectNextElements(set.iterator(), 1L, 2L).withNoNext();
  }

  @Test
  public void testAdd_21() {
    CompressedLongSet set = new CompressedLongSet();

    set.add(2L);
    assertFalse(set.isEmpty());
    assertFalse(set.contains(1L));
    assertTrue(set.contains(2L));
    assertEquals(1, set.size());

    set.add(1L);
    assertEquals(new LongRange(1L, 2L), set.numbers.get(1L));
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertTrue(set.contains(2L));

    assertEquals(2, set.size());
    expectNextElements(set.iterator(), 1L, 2L).withNoNext();
  }

  @Test
  public void testAdd_13() {
    CompressedLongSet set = new CompressedLongSet();

    set.add(1);
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertFalse(set.contains(3L));

    set.add(3);
    assertEquals(new LongRange(1, 1), set.numbers.get(1L));
    assertEquals(new LongRange(3, 3), set.numbers.get(3L));
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertTrue(set.contains(3L));

    assertEquals(2, set.size());
    expectNextElements(set.iterator(), 1L, 3L).withNoNext();
  }

  @Test
  public void testAdd_31() {
    CompressedLongSet set = new CompressedLongSet();

    set.add(3L);
    assertFalse(set.isEmpty());
    assertFalse(set.contains(1L));
    assertTrue(set.contains(3L));

    set.add(1);
    assertEquals(new LongRange(1, 1), set.numbers.get(1L));
    assertEquals(new LongRange(3, 3), set.numbers.get(3L));
    assertFalse(set.isEmpty());
    assertTrue(set.contains(1L));
    assertTrue(set.contains(3L));

    assertEquals(2, set.size());
    expectNextElements(set.iterator(), 1L, 3L).withNoNext();
  }

  @Test
  public void testAdd_132() {
    CompressedLongSet set = new CompressedLongSet();
    set.add(1L);
    set.add(3L);
    set.add(2L);
    assertEquals(new LongRange(1L, 3L), set.numbers.get(1L));
    assertEquals(3, set.size());
    expectNextElements(set.iterator(), 1L, 2L, 3L).withNoNext();
  }

  @Test
  public void testAdd_1to10() {
    CompressedLongSet set = new CompressedLongSet();
    set.add(1L);
    set.add(3L);
    set.add(6L);
    set.add(9L);
    set.add(2L);
    set.add(7L);
    set.add(10L);
    set.add(4L);
    set.add(8L);
    set.add(5L);
    assertEquals(new LongRange(1L, 10L), set.numbers.get(1L));
    assertEquals(10, set.size());
    expectNextElements(set.iterator(), 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L).withNoNext();
  }


  @Test
  public void testRemove_not() {
    CompressedLongSet set = new CompressedLongSet();
    assertFalse(set.remove(3));
    set.add(2);
    assertFalse(set.remove(3));
    set.add(4);
    assertFalse(set.remove(3));
  }

  @Test
  public void testRemove_exactly() {
    CompressedLongSet set = new CompressedLongSet();
    set.add(2);
    assertTrue(set.remove(2));
    assertEquals(0, set.numbers.size());
    assertTrue(set.isEmpty());
  }

  @Test
  public void testRemove_min() {
    CompressedLongSet set = new CompressedLongSet();
    set.addAll(2, 3);
    assertTrue(set.remove(2));
    assertEquals(1, set.numbers.size());
    assertEquals(new LongRange(3, 3), set.numbers.get(3L));
  }

  @Test
  public void testRemove_max() {
    CompressedLongSet set = new CompressedLongSet();
    set.addAll(4, 5);
    assertTrue(set.remove(5));
    assertEquals(1, set.numbers.size());
    assertEquals(new LongRange(4, 4), set.numbers.get(4L));
  }

  @Test
  public void testRemove_mid() {
    CompressedLongSet set = new CompressedLongSet();
    set.addAll(6, 7, 8);
    assertTrue(set.remove(7));
    assertEquals(2, set.numbers.size());
    assertEquals(new LongRange(6, 6), set.numbers.get(6L));
    assertEquals(new LongRange(8, 8), set.numbers.get(8L));
  }

}

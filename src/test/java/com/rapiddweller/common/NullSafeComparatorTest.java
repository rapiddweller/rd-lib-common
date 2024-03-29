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

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Tests the NullSafeComparator.
 * Created: 20.04.2006 18:01:28
 *
 * @author Volker Bergmann
 */
public class NullSafeComparatorTest {

  @Test
  public void testInstantiation() {
    new NullSafeComparator<Integer>();
  }

  @Test
  public void testComparableComparation() {
    Comparator<Integer> comparator = new NullSafeComparator<>();
    Integer i1 = 1;
    Integer i2 = 2;
    Integer i2d = 2;
    assertEquals(-1, comparator.compare(i1, i2));
    assertEquals(-1, comparator.compare(null, i2));
    assertEquals(1, comparator.compare(i2, i1));
    assertEquals(1, comparator.compare(i2, null));
    assertEquals(0, comparator.compare(i2, i2));
    assertEquals(0, comparator.compare(i2, i2d));
    assertEquals(0, comparator.compare(null, null));
  }

  @Test
  public void testStaticComparableComparation() {
    Integer i1 = 1;
    Integer i2 = 2;
    Integer i2d = 2;
    assertEquals(-1, NullSafeComparator.compare(i1, i2, -1));
    assertEquals(1, NullSafeComparator.compare(i2, null, -1));
    assertEquals(-1, NullSafeComparator.compare(null, i2, -1));
    assertEquals(1, NullSafeComparator.compare(i2, i1, -1));
    assertEquals(0, NullSafeComparator.compare(i2, i2, -1));
    assertEquals(0, NullSafeComparator.compare(i2, i2d, -1));
    assertEquals(0, NullSafeComparator.compare(null, (Integer) null, -1));
  }

  @Test
  public void testDownwardComparation() {
    Comparator<Integer> comparator = new NullSafeComparator<>(NullSafeComparator.NULL_IS_GREATER);
    Integer i1 = 1;
    Integer i2 = 2;
    Integer i2d = 2;
    assertEquals(-1, comparator.compare(i1, i2));
    assertEquals(1, comparator.compare(null, i2));
    assertEquals(1, comparator.compare(i2, i1));
    assertEquals(-1, comparator.compare(i2, null));
    assertEquals(0, comparator.compare(i2, i2));
    assertEquals(0, comparator.compare(i2, i2d));
    assertEquals(0, comparator.compare(null, null));
  }

  @Test
  public void testCollatorComparation() {
    Collator collator = Collator.getInstance(Locale.GERMANY);
    Comparator<String> comparator = new NullSafeComparator<>(collator);
    String s1 = "Alpha";
    String s2 = "Beta";
    String s2d = "Beta";
    assertEquals(-1, comparator.compare(s1, s2));
    assertEquals(-1, comparator.compare(null, s1));
    assertEquals(1, comparator.compare(s2, s1));
    assertEquals(1, comparator.compare(s2, null));
    assertEquals(0, comparator.compare(s2, s2));
    assertEquals(0, comparator.compare(s2, s2d));
    assertEquals(0, comparator.compare(null, null));
    String s0 = "";
    assertEquals(-1, comparator.compare(null, s0));
  }

}

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

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link MarkedMap}.
 * Created: 03.02.2012 16:57:31
 *
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class MarkedMapTest {

  @Test
  public void test() {
    Map<Integer, Integer> map = CollectionUtil.buildMap(1, 11);
    // Test bulk construction
    MarkedMap<Integer, Integer> mmap = new MarkedMap<>(map);
    assertEquals(1, mmap.size());
    assertEquals(11, mmap.get(1).intValue());
    // test marking
    assertFalse(mmap.isMarked(1));
    mmap.mark(1);
    assertTrue(mmap.isMarked(1));
    // add element
    mmap.put(2, 22);
    assertFalse(mmap.isMarked(2));
    // check unmarkedEntries()
    Map<Integer, Integer> unmarkedEntries = mmap.unmarkedEntries();
    assertEquals(1, unmarkedEntries.size());
    assertEquals(2, unmarkedEntries.keySet().iterator().next().intValue());
    // check markedEntries()
    Map<Integer, Integer> markedEntries = mmap.markedEntries();
    assertEquals(1, markedEntries.size());
    assertEquals(1, markedEntries.keySet().iterator().next().intValue());
    // check toString()
    assertEquals("[1=11(+), 2=22(-)]", mmap.toString());
  }

}

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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link NamedValueList}.
 * Created at 09.05.2008 21:19:22
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class NamedValueListTest {

  @Test
  public void testCaseIgnorantList() {
    NamedValueList<Integer> list = NamedValueList.createCaseIgnorantList();
    list.add("ONE", 1);
    assertEquals(1, list.size());
    assertEquals("one", list.getName(0));
    assertEquals(1, list.getValue(0).intValue());
    assertEquals(1, list.someValueOfName("One").intValue());

    list.add("oNE", 11);
    assertEquals(2, list.size());
    assertEquals("one", list.getName(1));
    assertEquals(11, list.getValue(1).intValue());
    assertEquals(11, list.someValueOfName("One").intValue());
  }

  @Test
  public void testCaseInsensitiveList() {
    NamedValueList<Integer> list = NamedValueList.createCaseInsensitiveList();
    list.add("ONE", 1);
    assertEquals(1, list.size());
    assertEquals("ONE", list.getName(0));
    assertEquals(1, list.getValue(0).intValue());
    int index = list.someValueOfName("One");
    assertEquals(1, index);

    list.add("oNE", 11);
    assertEquals(2, list.size());
    assertEquals("oNE", list.getName(1));
    assertEquals(11, list.getValue(1).intValue());
    assertTrue(index == 11 || index == 1);
  }

  @Test
  public void testCaseSensitiveList() {
    NamedValueList<Integer> list = NamedValueList.createCaseSensitiveList();
    list.add("ONE", 1);
    assertEquals(1, list.size());
    assertEquals("ONE", list.getName(0));
    assertEquals(1, list.getValue(0).intValue());
    assertEquals(1, list.someValueOfName("ONE").intValue());
    assertEquals(null, list.someValueOfName("One"));

    list.add("oNE", 11);
    assertEquals(2, list.size());
    assertEquals("oNE", list.getName(1));
    assertEquals(11, list.getValue(1).intValue());
    assertEquals(11, list.someValueOfName("oNE").intValue());
    assertEquals(1, list.someValueOfName("ONE").intValue());
  }

}

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

package com.rapiddweller.common.comparator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link BooleanComparator}.
 * Created at 02.05.2008 15:54:40
 *
 * @author Volker Bergmann
 * @since 0.4.3
 */
public class BooleanComparatorTest {

  @Test
  public void testCompare() {
    BooleanComparator comparator = new BooleanComparator();
    assertEquals(-1, comparator.compare(Boolean.FALSE, Boolean.TRUE));
    assertEquals(0, comparator.compare(Boolean.FALSE, Boolean.FALSE));
    assertEquals(0, comparator.compare(Boolean.TRUE, Boolean.TRUE));
    assertEquals(1, comparator.compare(Boolean.TRUE, Boolean.FALSE));
  }

}

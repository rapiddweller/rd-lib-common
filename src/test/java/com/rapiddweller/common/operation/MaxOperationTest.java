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

package com.rapiddweller.common.operation;

import com.rapiddweller.common.comparator.ReverseComparator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link MaxOperation}.
 * Created: 25.01.2008 18:03:12
 *
 * @author Volker Bergmann
 */
public class MaxOperationTest {

  @Test
  public void testInteger() {
    MaxOperation<Integer> op = new MaxOperation<>();
    assertEquals(Integer.valueOf(2), op.perform(-1, 0, 1, 2));
  }

  @Test
  public void testString() {
    MaxOperation<String> op = new MaxOperation<>();
    assertEquals("charly", op.perform("alpha", "bravo", "charly"));
  }

  @Test
  public void testStringDesc() {
    MaxOperation<String> op = new MaxOperation<>(new ReverseComparator<>());
    assertEquals("alpha", op.perform("alpha", "bravo", "charly"));
  }

}

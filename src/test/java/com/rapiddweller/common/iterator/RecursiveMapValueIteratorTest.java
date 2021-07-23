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

package com.rapiddweller.common.iterator;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.OrderedMap;
import org.junit.Test;

import java.util.Map;

/**
 * Tests the {@link RecursiveMapValueIterator}.
 * Created: 04.08.2013 08:26:19
 *
 * @author Volker Bergmann
 * @since 0.5.24
 */

public class RecursiveMapValueIteratorTest extends IteratorTestCase {

  @SuppressWarnings({"cast", "unchecked"})
  @Test
  public void testPlain() {
    Map<Integer, Integer> map = (Map<Integer, Integer>) CollectionUtil.buildMap(1, 2, 3, 4);
    RecursiveMapValueIterator<Integer> iterator = new RecursiveMapValueIterator<>(map);
    expectNextElements(iterator, 2, 4);
  }

  @SuppressWarnings({"cast", "unchecked"})
  @Test
  public void testPlainRecursion() {
    Map<Integer, Integer> map1 = (Map<Integer, Integer>) CollectionUtil.buildMap(1, 2, 3, 4);
    Map<Integer, Integer> map2 = (Map<Integer, Integer>) CollectionUtil.buildMap(5, 6, 7, 8);
    Map<Integer, ?> map = (Map<Integer, ?>) CollectionUtil.buildMap(11, map1, 13, map2);
    RecursiveMapValueIterator<Integer> iterator = new RecursiveMapValueIterator<>(map);
    expectNextElements(iterator, 2, 4, 6, 8).withNoNext();
  }

  @Test
  public void testMixedRecursion() {
    Map<Integer, ?> map1 = buildMap(1, 2, 3, 4);
    Map<Integer, ?> map2 = buildMap(5, 6, 7, 8);
    Map<Integer, ?> map = buildMap(9, 10, 11, map1, 13, 14, 15, map2, 17, 18);
    RecursiveMapValueIterator<Integer> iterator = new RecursiveMapValueIterator<>(map);
    expectNextElements(iterator, 10, 2, 4, 14, 6, 8, 18).withNoNext();
  }

  private static OrderedMap<Integer, ?> buildMap(Object... keyValuePairs) {
    OrderedMap<Integer, Object> result = new OrderedMap<>();
    for (int i = 0; i < keyValuePairs.length; i += 2) {
      Integer key = (Integer) keyValuePairs[i];
      Object value = keyValuePairs[i + 1];
      result.put(key, value);
    }
    return result;
  }

}

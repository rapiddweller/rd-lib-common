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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Compares classes based on a predefined order.
 * Created: 17.02.2010 13:07:38
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class TypeComparator implements Comparator<Class<?>> {

  private final Map<Class<?>, Integer> indexes;

  /**
   * Instantiates a new Type comparator.
   *
   * @param orderedClasses the ordered classes
   */
  public TypeComparator(Class<?>... orderedClasses) {
    indexes = new HashMap<>();
    int count = 0;
    for (Class<?> type : orderedClasses) {
      indexes.put(type, ++count);
    }
  }

  @Override
  public int compare(Class<?> c1, Class<?> c2) {
    if (c1 == c2) {
      return 0;
    }
    int i1 = indexOfClass(c1);
    int i2 = indexOfClass(c2);
    return IntComparator.compare(i1, i2);
  }

  private int indexOfClass(Class<?> type) {
    Integer result = indexes.get(type);
    if (result == null) {
      throw new IllegalArgumentException("Not a supported type: " + type);
    }
    return result;
  }

}

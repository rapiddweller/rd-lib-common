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

import com.rapiddweller.common.ComparableComparator;

import java.util.Comparator;

/**
 * Compares two arrays by the first 'min-length' array elements with a Comparator.
 * Created: 22.05.2007 07:07:17
 * @param <E> the component type of the arrays to be compared
 * @author Volker Bergmann
 * @since 0.1
 */
public class ArrayComparator<E> implements Comparator<E[]> {

  private final Comparator<E> elementComparator;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ArrayComparator() {
    this(new ComparableComparator());
  }

  public ArrayComparator(Comparator<E> elementComparator) {
    this.elementComparator = elementComparator;
  }

  @Override
  public int compare(E[] array1, E[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    }
    if (array2 == null) {
      return 1;
    }
    // iterate through the elements and compara them one by one
    int minLength = Math.min(array1.length, array2.length);
    for (int i = 0; i < minLength; i++) {
      int elementComparison = elementComparator.compare(array1[i], array2[i]);
      // if element #i differs then return the difference
      if (elementComparison != 0) {
        return elementComparison;
      }
    }
    // All elements from 0 to minLength are equals - return the longer array as greater
    // the arrays have equal size and equal elements
    return Integer.compare(array1.length, array2.length);
  }

}

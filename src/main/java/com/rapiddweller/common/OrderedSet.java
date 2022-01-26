/*
 * Copyright (C) 2004-2022 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * {@link Set} implementation that tracks the order in which elements where added
 * and returns them in that order by the <i>iterator()</i> method.
 * This is useful for all cases in which elements need to be unique
 * but processed in the original order.
 * Created at 28.02.2009 12:26:35
 * @param <E> the type of the collection's elements
 * @author Volker Bergmann
 * @since 0.4.8
 */
public class OrderedSet<E> implements Set<E> {

  private final OrderedMap<E, E> map;

  public OrderedSet() {
    map = new OrderedMap<>();
  }

  public OrderedSet(int initialCapacity) {
    map = new OrderedMap<>(initialCapacity);
  }

  public OrderedSet(int initialCapacity, float loadFactor) {
    map = new OrderedMap<>(initialCapacity, loadFactor);
  }

  public OrderedSet(Collection<E> source) {
    map = new OrderedMap<>(source.size());
    addAll(source);
  }

  // Set interface implementation ------------------------------------------------------------------------------------

  @Override
  public boolean add(E item) {
    return (map.put(item, item) == null);
  }

  @Override
  public boolean addAll(Collection<? extends E> source) {
    boolean changed = false;
    for (E item : source) {
      changed |= add(item);
    }
    return changed;
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public boolean contains(Object item) {
    return map.containsKey(item);
  }

  @Override
  public boolean containsAll(Collection<?> items) {
    for (Object o : items) {
      if (!map.containsKey(o)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public boolean remove(Object item) {
    return (map.remove(item) != null);
  }

  @Override
  public boolean removeAll(Collection<?> items) {
    boolean changed = false;
    for (Object item : items) {
      changed |= remove(item);
    }
    return changed;
  }

  @Override
  public boolean retainAll(Collection<?> items) {
    boolean changed = false;
    for (E item : map.keySet()) {
      if (!items.contains(item)) {
        map.remove(item);
        changed = true;
      }
    }
    return changed;
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public Object[] toArray() {
    return map.keySet().toArray();
  }

  @Override
  public <T> T[] toArray(T[] array) {
    return map.keySet().toArray(array);
  }

  // List interface --------------------------------------------------------------------------------------------------

  public E get(int index) {
    return map.valueAt(index);
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public int hashCode() {
    return map.hashCode();
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return (this.map.equals(((OrderedSet) obj).map));
  }

  @Override
  public String toString() {
    return map.values().toString();
  }

}

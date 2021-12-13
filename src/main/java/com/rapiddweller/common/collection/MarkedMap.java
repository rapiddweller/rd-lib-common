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

import com.rapiddweller.common.OrderedMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link Map} proxy which allow to attach a mark to each entry.
 * Created: 03.02.2012 16:40:07
 * @param <K> the maps's key type
 * @param <V> the map's value type
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class MarkedMap<K, V> implements Map<K, V> {

  private final Map<K, V> realMap;
  private Map<K, Boolean> marks;

  public MarkedMap() {
    this(new HashMap<>());
  }

  public MarkedMap(Map<K, V> realMap) {
    this.realMap = realMap;
    this.marks = new HashMap<>(realMap.size());
    for (K key : realMap.keySet()) {
      marks.put(key, false);
    }
  }

  // marker interface ------------------------------------------------------------------------------------------------

  public void mark(K key) {
    marks.put(key, true);
  }

  public boolean unmark(K key) {
    return marks.put(key, false);
  }

  public boolean isMarked(K key) {
    return marks.get(key);
  }

  public Map<K, V> unmarkedEntries() {
    Map<K, V> result = new OrderedMap<>();
    for (Map.Entry<K, V> entry : realMap.entrySet()) {
      if (!isMarked(entry.getKey())) {
        result.put(entry.getKey(), entry.getValue());
      }
    }
    return result;
  }

  public Map<K, V> markedEntries() {
    Map<K, V> result = new OrderedMap<>();
    for (Map.Entry<K, V> entry : realMap.entrySet()) {
      if (isMarked(entry.getKey())) {
        result.put(entry.getKey(), entry.getValue());
      }
    }
    return result;
  }


  // Map interface implementation ------------------------------------------------------------------------------------

  @Override
  public void clear() {
    realMap.clear();
    marks.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return realMap.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return realMap.containsValue(value);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return realMap.entrySet();
  }

  @Override
  public V get(Object value) {
    return realMap.get(value);
  }

  @Override
  public boolean isEmpty() {
    return realMap.isEmpty();
  }

  @Override
  public Set<K> keySet() {
    return realMap.keySet();
  }

  @Override
  public V put(K key, V value) {
    V result = realMap.put(key, value);
    marks.put(key, false);
    return result;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> otherMap) {
    realMap.putAll(otherMap);
    this.marks = new HashMap<>(realMap.size());
    for (K key : realMap.keySet()) {
      marks.put(key, false);
    }
  }

  @Override
  public V remove(Object value) {
    V result = realMap.remove(value);
    marks.remove(value);
    return result;
  }

  @Override
  public int size() {
    return realMap.size();
  }

  @Override
  public Collection<V> values() {
    return realMap.values();
  }


  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((marks == null) ? 0 : marks.hashCode());
    result = prime * result + ((realMap == null) ? 0 : realMap.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    @SuppressWarnings("unchecked")
    MarkedMap<K, V> that = (MarkedMap<K, V>) other;
    return this.marks.equals(that.marks) && this.realMap.equals(that.realMap);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    boolean first = true;
    for (Map.Entry<K, V> entry : realMap.entrySet()) {
      if (!first) {
        builder.append(", ");
      }
      builder.append(entry.getKey()).append('=').append(entry.getValue());
      builder.append(marks.get(entry.getKey()) ? "(+)" : "(-)");
      first = false;
    }
    builder.append("]");
    return builder.toString();
  }

}

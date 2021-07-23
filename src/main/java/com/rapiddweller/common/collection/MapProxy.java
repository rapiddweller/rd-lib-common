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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Proxy class for a {@link Map}.
 * Created: 12.12.2012 11:49:35
 *
 * @param <M> The type of map to wrap
 * @param <K> the key type
 * @param <V> the value type
 * @author Volker Bergmann
 * @since 0.5.21
 */
public class MapProxy<M extends Map<K, V>, K, V> implements Map<K, V> {

  /**
   * The Real map.
   */
  protected final M realMap;

  /**
   * Instantiates a new Map proxy.
   *
   * @param realMap the real map
   */
  protected MapProxy(M realMap) {
    this.realMap = realMap;
  }

  /**
   * Gets real map.
   *
   * @return the real map
   */
  public Object getRealMap() {
    return realMap;
  }

  @Override
  public void clear() {
    realMap.clear();
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
  public Set<java.util.Map.Entry<K, V>> entrySet() {
    return realMap.entrySet();
  }

  @Override
  public V get(Object key) {
    return realMap.get(key);
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
    return realMap.put(key, value);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    realMap.putAll(m);
  }

  @Override
  public V remove(Object key) {
    return realMap.remove(key);
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
    return realMap.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return realMap.equals(o);
  }

  @Override
  public String toString() {
    return realMap.toString();
  }

}

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

package com.rapiddweller.common.accessor;

import com.rapiddweller.common.Accessor;
import com.rapiddweller.common.NullSafeComparator;

import java.util.Map;

/**
 * Accesses Values from a Map.
 * Created: 11.03.2006 12:39:07
 *
 * @param <C> the object type to access
 * @param <K> the the value type
 * @param <V> the key type
 * @author Volker Bergmann
 */
public class MapAccessor<C extends Map<K, V>, K, V> implements Accessor<C, V> {

  /**
   * the key of the object to look up; null is supported.
   */
  private final K key;

  /**
   * Instantiates a new Map accessor.
   *
   * @param key the key
   */
  public MapAccessor(K key) {
    this.key = key;
  }

  // Accessor interface ----------------------------------------------------------------------------------------------

  @Override
  public V getValue(C item) {
    return item.get(key);
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final MapAccessor that = (MapAccessor) o;
    return NullSafeComparator.equals(this.key, that.key);
  }

  @Override
  public int hashCode() {
    return (key != null ? key.hashCode() : 0);
  }
}

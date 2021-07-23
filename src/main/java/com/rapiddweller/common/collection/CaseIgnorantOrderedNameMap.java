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

import java.util.Map;

/**
 * Maps name strings to objects ignoring the capitalization of the name.
 * Created: 12.12.2012 11:08:44
 *
 * @param <E> the type of the collection's elements
 * @author Volker Bergmann
 * @since 0.5.21
 */
public class CaseIgnorantOrderedNameMap<E> extends OrderedMap<String, E> {

  private static final long serialVersionUID = -3134506770888057108L;

  // constructors + factory methods ----------------------------------------------------------------------------------

  /**
   * Instantiates a new Case ignorant ordered name map.
   */
  public CaseIgnorantOrderedNameMap() {
  }

  /**
   * Instantiates a new Case ignorant ordered name map.
   *
   * @param that the that
   */
  public CaseIgnorantOrderedNameMap(Map<String, E> that) {
    super(that);
  }

  // Map interface implementation ------------------------------------------------------------------------------------

  @Override
  public boolean containsKey(Object key) {
    return containsKey((String) key);
  }

  /**
   * Contains key boolean.
   *
   * @param key the key
   * @return the boolean
   */
  public boolean containsKey(String key) {
    return super.containsKey(normalizeKey(key));
  }

  @Override
  public E get(Object key) {
    return get((String) key);
  }

  /**
   * Get e.
   *
   * @param key the key
   * @return the e
   */
  public E get(String key) {
    return super.get(normalizeKey(key));
  }

  @Override
  public Map.Entry<String, E> getEntry(String key) {
    String normalizedKey = normalizeKey(key);
    E value = super.get(normalizedKey);
    return new MapEntry<>(normalizedKey, value);
  }

  @Override
  public E put(String key, E value) {
    return super.put(normalizeKey(key), value);
  }

  /**
   * Remove e.
   *
   * @param key the key
   * @return the e
   */
  public E remove(String key) {
    return super.remove(normalizeKey(key));
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static String normalizeKey(String key) {
    return (key != null ? key.toLowerCase() : key);
  }

}

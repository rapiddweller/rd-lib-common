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

import java.util.List;
import java.util.Map;

/**
 * A map that assigns names to Objects and keeps entries
 * in the order in which they were inserted.
 * Created at 14.04.2008 09:49:34
 *
 * @param <E> the type of the collection's elements
 * @author Volker Bergmann
 * @since 0.5.2
 */
public class OrderedNameMap<E> extends MapProxy<OrderedMap<String, E>, String, E> {

  /**
   * caseSupport setting which respects capitalization
   */
  private static final int CASE_SENSITIVE = 0;

  /**
   * caseSupport setting which preserves capitalization for stored entries but
   */
  private static final int CASE_INSENSITIVE = 1;
  private static final int CASE_IGNORANT = 2;

  private final int caseSupport;

  // constructors + factory methods ----------------------------------------------------------------------------------

  /**
   * Instantiates a new Ordered name map.
   */
  public OrderedNameMap() {
    this(CASE_SENSITIVE);
  }

  /**
   * Instantiates a new Ordered name map.
   *
   * @param caseSupport the case support
   */
  public OrderedNameMap(int caseSupport) {
    super(OrderedNameMap.createRealMap(caseSupport));
    this.caseSupport = caseSupport;
  }

  /**
   * Instantiates a new Ordered name map.
   *
   * @param that the that
   */
  public OrderedNameMap(OrderedNameMap<E> that) {
    super(OrderedNameMap.createRealMap(that.caseSupport));
    this.caseSupport = that.caseSupport;
    putAll(that);
  }

  private static <T> OrderedMap<String, T> createRealMap(int caseSupport) {
    switch (caseSupport) {
      case CASE_SENSITIVE:
        return new CaseSensitiveOrderedNameMap<>();
      case CASE_INSENSITIVE:
        return new CaseInsensitiveOrderedNameMap<>();
      case CASE_IGNORANT:
        return new CaseIgnorantOrderedNameMap<>();
      default:
        throw new IllegalArgumentException("Illegal caseSupport setting: " + caseSupport);
    }
  }

  /**
   * Create case sensitive map ordered name map.
   *
   * @param <T> the type parameter
   * @return the ordered name map
   */
  public static <T> OrderedNameMap<T> createCaseSensitiveMap() {
    return new OrderedNameMap<>(CASE_SENSITIVE);
  }

  /**
   * Create case insensitive map ordered name map.
   *
   * @param <T> the type parameter
   * @return the ordered name map
   */
  public static <T> OrderedNameMap<T> createCaseInsensitiveMap() {
    return new OrderedNameMap<>(CASE_INSENSITIVE);
  }

  /**
   * Create case ignorant map ordered name map.
   *
   * @param <T> the type parameter
   * @return the ordered name map
   */
  public static <T> OrderedNameMap<T> createCaseIgnorantMap() {
    return new OrderedNameMap<>(CASE_IGNORANT);
  }

  /**
   * Value at e.
   *
   * @param index the index
   * @return the e
   */
  public E valueAt(int index) {
    return realMap.valueAt(index);
  }

  /**
   * Index of value int.
   *
   * @param value the value
   * @return the int
   */
  public int indexOfValue(E value) {
    return realMap.indexOfValue(value);
  }

  /**
   * Gets entry.
   *
   * @param key the key
   * @return the entry
   */
  public Map.Entry<String, E> getEntry(String key) {
    return realMap.getEntry(key);
  }

  /**
   * Equals ignore order boolean.
   *
   * @param that the that
   * @return the boolean
   */
  public boolean equalsIgnoreOrder(Map<String, E> that) {
    return realMap.equalsIgnoreOrder(that);
  }

  @Override
  public List<E> values() {
    return realMap.values();
  }

}

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

import com.rapiddweller.common.HeavyweightIterator;
import com.rapiddweller.common.HeavyweightTypedIterable;

import java.util.Iterator;

/**
 * {@link Iterable} proxy that wraps an untyped Iterable and adds type information.
 * Created: 02.09.2007 23:29:10
 *
 * @param <E> the type to wrap
 * @author Volker Bergmann
 */
public class TypedIterableProxy<E> implements HeavyweightTypedIterable<E> {

  private final Class<E> type;
  private final Iterable<E> iterable;

  /**
   * Instantiates a new Typed iterable proxy.
   *
   * @param type     the type
   * @param iterable the iterable
   */
  public TypedIterableProxy(Class<E> type, Iterable<E> iterable) {
    this.type = type;
    this.iterable = iterable;
  }

  @Override
  public Class<E> getType() {
    return type;
  }

  @Override
  public HeavyweightIterator<E> iterator() {
    Iterator<E> iterator = iterable.iterator();
    return new HeavyweightIteratorProxy<>(iterator);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '[' + iterable + " -> " + type.getSimpleName() + ']';
  }
}

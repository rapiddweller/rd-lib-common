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

import java.util.Iterator;

/**
 * Proxy implementation for an {@link Iterable}.
 * Created: 08.03.2011 11:48:21
 *
 * @param <E> the type to iterate
 * @author Volker Bergmann
 * @since 0.5.8
 */
public abstract class IterableProxy<E> implements Iterable<E> {

  /**
   * The Source.
   */
  protected Iterable<E> source;

  /**
   * Instantiates a new Iterable proxy.
   *
   * @param source the source
   */
  public IterableProxy(Iterable<E> source) {
    this.source = source;
  }

  @Override
  public Iterator<E> iterator() {
    return source.iterator();
  }

  @Override
  public String toString() {
    return source.toString();
  }

}

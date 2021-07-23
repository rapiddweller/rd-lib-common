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
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Allows linear iteration over nested {@link Set}s using a plain {@link Iterator}.
 * Created: 04.08.2013 07:58:05
 *
 * @param <E> the type to iterate
 * @author Volker Bergmann
 * @since 0.5.24
 */
public class RecursiveMapValueIterator<E> implements Iterator<E> {

  private final Stack<Iterator<?>> iterators;
  private E next;
  private boolean hasNext;

  /**
   * Instantiates a new Recursive map value iterator.
   *
   * @param root the root
   */
  public RecursiveMapValueIterator(Map<?, ?> root) {
    this.iterators = new Stack<>();
    this.iterators.push(root.values().iterator());
    forward();
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }

  @Override
  public E next() {
    E result = next;
    forward();
    return result;
  }

  @Override
  public void remove() {
    iterators.peek().remove();
  }

  @SuppressWarnings("unchecked")
  private void forward() {
    Iterator<?> iterator = iterators.peek();
    if (iterator.hasNext()) {
      Object candidate = iterator.next();
      if (candidate instanceof Map) {
        iterators.push(((Map<?, ?>) candidate).values().iterator());
        forward();
      } else {
        hasNext = true;
        next = (E) candidate;
      }
    } else if (iterators.size() > 1) {
      iterators.pop();
      forward();
    } else {
      this.hasNext = false;
      this.next = null;
    }
  }

}

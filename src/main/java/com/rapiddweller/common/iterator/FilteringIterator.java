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

import com.rapiddweller.common.Filter;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Filters elements of another {@link Iterator} or {@link BidirectionalIterator}
 * by a {@link Filter} element.
 * Created: 08.05.2007 19:37:33
 * @param <E> the type to iterate
 * @author Volker Bergmann
 */
public class FilteringIterator<E> extends BidirectionalIteratorProxy<E> {

  protected Filter<E> filter;

  private E next;
  private E previous;

  public FilteringIterator(Iterator<E> realIterator, Filter<E> filter) {
    this(new JDKIteratorWrapper<>(realIterator), filter);
  }

  public FilteringIterator(BidirectionalIterator<E> realIterator, Filter<E> filter) {
    super(realIterator);
    this.filter = filter;
  }

  @Override
  public boolean hasNext() {
    if (next != null) {
      return true;
    }
    while (super.hasNext()) {
      E tmp = super.next();
      if (filter.accept(tmp)) {
        this.next = tmp;
        return true;
      }
    }
    return false;
  }

  @Override
  public E next() {
    if (next == null && !hasNext()) {
      throw new NoSuchElementException("Nothing more to iterate");
    }
    E result = next;
    next = null;
    previous = null;
    return result;
  }

  @Override
  public E first() {
    next = null;
    previous = null;
    E first = super.first();
    if (filter.accept(first)) {
      return first;
    }
    while (super.hasNext()) {
      E tmp = super.next();
      if (filter.accept(tmp)) {
        return tmp;
      }
    }
    return null;
  }

  @Override
  public boolean hasPrevious() {
    if (previous != null) {
      return true;
    }
    while (super.hasPrevious()) {
      E tmp = super.previous();
      if (filter.accept(tmp)) {
        this.previous = tmp;
        return true;
      }
    }
    return false;
  }

  @Override
  public E previous() {
    if (previous == null && !hasPrevious()) {
      throw new NoSuchElementException("Nothing more to iterate");
    }
    E result = previous;
    previous = null;
    next = null;
    return result;
  }

  @Override
  public E last() {
    next = null;
    previous = null;
    E last = super.last();
    if (filter.accept(last)) {
      return last;
    }
    while (super.hasPrevious()) {
      E tmp = super.previous();
      if (filter.accept(tmp)) {
        return tmp;
      }
    }
    return null;
  }

}

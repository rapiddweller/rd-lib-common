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

/**
 * Proxy for a {@link BidirectionalIterator}.
 * Created: 12.05.2007 23:18:31
 *
 * @param <E> the type to iterate
 * @author Volker Bergmann
 */
public abstract class BidirectionalIteratorProxy<E> implements BidirectionalIterator<E> {

  /**
   * The Real iterator.
   */
  protected BidirectionalIterator<E> realIterator;

  /**
   * Instantiates a new Bidirectional iterator proxy.
   *
   * @param realIterator the real iterator
   */
  public BidirectionalIteratorProxy(BidirectionalIterator<E> realIterator) {
    this.realIterator = realIterator;
  }

  @Override
  public E first() {
    return realIterator.first();
  }

  @Override
  public boolean hasPrevious() {
    return realIterator.hasPrevious();
  }

  @Override
  public E previous() {
    return realIterator.previous();
  }

  @Override
  public E last() {
    return realIterator.last();
  }

  @Override
  public boolean hasNext() {
    return realIterator.hasNext();
  }

  @Override
  public E next() {
    return realIterator.next();
  }

  @Override
  public void remove() {
    realIterator.remove();
  }

}

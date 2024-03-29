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

import com.rapiddweller.common.exception.ExceptionFactory;

import java.util.Iterator;

/**
 * Wraps a JDK {@link Iterator} into a {@link BidirectionalIterator},
 * making the unsupported operations throw an {@link UnsupportedOperationException}.
 * Created: 12.06.2007 19:51:44
 * @param <E> the type to be wrapped
 * @author Volker Bergmann
 */
public class JDKIteratorWrapper<E> implements BidirectionalIterator<E> {

  public static final String OPERATION_NOT_SUPPORTED = "Operation not supported";

  private final Iterator<E> realIterator;

  public JDKIteratorWrapper(Iterator<E> realIterator) {
    this.realIterator = realIterator;
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

  @Override
  public E first() {
    throw ExceptionFactory.getInstance().programmerUnsupported(OPERATION_NOT_SUPPORTED);
  }

  @Override
  public boolean hasPrevious() {
    throw ExceptionFactory.getInstance().programmerUnsupported(OPERATION_NOT_SUPPORTED);
  }

  @Override
  public E previous() {
    throw ExceptionFactory.getInstance().programmerUnsupported(OPERATION_NOT_SUPPORTED);
  }

  @Override
  public E last() {
    throw ExceptionFactory.getInstance().programmerUnsupported(OPERATION_NOT_SUPPORTED);
  }

}

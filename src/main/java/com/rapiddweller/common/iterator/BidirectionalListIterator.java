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

import java.util.List;
import java.util.NoSuchElementException;

/**
 * A {@link BidirectionalIterator} for {@link List}s.
 * Created: 08.05.2007 19:50:20
 * @param <E> the type to iterate
 * @author Volker Bergmann
 */
public class BidirectionalListIterator<E> implements BidirectionalIterator<E> {

  private final List<E> list;
  private int index;

  public BidirectionalListIterator(List<E> list) {
    this.list = list;
    this.index = -1;
  }

  @Override
  public E first() {
    index = 0;
    return list.get(index);
  }

  @Override
  public boolean hasPrevious() {
    return (index > 0);
  }

  @Override
  public E previous() {
    if (!hasPrevious()) {
      throw ExceptionFactory.getInstance().operationFailed("No previous object exists", null);
    }
    index--;
    return list.get(index);
  }

  @Override
  public E last() {
    index = list.size() - 1;
    return list.get(index);
  }

  @Override
  public boolean hasNext() {
    return (index < list.size() - 1);
  }

  @Override
  public E next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No next object exists");
    }
    index++;
    return list.get(index);
  }

  @Override
  public void remove() {
    throw ExceptionFactory.getInstance().programmerUnsupported("Not implemented");
  }

}

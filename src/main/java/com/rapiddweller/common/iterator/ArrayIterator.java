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

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@link Iterator} implementation that iterates the elements of an array.
 * Created at 30.06.2009 09:26:55
 * @param <E> the type to iterate
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ArrayIterator<E> implements Iterator<E> {

  private final E[] array;
  private int cursor;

  public ArrayIterator(E[] array) {
    Assert.notNull(array, "array");
    this.array = array;
    this.cursor = 0;
  }

  @Override
  public boolean hasNext() {
    return (cursor < array.length);
  }

  @Override
  public E next() {
    if (cursor >= array.length) {
      throw new NoSuchElementException();
    } else {
      return array[cursor++];
    }
  }

  @Override
  public void remove() {
    throw ExceptionFactory.getInstance().illegalOperation("remove() is not supported by " + getClass());
  }

}

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

package com.rapiddweller.common;

import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.lang.reflect.Array;

/**
 * Helper class for building arrays.
 * @param <E> the component type of the array
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class ArrayBuilder<E> {

  private static final int DEFAULT_INITIAL_CAPACITY = 10;

  private final Class<E> componentType;
  private E[] buffer;
  private int elementCount;

  public ArrayBuilder(Class<E> componentType) {
    this(componentType, DEFAULT_INITIAL_CAPACITY);
  }

  public ArrayBuilder(Class<E> componentType, int initialCapacity) {
    this.componentType = componentType;
    this.buffer = createBuffer(initialCapacity);
  }

  public ArrayBuilder<E> add(E element) {
    if (buffer == null) {
      throw ExceptionFactory.getInstance().illegalOperation("ArrayBuilder cannot be reused after invoking toArray()");
    }
    if (elementCount >= buffer.length - 1) {
      E[] newBuffer = createBuffer(buffer.length * 2);
      System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
      buffer = newBuffer;
    }
    buffer[elementCount++] = element;
    return this;
  }

  @SafeVarargs
  public final void addAllIfNotContained(E... elements) {
    for (E element : elements) {
      addIfNotContained(element);
    }
  }

  public void addIfNotContained(E element) {
    if (!contains(element)) {
      add(element);
    }
  }

  public boolean contains(E element) {
    for (int i = 0; i < elementCount; i++) {
      if (NullSafeComparator.equals(buffer[i], element)) {
        return true;
      }
    }
    return false;
  }

  public void addAll(E[] elements) {
    for (E element : elements) {
      add(element);
    }
  }

  public E[] toArray() {
    E[] result = ArrayUtil.newInstance(componentType, elementCount);
    System.arraycopy(buffer, 0, result, 0, elementCount);
    elementCount = 0;
    buffer = null;
    return result;
  }

  public int size() {
    return elementCount;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < elementCount; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    }
    return builder.toString();
  }

  // private helpers -------------------------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  private E[] createBuffer(int initialCapacity) {
    return (E[]) Array.newInstance(componentType, initialCapacity);
  }

}

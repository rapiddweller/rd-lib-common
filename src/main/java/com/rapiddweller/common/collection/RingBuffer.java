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

import com.rapiddweller.common.ArrayUtil;

/**
 * Implements a generic ring buffer.<br><br>
 * Created: 25.11.2017 23:34:53
 *
 * @param <E> the type of object to be buffered
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class RingBuffer<E> {

  /**
   * The Buffer.
   */
  protected final E[] buffer;
  private int cursor;
  private int size;

  /**
   * Instantiates a new Ring buffer.
   *
   * @param componentClass the component class
   * @param capacity       the capacity
   */
  public RingBuffer(Class<E> componentClass, int capacity) {
    this.buffer = ArrayUtil.newInstance(componentClass, capacity);
    this.cursor = 0;
    this.size = 0;
  }

  /**
   * Gets capacity.
   *
   * @return the capacity
   */
  public int getCapacity() {
    return buffer.length;
  }

  /**
   * Size int.
   *
   * @return the int
   */
  public int size() {
    return size;
  }

  /**
   * Is filled boolean.
   *
   * @return the boolean
   */
  public boolean isFilled() {
    return (size == buffer.length);
  }

  /**
   * Contains boolean.
   *
   * @param object the object
   * @return the boolean
   */
  public boolean contains(E object) {
    for (Object o : buffer) {
      if (o != null && o.equals(object)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Add e.
   *
   * @param object the object
   * @return the e
   */
  public E add(E object) {
    E oldComponent = buffer[cursor];
    buffer[cursor++] = object;
    if (cursor == buffer.length) {
      cursor = 0;
    }
    if (size < buffer.length) {
      size++;
    }
    return oldComponent;
  }

  /**
   * Get e.
   *
   * @param index the index
   * @return the e
   */
  public E get(int index) {
    if (index > size - 1) {
      return null;
    }
    int offset = cursor - index - 1;
    if (offset < 0) {
      offset += buffer.length;
    }
    return buffer[offset];
  }

}

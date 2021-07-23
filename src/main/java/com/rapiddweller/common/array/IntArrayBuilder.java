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

package com.rapiddweller.common.array;

import com.rapiddweller.common.converter.ToStringConverter;

/**
 * Helper class for constructing int arrays.
 * Created: 27.12.2010 07:45:22
 *
 * @author Volker Bergmann
 * @since 1.0.6
 */
public class IntArrayBuilder {

  private static final int DEFAULT_INITIAL_CAPACITY = 10;

  // constructors ------------------------------------------------------------

  /**
   * The Buffer.
   */
  protected int[] buffer;
  /**
   * The Item count.
   */
  protected int itemCount;

  // constructors ------------------------------------------------------------

  /**
   * Instantiates a new Int array builder.
   */
  public IntArrayBuilder() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Instantiates a new Int array builder.
   *
   * @param initialCapacity the initial capacity
   */
  public IntArrayBuilder(int initialCapacity) {
    this.buffer = createBuffer(initialCapacity);
  }

  // interface ---------------------------------------------------------------

  /**
   * Length int.
   *
   * @return the int
   */
  public int length() {
    return itemCount;
  }

  /**
   * Get int.
   *
   * @param index the index
   * @return the int
   */
  public int get(int index) {
    return this.buffer[index];
  }

  /**
   * Set.
   *
   * @param index the index
   * @param value the value
   */
  public void set(int index, int value) {
    if (index < this.buffer.length) {
      this.buffer[index] = value;
    } else {
      for (int i = buffer.length; i < index; i++) {
        add(0);
      }
      add(value);
    }
  }

  /**
   * Add int array builder.
   *
   * @param item the item
   * @return the int array builder
   */
  public IntArrayBuilder add(int item) {
    if (this.buffer == null) {
      throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
    }
    if (itemCount >= buffer.length - 1) {
      int[] newBuffer = createBuffer(buffer.length * 2);
      System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
      this.buffer = newBuffer;
    }
    buffer[itemCount++] = item;
    return this;
  }

  /**
   * Add all.
   *
   * @param elements the elements
   */
  public void addAll(int[] elements) {
    addAll(elements, 0, elements.length);
  }

  /**
   * Add all.
   *
   * @param elements  the elements
   * @param fromIndex the from index
   * @param toIndex   the to index
   */
  public void addAll(int[] elements, int fromIndex, int toIndex) {
    for (int i = fromIndex; i < toIndex; i++) {
      add(elements[i]);
    }
  }

  // private helper method -------------------------------------------------------------------------------------------

  private static int[] createBuffer(int capacity) {
    return new int[capacity];
  }

  /**
   * Get and delete buffer int [ ].
   *
   * @return the int [ ]
   */
  public int[] getAndDeleteBuffer() {
    if (this.buffer == null) {
      throw new UnsupportedOperationException("buffer already deleted");
    }
    return this.buffer;
  }

  /**
   * To array int [ ].
   *
   * @return the int [ ]
   */
  public int[] toArray() {
    if (this.buffer == null) {
      throw new UnsupportedOperationException("buffer was deleted");
    }
    int[] result = new int[this.itemCount];
    System.arraycopy(buffer, 0, result, 0, this.itemCount);
    this.itemCount = 0;
    buffer = null;
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < itemCount; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(ToStringConverter.convert(buffer[i], ""));
    }
    return builder.toString();
  }

}

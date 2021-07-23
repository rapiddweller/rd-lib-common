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
 * Parent class for classes that manage a dynamically increasing byte array.
 * Created: 02.04.2015 11:04:03
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public abstract class AbstractByteArray {

  private static final int DEFAULT_INITIAL_CAPACITY = 10;

  /**
   * The Buffer.
   */
  protected byte[] buffer;
  /**
   * The Item count.
   */
  protected int itemCount;

  // constructors ------------------------------------------------------------

  /**
   * Instantiates a new Abstract byte array.
   */
  public AbstractByteArray() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Instantiates a new Abstract byte array.
   *
   * @param initialCapacity the initial capacity
   */
  public AbstractByteArray(int initialCapacity) {
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
   * Get byte.
   *
   * @param index the index
   * @return the byte
   */
  public byte get(int index) {
    return this.buffer[index];
  }

  /**
   * Set.
   *
   * @param index the index
   * @param value the value
   */
  public void set(int index, byte value) {
    if (index < this.buffer.length) {
      this.buffer[index] = value;
    } else {
      for (int i = buffer.length; i < index; i++) {
        add((byte) 0);
      }
      add(value);
    }
  }

  /**
   * Add abstract byte array.
   *
   * @param item the item
   * @return the abstract byte array
   */
  public AbstractByteArray add(byte item) {
    if (itemCount >= buffer.length - 1) {
      byte[] newBuffer = createBuffer(buffer.length * 2);
      System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
      buffer = newBuffer;
    }
    buffer[itemCount++] = item;
    return this;
  }

  /**
   * Add all.
   *
   * @param elements the elements
   */
  public void addAll(byte[] elements) {
    addAll(elements, 0, elements.length);
  }

  /**
   * Add all.
   *
   * @param elements  the elements
   * @param fromIndex the from index
   * @param toIndex   the to index
   */
  public void addAll(byte[] elements, int fromIndex, int toIndex) {
    for (int i = fromIndex; i < toIndex; i++) {
      add(elements[i]);
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < itemCount; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    }
    return builder.toString();
  }

  // private helper method -------------------------------------------------------------------------------------------

  private static byte[] createBuffer(int capacity) {
    return new byte[capacity];
  }

}

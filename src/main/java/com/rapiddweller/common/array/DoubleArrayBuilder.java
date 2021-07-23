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
 * Helper class for constructing double arrays.
 * Created: 30.12.2013 21:53:43
 *
 * @author Volker Bergmann
 * @since 0.5.26
 */
public class DoubleArrayBuilder {

  private static final int DEFAULT_INITIAL_CAPACITY = 10;

  private double[] buffer;
  private int itemCount;

  /**
   * Instantiates a new Double array builder.
   */
  public DoubleArrayBuilder() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Instantiates a new Double array builder.
   *
   * @param initialCapacity the initial capacity
   */
  public DoubleArrayBuilder(int initialCapacity) {
    this.buffer = createBuffer(initialCapacity);
  }

  /**
   * Clear.
   */
  public void clear() {
    this.itemCount = 0;
  }

  /**
   * Get double.
   *
   * @param index the index
   * @return the double
   */
  public double get(int index) {
    return buffer[index];
  }

  /**
   * Add double array builder.
   *
   * @param item the item
   * @return the double array builder
   */
  public DoubleArrayBuilder add(double item) {
    if (buffer == null) {
      throw new UnsupportedOperationException(getClass().getName() + " cannot be reused after invoking toArray()");
    }
    if (itemCount >= buffer.length - 1) {
      double[] newBuffer = createBuffer(buffer.length * 2);
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
  public void addAll(double[] elements) {
    addAll(elements, 0, elements.length);
  }

  /**
   * Add all.
   *
   * @param elements  the elements
   * @param fromIndex the from index
   * @param toIndex   the to index
   */
  public void addAll(double[] elements, int fromIndex, int toIndex) {
    for (int i = fromIndex; i < toIndex; i++) {
      add(elements[i]);
    }
  }

  /**
   * Size int.
   *
   * @return the int
   */
  public int size() {
    return itemCount;
  }

  /**
   * To array double [ ].
   *
   * @return the double [ ]
   */
  public double[] toArray() {
    if (buffer == null) {
      throw new UnsupportedOperationException(getClass().getName() + " cannot be reused after invoking toArray()");
    }
    double[] result = new double[itemCount];
    System.arraycopy(buffer, 0, result, 0, itemCount);
    itemCount = 0;
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
      builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    }
    return builder.toString();
  }


  // private helper method -------------------------------------------------------------------------------------------

  private static double[] createBuffer(int capacity) {
    return new double[capacity];
  }

}

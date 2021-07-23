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

import com.rapiddweller.common.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides a ring buffer for double values.<br><br>
 * Created: 25.11.2017 19:27:12
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class DoubleRingBuffer {

  private final double[] buffer;
  private int sampleCount;
  private int cursor;
  private boolean filled;
  private double lastValue;

  /**
   * Instantiates a new Double ring buffer.
   *
   * @param capacity the capacity
   */
  public DoubleRingBuffer(int capacity) {
    this.buffer = new double[capacity];
    Arrays.fill(this.buffer, Double.NaN);
    this.sampleCount = 0;
    this.cursor = 0;
    this.filled = false;
  }

  /**
   * Gets sample count.
   *
   * @return the sample count
   */
  public int getSampleCount() {
    return sampleCount;
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
    return buffer.length;
  }

  /**
   * Is filled boolean.
   *
   * @return the boolean
   */
  public boolean isFilled() {
    return filled;
  }

  /**
   * Add.
   *
   * @param value the value
   */
  public void add(double value) {
    lastValue = value;
    this.sampleCount++;
    this.buffer[cursor++] = value;
    if (cursor == buffer.length) {
      filled = true;
      cursor = 0;
    }
  }

  /**
   * Last double.
   *
   * @return the double
   */
  public double last() {
    return lastValue;
  }

  /**
   * First double.
   *
   * @return the double
   */
  public double first() {
    return this.buffer[cursor];
  }

  /**
   * Min double.
   *
   * @return the minimum value in the buffer or NaN if the buffer is empty
   */
  public double min() {
    double min = buffer[0];
    int n = (filled ? buffer.length : cursor);
    for (int i = 1; i < n; i++) {
      if (!Double.isNaN(buffer[i]) && buffer[i] < min) {
        min = buffer[i];
      }
    }
    return min;
  }

  /**
   * Max double.
   *
   * @return the maximum value in the buffer or NaN if the buffer is empty
   */
  public double max() {
    double max = buffer[0];
    int n = (filled ? buffer.length : cursor);
    for (int i = 1; i < n; i++) {
      if (!Double.isNaN(buffer[i]) && buffer[i] > max) {
        max = buffer[i];
      }
    }
    return max;
  }

  /**
   * Average double.
   *
   * @return the double
   */
  public double average() {
    boolean contentFound = false;
    double sum = 0.;
    int count = 0;
    for (double d : buffer) {
      if (!Double.isNaN(d)) {
        contentFound = true;
        sum += d;
        count++;
      }
    }
    return (contentFound ? sum / count : Double.NaN);
  }

  /**
   * Median double.
   *
   * @return the double
   */
  public double median() {
    List<Double> list = new ArrayList<>();
    for (double d : buffer) {
      if (!Double.isNaN(d)) {
        list.add(d);
      }
    }
    if (list.isEmpty()) {
      return Double.NaN;
    }
    Collections.sort(list);
    return list.get(list.size() / 2);
  }

  /**
   * Sum double.
   *
   * @return the double
   */
  public double sum() {
    double sum = 0.;
    for (double d : buffer) {
      if (!Double.isNaN(d)) {
        sum += d;
      }
    }
    return sum;
  }

  /**
   * Get content double [ ].
   *
   * @return the double [ ]
   */
  public double[] getContent() {
    return buffer;
  }

  /**
   * Corrected standard deviation double.
   *
   * @return the double
   */
  public double correctedStandardDeviation() {
    if (!filled) {
      return Double.NaN;
    }
    return MathUtil.correctedStandardDeviation(buffer);
  }

  /**
   * Standard deviation double.
   *
   * @return the double
   */
  public double standardDeviation() {
    if (!filled) {
      return Double.NaN;
    }
    return MathUtil.standardDeviation(buffer);
  }

  /**
   * Variance double.
   *
   * @return the double
   */
  public double variance() {
    if (!filled) {
      return Double.NaN;
    }
    return MathUtil.variance(buffer);
  }

}

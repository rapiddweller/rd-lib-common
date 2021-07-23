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

package com.rapiddweller.common.math;

/**
 * Represents a range of int values from a <code>min</code> to a <code>max</code> value
 * (including min and max).
 * Created: 05.10.2010 19:33:23
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class IntRange {

  /**
   * The Min.
   */
  protected int min;
  /**
   * The Max.
   */
  protected int max;

  /**
   * Instantiates a new Int range.
   *
   * @param min the min
   * @param max the max
   */
  public IntRange(int min, int max) {
    this.min = min;
    this.max = max;
  }

  /**
   * Gets min.
   *
   * @return the min
   */
  public int getMin() {
    return min;
  }

  /**
   * Sets min.
   *
   * @param min the min
   */
  public void setMin(int min) {
    this.min = min;
  }

  /**
   * Gets max.
   *
   * @return the max
   */
  public int getMax() {
    return max;
  }

  /**
   * Sets max.
   *
   * @param max the max
   */
  public void setMax(int max) {
    this.max = max;
  }

  /**
   * Contains boolean.
   *
   * @param i the
   * @return the boolean
   */
  public boolean contains(int i) {
    return (min <= i && i <= max);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + max;
    result = prime * result + min;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    IntRange that = (IntRange) obj;
    return (max == that.max && min == that.min);
  }

  @Override
  public String toString() {
    return (min != max ? min + "..." + max : String.valueOf(min));
  }

}

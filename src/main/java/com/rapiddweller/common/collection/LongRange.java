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

/**
 * Represents a range of {@link Long} values.
 * Created: 18.10.2010 08:28:48
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class LongRange {
  /**
   * The Min.
   */
  protected long min;
  /**
   * The Max.
   */
  protected long max;

  /**
   * Instantiates a new Long range.
   *
   * @param min the min
   * @param max the max
   */
  public LongRange(long min, long max) {
    this.min = min;
    this.max = max;
  }

  /**
   * Gets min.
   *
   * @return the min
   */
  public long getMin() {
    return min;
  }

  /**
   * Sets min.
   *
   * @param min the min
   */
  public void setMin(long min) {
    this.min = min;
  }

  /**
   * Gets max.
   *
   * @return the max
   */
  public long getMax() {
    return max;
  }

  /**
   * Sets max.
   *
   * @param max the max
   */
  public void setMax(long max) {
    this.max = max;
  }

  /**
   * Contains boolean.
   *
   * @param i the
   * @return the boolean
   */
  public boolean contains(long i) {
    return (min <= i && i <= max);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    long result = 1;
    result = prime * result + max;
    result = prime * result + min;
    return (int) ((result >>> 32) ^ result);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    LongRange that = (LongRange) obj;
    return (max == that.max && min == that.min);
  }

  @Override
  public String toString() {
    return (min != max ? min + "..." + max : String.valueOf(min));
  }

}

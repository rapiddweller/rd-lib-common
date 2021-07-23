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

import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.NullSafeComparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Represents an interval between to endpoints.
 * The endpoints can be of any class for which a {@link Comparator} can be provided.
 * Using the parameters {@link #minInclusive} and {@link #maxInclusive}, one can
 * specify whether the interval shall contain the endpoint values themselves.
 * Created: 10.03.2011 15:20:36
 *
 * @param <E> the type of the bounds that define the interval
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class Interval<E> implements Serializable {

  private static final long serialVersionUID = -5866553873478128132L;

  /**
   * The Min.
   */
  public final E min;
  /**
   * The Min inclusive.
   */
  public final boolean minInclusive;

  /**
   * The Max.
   */
  public final E max;
  /**
   * The Max inclusive.
   */
  public final boolean maxInclusive;

  /**
   * The Comparator.
   */
  public final Comparator<E> comparator;

  /**
   * For comparable interval.
   *
   * @param <T>          the type parameter
   * @param min          the min
   * @param minInclusive the min inclusive
   * @param max          the max
   * @param maxInclusive the max inclusive
   * @return the interval
   */
  public static <T extends Comparable<T>> Interval<T> forComparable(T min, boolean minInclusive, T max, boolean maxInclusive) {
    return new Interval<>(min, minInclusive, max, maxInclusive, new ComparableComparator<>());
  }

  /**
   * Instantiates a new Interval.
   *
   * @param min          the min
   * @param minInclusive the min inclusive
   * @param max          the max
   * @param maxInclusive the max inclusive
   * @param comparator   the comparator
   */
  public Interval(E min, boolean minInclusive, E max, boolean maxInclusive,
                  Comparator<E> comparator) {
    this.min = min;
    this.minInclusive = minInclusive;
    this.max = max;
    this.maxInclusive = maxInclusive;
    this.comparator = comparator;
  }

  /**
   * Create closed interval interval.
   *
   * @param <T> the type parameter
   * @param min the min
   * @param max the max
   * @return the interval
   */
  public static <T extends Comparable<T>> Interval<T> createClosedInterval(T min, T max) {
    return new Interval<>(min, true, max, true, new ComparableComparator<>());
  }

  /**
   * Gets min.
   *
   * @return the min
   */
  public E getMin() {
    return min;
  }

  /**
   * Is min inclusive boolean.
   *
   * @return the boolean
   */
  public boolean isMinInclusive() {
    return minInclusive;
  }

  /**
   * Gets max.
   *
   * @return the max
   */
  public E getMax() {
    return max;
  }

  /**
   * Is max inclusive boolean.
   *
   * @return the boolean
   */
  public boolean isMaxInclusive() {
    return maxInclusive;
  }

  /**
   * Create infinite interval interval.
   *
   * @param <T> the type parameter
   * @return the interval
   */
  public static <T> Interval<T> createInfiniteInterval() {
    return new Interval<>(null, false, null, false, null);
  }

  /**
   * Contains boolean.
   *
   * @param x the x
   * @return the boolean
   */
  public boolean contains(E x) {
    if (min != null) {
      int minComp = comparator.compare(min, x);
      if (minComp > 0 || (!minInclusive && minComp == 0)) {
        return false;
      }
    }
    if (max == null) {
      return true;
    }
    int maxComp = comparator.compare(x, max);
    return (maxComp < 0 || (maxInclusive && maxComp == 0));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((max == null) ? 0 : max.hashCode());
    result = prime * result + (maxInclusive ? 1231 : 1237);
    result = prime * result + ((min == null) ? 0 : min.hashCode());
    result = prime * result + (minInclusive ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    @SuppressWarnings("rawtypes")
    Interval that = (Interval) other;
    return
        (NullSafeComparator.equals(this.min, that.min)
            && this.minInclusive == that.minInclusive
            && NullSafeComparator.equals(this.max, that.max)
            && this.maxInclusive == that.maxInclusive);
  }

  @Override
  public String toString() {
    if (min != null && max != null && comparator.compare(min, max) == 0) {
      return String.valueOf(min);
    } else {
      return (minInclusive ? '[' : ']') + String.valueOf(min) + ',' + max + (maxInclusive ? ']' : '[');
    }
  }

}

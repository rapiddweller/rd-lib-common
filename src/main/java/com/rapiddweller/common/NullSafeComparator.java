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

import java.util.Comparator;

/**
 * Comparator Decorator that adds support for null-Value comparison.
 * null may be defined to have a value either greater or less than any non-null value.
 * Created: 11.04.2005 08:34:02
 *
 * @param <E> the type of the objects to compare
 * @author Volker Bergmann
 */
public class NullSafeComparator<E> implements Comparator<E> {

  /**
   * The constant NULL_IS_LESSER.
   */
  public static final int NULL_IS_LESSER = -1;
  /**
   * The constant NULL_IS_GREATER.
   */
  public static final int NULL_IS_GREATER = 1;

  private final Comparator<? super E> realComparator;

  /**
   * The value returned if null is compared to something.
   */
  private final int nullComparation;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Null safe comparator.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public NullSafeComparator() {
    this(new ComparableComparator());
  }

  /**
   * Instantiates a new Null safe comparator.
   *
   * @param nullComparation the null comparation
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public NullSafeComparator(int nullComparation) {
    this(new ComparableComparator(), nullComparation);
  }

  /**
   * Instantiates a new Null safe comparator.
   *
   * @param realComparator the real comparator
   */
  public NullSafeComparator(Comparator<? super E> realComparator) {
    this(realComparator, -1);
  }

  /**
   * Instantiates a new Null safe comparator.
   *
   * @param realComparator  the real comparator
   * @param nullComparation the null comparation
   */
  public NullSafeComparator(Comparator<? super E> realComparator, int nullComparation) {
    this.realComparator = realComparator;
    this.nullComparation = nullComparation;
  }

  // interface -------------------------------------------------------------------------------------------------------

  @Override
  public int compare(E o1, E o2) {
    if (o1 == o2) {
      return 0;
    }
    if (o1 == null) {
      return (o2 == null ? 0 : nullComparation);
    } else if (o2 == null) {
      return -nullComparation;
    } else {
      return realComparator.compare(o1, o2);
    }
  }

  /**
   * Compare int.
   *
   * @param <T>             the type parameter
   * @param o1              the o 1
   * @param o2              the o 2
   * @param nullComparation the null comparation
   * @return the int
   */
  public static <T extends Comparable<T>> int compare(T o1, T o2, int nullComparation) {
    if (o1 == o2) {
      return 0;
    }
    if (o1 == null) {
      return (o2 == null ? 0 : nullComparation);
    } else if (o2 == null) {
      return -nullComparation;
    } else {
      return o1.compareTo(o2);
    }
  }

  /**
   * Equals boolean.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the boolean
   */
  public static boolean equals(Object o1, Object o2) {
    if (o1 == null) {
      return (o2 == null);
    } else {
      return o1.equals(o2);
    }
  }

  /**
   * Hash code int.
   *
   * @param o the o
   * @return the int
   */
  public static int hashCode(Object o) {
    return (o != null ? o.hashCode() : 0);
  }
}

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

package com.rapiddweller.common.condition;

import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.Condition;

import java.util.Comparator;

/**
 * Condition implementation that compares an arbitrary number of arguments
 * with one of different available operators.
 * Created: 06.03.2006 17:49:06
 *
 * @param <E> the type of argument to evaluate
 * @author Volker Bergmann
 * @since 0.1
 */
public class ComparationCondition<E> implements Condition<E[]> {

  /**
   * The constant EQUAL.
   */
  public static final int EQUAL = 0;
  /**
   * The constant NOT_EQUAL.
   */
  public static final int NOT_EQUAL = 1;
  /**
   * The constant GREATER_OR_EQUAL.
   */
  public static final int GREATER_OR_EQUAL = 2;
  /**
   * The constant GREATER.
   */
  public static final int GREATER = 3;
  /**
   * The constant LESS_OR_EQUAL.
   */
  public static final int LESS_OR_EQUAL = 4;
  /**
   * The constant LESS.
   */
  public static final int LESS = 5;

  private int operator;
  private Comparator<E> comparator;

  /**
   * Instantiates a new Comparation condition.
   */
  public ComparationCondition() {
    this(EQUAL);
  }

  /**
   * Instantiates a new Comparation condition.
   *
   * @param operator the operator
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public ComparationCondition(int operator) {
    this(operator, new ComparableComparator());
  }

  /**
   * Instantiates a new Comparation condition.
   *
   * @param operator   the operator
   * @param comparator the comparator
   */
  public ComparationCondition(int operator, Comparator<E> comparator) {
    this.operator = operator;
    this.comparator = comparator;
  }

  /**
   * Gets operator.
   *
   * @return the operator
   */
  public int getOperator() {
    return operator;
  }

  /**
   * Sets operator.
   *
   * @param operator the operator
   */
  public void setOperator(int operator) {
    this.operator = operator;
  }

  /**
   * Gets comparator.
   *
   * @return the comparator
   */
  public Comparator<E> getComparator() {
    return comparator;
  }

  /**
   * Sets comparator.
   *
   * @param comparator the comparator
   */
  public void setComparator(Comparator<E> comparator) {
    this.comparator = comparator;
  }

  @Override
  public boolean evaluate(E[] arguments) {
    if (arguments.length != 2) {
      throw new IllegalArgumentException("Comparation only supported for two arguments, found: "
          + arguments.length);
    }
    int comparation = comparator.compare(arguments[0], arguments[1]);
    switch (operator) {
      case EQUAL:
        return comparation == 0;
      case NOT_EQUAL:
        return comparation != 0;
      case GREATER_OR_EQUAL:
        return comparation >= 0;
      case GREATER:
        return comparation == 1;
      case LESS_OR_EQUAL:
        return comparation <= 0;
      case LESS:
        return comparation == -1;
      default:
        throw new IllegalStateException("Operator no supported: " + operator);
    }
  }
}

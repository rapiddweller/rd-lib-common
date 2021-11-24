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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.exception.IllegalArgumentError;
import com.rapiddweller.common.version.DateVersionNumberComponent;
import com.rapiddweller.common.version.NumberVersionNumberComponent;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link ComparationCondition}.
 * Created at 29.04.2008 18:18:45
 *
 * @author Volker Bergmann
 * @since 0.4.2
 */
public class ComparationConditionTest {

  @Test
  public void testSetOperator() {
    ComparationCondition<Object> comparationCondition = new ComparationCondition<>();
    comparationCondition.setOperator(1);
    assertEquals(1, comparationCondition.getOperator());
  }

  @Test
  public void testSetComparator() {
    ComparationCondition<Object> comparationCondition = new ComparationCondition<>();
    NullSafeComparator<Object> nullSafeComparator = new NullSafeComparator<>();
    comparationCondition.setComparator(nullSafeComparator);
    assertSame(nullSafeComparator, comparationCondition.getComparator());
  }

  @Test
  public void testEvaluate() {
    assertThrows(IllegalArgumentError.class,
        () -> (new ComparationCondition<>()).evaluate(new Object[] {"foo", "foo", "foo"}));
    assertTrue((new ComparationCondition<>()).evaluate(new Object[] {"arguments", "arguments"}));
    assertFalse((new ComparationCondition<>(1)).evaluate(new Object[] {"arguments", "arguments"}));
    assertTrue((new ComparationCondition<>(2)).evaluate(new Object[] {"arguments", "arguments"}));
    assertFalse((new ComparationCondition<>(3)).evaluate(new Object[] {"arguments", "arguments"}));
    assertTrue((new ComparationCondition<>(4)).evaluate(new Object[] {"arguments", "arguments"}));
    assertFalse((new ComparationCondition<>(5)).evaluate(new Object[] {"arguments", "arguments"}));
    assertThrows(IllegalArgumentError.class,
        () -> (new ComparationCondition<>(-1)).evaluate(new Object[] {"arguments", "arguments"}));
    assertTrue((new ComparationCondition<>(1)).evaluate(new Object[] {"Arguments", "arguments"}));
    assertFalse((new ComparationCondition<>(2)).evaluate(new Object[] {"Arguments", "arguments"}));
    assertFalse((new ComparationCondition<>(4)).evaluate(new Object[] {"java.lang.Object[]", "arguments"}));
  }

  @Test
  public void testEvaluate2() throws ParseException {
    NumberVersionNumberComponent numberVersionNumberComponent = new NumberVersionNumberComponent(10);
    assertFalse((new ComparationCondition<>())
        .evaluate(new Object[] {numberVersionNumberComponent, new DateVersionNumberComponent("2020-03-01")}));
  }

  @Test
  public void testConstructor() {
    ComparationCondition<Object> actualComparationCondition = new ComparationCondition<>();
    assertEquals(0, actualComparationCondition.getOperator());
    assertTrue(actualComparationCondition.getComparator() instanceof com.rapiddweller.common.ComparableComparator);
  }

  @Test
  public void testConstructor2() {
    ComparationCondition<Object> actualComparationCondition = new ComparationCondition<>(1);
    assertEquals(1, actualComparationCondition.getOperator());
    assertTrue(actualComparationCondition.getComparator() instanceof com.rapiddweller.common.ComparableComparator);
  }

  @Test
  public void testEqual() {
    ComparationCondition<Integer> condition = new ComparationCondition<>(ComparationCondition.EQUAL);
    assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
    assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
  }

  @Test
  public void testNotEqual() {
    ComparationCondition<Integer> condition = new ComparationCondition<>(ComparationCondition.NOT_EQUAL);
    assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
    assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
  }

  @Test
  public void testGreaterOrEqual() {
    ComparationCondition<Integer> condition = new ComparationCondition<>(ComparationCondition.GREATER_OR_EQUAL);
    assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
    assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
    assertTrue(condition.evaluate(ArrayUtil.toArray(2, 1)));
  }

  @Test
  public void testGreater() {
    ComparationCondition<Integer> condition = new ComparationCondition<>(ComparationCondition.GREATER);
    assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
    assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
    assertTrue(condition.evaluate(ArrayUtil.toArray(2, 1)));
  }

  @Test
  public void testLessOrEqual() {
    ComparationCondition<Integer> condition = new ComparationCondition<>(ComparationCondition.LESS_OR_EQUAL);
    assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
    assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
    assertFalse(condition.evaluate(ArrayUtil.toArray(2, 1)));
  }

  @Test
  public void testLess() {
    ComparationCondition<Integer> condition = new ComparationCondition<>(ComparationCondition.LESS);
    assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
    assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
    assertFalse(condition.evaluate(ArrayUtil.toArray(2, 1)));
  }

}

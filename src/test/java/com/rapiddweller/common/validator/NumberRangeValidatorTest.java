/*
 * Copyright (C) 2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

package com.rapiddweller.common.validator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link NumberRangeValidator}.<br/><br/>
 * Created: 01.10.2021 08:36:37
 * @author Volker Bergmann
 * @since 1.1.5
 */
public class NumberRangeValidatorTest {

  @Test
  public void testAllInclusive() {
    NumberRangeValidator<Double> v = new NumberRangeValidator<>(0., true, 1., true);
    assertTrue(v.valid(0.));
    assertInsideValuesValid(v);
    assertTrue(v.valid(1.));
    assertEquals("NumberRangeValidator{[0.0,1.0]}", v.toString());
  }

  @Test
  public void testMaxExclusive() {
    NumberRangeValidator<Double> v = new NumberRangeValidator<>(0., true, 1., false);
    assertTrue(v.valid(0.));
    assertInsideValuesValid(v);
    assertFalse(v.valid(1.));
    assertEquals("NumberRangeValidator{[0.0,1.0[}", v.toString());
  }

  @Test
  public void testMinExclusive() {
    NumberRangeValidator<Double> v = new NumberRangeValidator<>(0., false, 1., true);
    assertFalse(v.valid(0.));
    assertInsideValuesValid(v);
    assertTrue(v.valid(1.));
    assertEquals("NumberRangeValidator{]0.0,1.0]}", v.toString());
  }

  @Test
  public void testAllExclusive() {
    NumberRangeValidator<Double> v = new NumberRangeValidator<>(0., false, 1., false);
    assertFalse(v.valid(0.));
    assertInsideValuesValid(v);
    assertFalse(v.valid(1.));
    assertEquals("NumberRangeValidator{]0.0,1.0[}", v.toString());
  }

  // private helper method -------------------------------------------------------------------------------------------

  private void assertInsideValuesValid(NumberRangeValidator<Double> v) {
    assertTrue(v.valid(0.001));
    assertTrue(v.valid(0.5));
    assertTrue(v.valid(0.999));
  }

}

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

package com.rapiddweller.common.filter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link AndFilter}.
 * Created: 08.06.2012 21:00:24
 *
 * @author Volker Bergmann
 * @since 0.5.16
 */
public class AndFilterTest {

  private static final ConstantFilter<Integer> TRUE = new ConstantFilter<>(true);
  private static final ConstantFilter<Integer> FALSE = new ConstantFilter<>(false);

  @Test
  public void testTrueAndTrue() {
    assertTrue(new AndFilter<>(TRUE, TRUE).accept(0));
  }

  @Test
  public void testTrueAndFalse() {
    assertFalse(new AndFilter<>(TRUE, FALSE).accept(0));
  }

  @Test
  public void testFalseAndTrue() {
    assertFalse(new AndFilter<>(FALSE, TRUE).accept(0));
  }

  @Test
  public void testFalseAndFalse() {
    assertFalse(new AndFilter<>(FALSE, FALSE).accept(0));
  }

}

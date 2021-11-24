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

package com.rapiddweller.common.converter;

import com.rapiddweller.common.exception.IllegalArgumentError;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link String2BooleanConverter}.
 * Created: 15.12.2012 13:15:34
 *
 * @author Volker Bergmann
 * @since 0.5.21
 */
public class String2BooleanConverterTest extends AbstractConverterTest {

  private static final String2BooleanConverter converter = new String2BooleanConverter();

  public String2BooleanConverterTest() {
    super(String2BooleanConverter.class);
  }

  @Test
  public void testNull() {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyString() {
    assertNull(converter.convert(""));
  }

  @Test
  public void testTrueAndFalse() {
    assertTrue(converter.convert("true"));
    assertTrue(converter.convert("TRUE"));
    assertTrue(converter.convert("True"));
    assertFalse(converter.convert("false"));
    assertFalse(converter.convert("FALSE"));
    assertFalse(converter.convert("False"));
  }

  @Test(expected = IllegalArgumentError.class)
  public void testIllegalStrings() {
    converter.convert("x");
  }

}

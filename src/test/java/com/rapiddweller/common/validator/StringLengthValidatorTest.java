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

package com.rapiddweller.common.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the StringLengthValidator.
 * Created: 29.09.2006 16:32:44
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class StringLengthValidatorTest {

  @Test
  public void testUnlimited() {
    StringLengthValidator validator = new StringLengthValidator();
    assertFalse(validator.valid(null));
    assertTrue(new StringLengthValidator(0, null, true).valid(null));
    assertTrue(validator.valid(""));
    assertTrue(validator.valid("abc"));
    assertTrue(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
  }

  @Test
  public void testMaxLength10() {
    StringLengthValidator validator = new StringLengthValidator(10);
    assertFalse(validator.valid(null));
    assertTrue(validator.valid(""));
    assertTrue(validator.valid("abc"));
    assertFalse(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
  }

  @Test
  public void testMinLength5MaxLength10() {
    StringLengthValidator validator = new StringLengthValidator(5, 10);
    assertFalse(validator.valid(null));
    assertFalse(validator.valid(""));
    assertFalse(validator.valid("abcd"));
    assertTrue(validator.valid("abcde"));
    assertTrue(validator.valid("abcdefghij"));
    assertFalse(validator.valid("abcdefghijk"));
    assertFalse(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
  }

  @Test
  public void testMinLength5() {
    StringLengthValidator validator = new StringLengthValidator(5, null);
    assertFalse(validator.valid(null));
    assertFalse(validator.valid(""));
    assertFalse(validator.valid("abcd"));
    assertTrue(validator.valid("abcde"));
    assertTrue(validator.valid("abcdefghij"));
    assertTrue(validator.valid("abcdefghijk"));
    assertTrue(validator.valid("abcdefghijklmnopqrstuvwxyz1234567890!"));
  }

}

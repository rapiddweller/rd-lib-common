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

import com.rapiddweller.common.Validator;

/**
 * Simple helper class that provides methods for testing validators.
 * Created at 24.07.2009 17:52:21
 *
 * @param <E> the object type to be validated
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class SimpleValidatorTest<E> {

  private final Validator<E> validator;

  /**
   * Instantiates a new Simple validator test.
   *
   * @param validator the validator
   */
  public SimpleValidatorTest(Validator<E> validator) {
    this.validator = validator;
  }

  /**
   * Assert valid.
   *
   * @param candidate the candidate
   */
  protected void assertValid(E candidate) {
    assert validator.valid(candidate);
  }

  /**
   * Assert invalid.
   *
   * @param candidate the candidate
   */
  protected void assertInvalid(E candidate) {
    assert !validator.valid(candidate);
  }

}

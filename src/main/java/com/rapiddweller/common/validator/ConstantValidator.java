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
 * Requires that each validated object is equal to a reference object.
 * Created at 04.07.2009 06:59:57
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ConstantValidator implements Validator<Object> {

  private final Object expectedValue;

  /**
   * Instantiates a new Constant validator.
   */
  public ConstantValidator() {
    this(null);
  }

  /**
   * Instantiates a new Constant validator.
   *
   * @param expectedValue the expected value
   */
  public ConstantValidator(Object expectedValue) {
    this.expectedValue = expectedValue;
  }

  @Override
  public boolean valid(Object value) {
    return (expectedValue.equals(value));
  }

}

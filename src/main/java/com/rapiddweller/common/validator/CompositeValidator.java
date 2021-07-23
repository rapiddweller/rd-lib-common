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

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.Validator;

/**
 * Abstract {@link Validator} implementation that combines the evaluation results of other Validators.
 * Created: 20.11.2007 09:50:13
 *
 * @param <E> the object type to be validated
 * @author Volker Bergmann
 */
public abstract class CompositeValidator<E> implements Validator<E> {

  /**
   * The Sub validators.
   */
  protected Validator<E>[] subValidators;

  /**
   * Instantiates a new Composite validator.
   *
   * @param subValidators the sub validators
   */
  protected CompositeValidator(Validator<E>[] subValidators) {
    this.subValidators = subValidators;
  }

  /**
   * Add.
   *
   * @param validator the validator
   */
  public void add(Validator<E> validator) {
    subValidators = ArrayUtil.append(validator, subValidators);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '[' + ArrayFormat.format(subValidators) + ']';
  }
}

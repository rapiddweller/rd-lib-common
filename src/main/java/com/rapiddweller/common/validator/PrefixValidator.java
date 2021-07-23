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
 * Validates that Strings have a given prefix.
 * Created: 02.08.2011 07:26:39
 *
 * @author Volker Bergmann
 * @since 0.5.9
 */
public class PrefixValidator extends SubStringValidator {

  /**
   * The Prefix.
   */
  protected String prefix;

  /**
   * Instantiates a new Prefix validator.
   *
   * @param prefix the prefix
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public PrefixValidator(String prefix) {
    super(0, prefix.length(), (Validator) new ConstantValidator(prefix));
    this.prefix = prefix;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '[' + prefix + ']';
  }

}

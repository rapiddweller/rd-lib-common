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

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Validator;
import com.rapiddweller.common.converter.SubstringExtractor;

/**
 * Uses another validator to validate sub strings.
 * Created: 02.08.2011 07:19:04
 *
 * @author Volker Bergmann
 * @since 0.5.9
 */
public class SubStringValidator extends AbstractValidator<String> {

  /**
   * The Substring extractor.
   */
  protected Converter<String, String> substringExtractor;
  /**
   * The Real validator.
   */
  protected Validator<String> realValidator;

  /**
   * Instantiates a new Sub string validator.
   *
   * @param from          the from
   * @param to            the to
   * @param realValidator the real validator
   */
  public SubStringValidator(int from, Integer to, Validator<String> realValidator) {
    this.substringExtractor = new SubstringExtractor(from, to);
    this.realValidator = realValidator;
  }


  @Override
  public boolean valid(String value) {
    if (value == null) {
      return false;
    }
    return realValidator.valid(substringExtractor.convert(value));
  }

}

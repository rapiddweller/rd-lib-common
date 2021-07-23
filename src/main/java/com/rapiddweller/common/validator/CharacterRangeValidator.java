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
 * Validates if a character is in a certain range.
 * Created at 14.08.2008 09:34:59
 *
 * @author Volker Bergmann
 * @since 0.4.5
 */
public class CharacterRangeValidator implements Validator<Character> {

  private char min;
  private char max;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Character range validator.
   */
  public CharacterRangeValidator() {
    this((char) 32, (char) 126);
  }

  /**
   * Instantiates a new Character range validator.
   *
   * @param min the min
   * @param max the max
   */
  public CharacterRangeValidator(char min, char max) {
    this.min = min;
    this.max = max;
  }

  // Validator interface ---------------------------------------------------------------------------------------------

  @Override
  public boolean valid(Character c) {
    return (min <= c && c <= max);
  }

  // properties ------------------------------------------------------------------------------------------------------

  /**
   * Gets min.
   *
   * @return the min
   */
  public char getMin() {
    return min;
  }

  /**
   * Sets min.
   *
   * @param min the min
   */
  public void setMin(char min) {
    this.min = min;
  }

  /**
   * Gets max.
   *
   * @return the max
   */
  public char getMax() {
    return max;
  }

  /**
   * Sets max.
   *
   * @param max the max
   */
  public void setMax(char max) {
    this.max = max;
  }

}

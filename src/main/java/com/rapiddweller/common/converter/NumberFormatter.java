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

import com.rapiddweller.common.ConversionException;

import java.text.NumberFormat;

/**
 * Formats {@link Number} objects as {@link String} using {@link NumberFormat}.
 * Created: 26.02.2010 08:36:36
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class NumberFormatter extends NumberFormatBasedConverter<Number, String> {

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Number formatter.
   */
  public NumberFormatter() {
    super(Number.class, String.class);
  }

  /**
   * Instantiates a new Number formatter.
   *
   * @param pattern the pattern
   */
  public NumberFormatter(String pattern) {
    super(Number.class, String.class, pattern);
  }

  // Converter interface implementation ------------------------------------------------------------------------------

  @Override
  public String convert(Number value) throws ConversionException {
    return format(value);
  }

}

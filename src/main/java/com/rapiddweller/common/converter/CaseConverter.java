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

import com.rapiddweller.common.LocaleUtil;

import java.util.Locale;

/**
 * Converts a String's characters to upper or lower case.
 * Created: 12.06.2006 19:05:09
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class CaseConverter extends ThreadSafeConverter<String, String> {

  /**
   * Mode flag for the Converter. If set to true, it converts to upper case, else to lower case
   */
  private boolean toUpper;

  private Locale locale;

  // Constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Case converter.
   */
  public CaseConverter() {
    this(true);
  }

  /**
   * Instantiates a new Case converter.
   *
   * @param toUpper the to upper
   */
  public CaseConverter(boolean toUpper) {
    this(toUpper, LocaleUtil.getFallbackLocale());
  }

  /**
   * Instantiates a new Case converter.
   *
   * @param toUpper the to upper
   * @param locale  the locale
   */
  public CaseConverter(boolean toUpper, Locale locale) {
    super(String.class, String.class);
    this.toUpper = toUpper;
    this.locale = locale;
  }

  // Properties ------------------------------------------------------------------------------------------------------

  /**
   * Sets the Locale of the CaseConverter.ConverterImpl.
   *
   * @param locale the Locale to set
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  /**
   * Sets to upper.
   *
   * @param toUpper the to upper
   */
  public void setToUpper(boolean toUpper) {
    this.toUpper = toUpper;
  }

  // Converter interface ---------------------------------------------------------------------------------------------

  /**
   * @see com.rapiddweller.common.Converter
   */
  @Override
  public String convert(String source) {
    if (source == null) {
      return null;
    }
    return (toUpper ? source.toUpperCase(locale) : source.toLowerCase(locale));
  }

}

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
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;

import java.util.Formatter;
import java.util.Locale;

/**
 * {@link Converter} implementation that uses a {@link Formatter}
 * to render argument objects in C-like printf format.
 * Created at 20.07.2009 07:18:43
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class PrintfConverter extends ThreadSafeConverter<Object, String> {

  private Locale locale;
  private String pattern;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Printf converter.
   */
  public PrintfConverter() {
    this("");
  }

  /**
   * Instantiates a new Printf converter.
   *
   * @param pattern the pattern
   */
  public PrintfConverter(String pattern) {
    this(pattern, Locale.getDefault());
  }

  /**
   * Instantiates a new Printf converter.
   *
   * @param pattern the pattern
   * @param locale  the locale
   */
  public PrintfConverter(String pattern, Locale locale) {
    super(Object.class, String.class);
    this.pattern = pattern;
    this.locale = locale;
  }

  // properties ------------------------------------------------------------------------------------------------------

  /**
   * Gets locale.
   *
   * @return the locale
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Sets locale.
   *
   * @param locale the locale
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  /**
   * Gets pattern.
   *
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Sets pattern.
   *
   * @param pattern the pattern
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  // converter interface ---------------------------------------------------------------------------------------------

  @Override
  public String convert(Object sourceValue) throws ConversionException {
    if (sourceValue == null) {
      return null;
    }
    Formatter formatter = new Formatter(locale);
    try {
      return formatter.format(pattern, sourceValue).out().toString();
    } finally {
      IOUtil.close(formatter);
    }
  }

}

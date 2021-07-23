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
import com.rapiddweller.common.LocaleUtil;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Renders a single object or an array of objects into a String, similar to the java.text.MessageFormat.
 * Created: 12.11.2007 20:46:31
 *
 * @author Volker Bergmann
 */
public class MessageConverter extends ThreadSafeConverter<Object, String> {

  private String pattern;
  private Locale locale;

  /**
   * Instantiates a new Message converter.
   */
  public MessageConverter() {
    this("{0}");
  }

  /**
   * Instantiates a new Message converter.
   *
   * @param pattern the pattern
   */
  public MessageConverter(String pattern) {
    this(pattern, LocaleUtil.getFallbackLocale());
  }

  /**
   * Instantiates a new Message converter.
   *
   * @param pattern the pattern
   * @param locale  the locale
   */
  public MessageConverter(String pattern, Locale locale) {
    super(Object.class, String.class);
    this.pattern = pattern;
    this.locale = locale;
  }

  /**
   * Sets pattern.
   *
   * @param pattern the pattern
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * Sets locale.
   *
   * @param locale the locale
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  @Override
  public String convert(Object sourceValue) throws ConversionException {
    Object tmp = sourceValue;
    if (tmp != null && !tmp.getClass().isArray()) {
      tmp = new Object[] {tmp};
    }
    return new MessageFormat(pattern, locale).format(tmp);
  }

}

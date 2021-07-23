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

import java.util.Locale;

/**
 * Converts Strings to lowercase.
 * Created at 26.05.2009 09:10:24
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ToLowerCaseConverter extends CaseConverter {

  /**
   * Instantiates a new To lower case converter.
   */
  public ToLowerCaseConverter() {
    super(false);
  }

  /**
   * Instantiates a new To lower case converter.
   *
   * @param locale the locale
   */
  public ToLowerCaseConverter(Locale locale) {
    super(false, locale);
  }

}

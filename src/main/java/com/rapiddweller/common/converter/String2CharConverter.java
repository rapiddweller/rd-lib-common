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
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Converts {@link String}s of length 1 to {@link Character}s, Strings of length 0 to <code>null</code>.<br/><br/>
 * Created: 27.02.2010 10:16:03
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class String2CharConverter extends ThreadSafeConverter<String, Character> {

  public String2CharConverter() {
    super(String.class, Character.class);
  }

  @Override
  public Character convert(String sourceValue) throws ConversionException {
    if (sourceValue == null) {
      return null;
    }
    switch (sourceValue.length()) {
      case 0:
        return null;
      case 1:
        return sourceValue.charAt(0);
      default:
        throw ExceptionFactory.getInstance().conversionFailed(
            "'" + sourceValue + "' cannot be converted to a character", null);
    }
  }

}

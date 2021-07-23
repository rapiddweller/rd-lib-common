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
import com.rapiddweller.common.StringUtil;

/**
 * {@link Converter} that escapes strings in C- and Java-style syntax, e.g. TAB -&gt; '\t'.
 * Created: 19.01.2011 15:49:24
 *
 * @author Volker Bergmann
 * @since 0.5.5
 */
public class EscapingConverter extends ThreadSafeConverter<String, String> {

  /**
   * Instantiates a new Escaping converter.
   */
  public EscapingConverter() {
    super(String.class, String.class);
  }

  @Override
  public String convert(String sourceValue) throws ConversionException {
    return StringUtil.escape(sourceValue);
  }

}

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Parses a {@link String} as {@link DateFormat}.
 * Created at 13.07.2009 18:38:54
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class String2DateFormatConverter extends ThreadSafeConverter<String, DateFormat> {

  /**
   * Instantiates a new String 2 date format converter.
   */
  public String2DateFormatConverter() {
    super(String.class, DateFormat.class);
  }

  @Override
  public DateFormat convert(String pattern) throws ConversionException {
    try {
      return new SimpleDateFormat(pattern);
    } catch (Exception e) {
      throw new ConversionException("Error in SimpleDateFormat creation", e);
    }
  }

}

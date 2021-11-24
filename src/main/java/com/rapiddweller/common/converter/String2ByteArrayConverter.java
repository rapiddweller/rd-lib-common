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
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.UnsupportedEncodingException;

/**
 * Converts strings to byte arrays based on a character encoding, e.g. UTF-8.
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class String2ByteArrayConverter extends ThreadSafeConverter<String, byte[]> {

  private final String encoding;

  public String2ByteArrayConverter() {
    this(SystemInfo.getFileEncoding());
  }

  public String2ByteArrayConverter(String encoding) {
    super(String.class, byte[].class);
    this.encoding = encoding;
  }

  @Override
  public byte[] convert(String sourceValue) throws ConversionException {
    try {
      return sourceValue.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().configurationError("Error converting " + sourceValue, e);
    }
  }

}

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

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.SystemInfo;

import java.io.UnsupportedEncodingException;

/**
 * Converts byte arrays to Strings based on a character encoding, e.g. UTF-8.
 * Created: 26.02.2010 08:26:55
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ByteArray2StringConverter extends ThreadSafeConverter<byte[], String> {

  private final String encoding;

  public ByteArray2StringConverter() {
    this(SystemInfo.getFileEncoding());
  }

  public ByteArray2StringConverter(String encoding) {
    super(byte[].class, String.class);
    this.encoding = encoding;
  }

  @Override
  public String convert(byte[] target) throws ConversionException {
    try {
      return new String(target, encoding);
    } catch (UnsupportedEncodingException e) {
      throw new ConfigurationError("Error converting byte array", e);
    }
  }

}

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
import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.net.URLEncoder;

/**
 * Decodes a String from the <code>application/x-www-form-urlencoded</code>  MIME format.
 * @author Volker Bergmann
 * @see java.net.URLDecoder Created at 04.07.2009 07:21:15
 * @since 0.5.0
 */
public class URLDecodeConverter extends ThreadSafeConverter<String, String> {

  private final String encoding;

  public URLDecodeConverter() {
    this(Encodings.UTF_8);
  }

  public URLDecodeConverter(String encoding) {
    super(String.class, String.class);
    this.encoding = encoding;
  }

  @Override
  public String convert(String sourceValue) throws ConversionException {
    return convert(sourceValue, encoding);
  }

  public static String convert(String sourceValue, String encoding) throws ConversionException {
    if (StringUtil.isEmpty(sourceValue)) {
      return null;
    }
    try {
      return URLEncoder.encode(sourceValue, encoding);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().conversionFailed(
          "URL decoding of '" + sourceValue + "' failed for encoding '" + encoding + "'", e);
    }
  }

}

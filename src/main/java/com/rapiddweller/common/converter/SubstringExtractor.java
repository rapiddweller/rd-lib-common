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

/**
 * Extracts a sub string from a string.
 * Created: 26.02.2010 10:55:11
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class SubstringExtractor extends ThreadSafeConverter<String, String> {

  private int from;
  private Integer to;

  /**
   * Instantiates a new Substring extractor.
   */
  public SubstringExtractor() {
    this(0);
  }

  /**
   * Instantiates a new Substring extractor.
   *
   * @param from the from
   */
  public SubstringExtractor(int from) {
    this(from, null);
  }

  /**
   * Instantiates a new Substring extractor.
   *
   * @param from the from
   * @param to   the to
   */
  public SubstringExtractor(int from, Integer to) {
    super(String.class, String.class);
    this.from = from;
    this.to = to;
  }

  /**
   * Sets from.
   *
   * @param from the from
   */
  public void setFrom(int from) {
    this.from = from;
  }

  /**
   * Sets to.
   *
   * @param to the to
   */
  public void setTo(Integer to) {
    this.to = to;
  }

  @Override
  public String convert(String sourceValue) throws ConversionException {
    if (sourceValue == null) {
      return null;
    }
    int startIndex = relativeIndex(from, sourceValue);
    if (startIndex >= sourceValue.length()) {
      return "";
    }
    if (to == null) {
      return sourceValue.substring(startIndex);
    } else {
      int endIndex = relativeIndex(to, sourceValue);
      if (endIndex < startIndex) {
        return "";
      }
      if (endIndex > sourceValue.length()) {
        endIndex = sourceValue.length();
      }
      return sourceValue.substring(startIndex, endIndex);
    }
  }

  private static int relativeIndex(int index, String sourceValue) {
    return (index >= 0 ? index : sourceValue.length() + index);
  }

}

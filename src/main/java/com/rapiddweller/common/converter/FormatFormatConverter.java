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

import com.rapiddweller.common.exception.ExceptionFactory;

import java.text.Format;

/**
 * Converts an object to a String by using a java.lang.Format object's format() method.
 * Created: 30.08.2006 19:43:09
 * @param <S> the object type to convert from
 * @author Volker Bergmann
 * @since 0.1
 */
public class FormatFormatConverter<S> extends FormatBasedConverter<S, String> {

  public FormatFormatConverter(Class<S> sourceType, Format format, boolean threadSafe) {
    super(sourceType, String.class, format, threadSafe);
  }

  @Override
  public synchronized String convert(S source) {
    try {
      return format.format(source);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().conversionFailed("Conversion failed for value: " + source, e);
    }
  }

}

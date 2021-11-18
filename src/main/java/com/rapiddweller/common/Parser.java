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

package com.rapiddweller.common;

import com.rapiddweller.common.exception.ParseException;

import java.text.ParsePosition;

/**
 * Parent for classes which parse {@link String}.
 * It resembles the 'parse' part of the {@link java.text.Format} class.<br/<br/>
 * Created: 10.03.2011 15:36:54
 * @param <E> the result type of the parser
 * @author Volker Bergmann
 * @since 0.5.8
 */
public abstract class Parser<E> {

  public E parse(String text) {
    ParsePosition pos = new ParsePosition(0);
    E result = parseObject(text, pos);
    if (pos.getIndex() < text.length()) {
      throw new ParseException("Illegal syntax", text, -1, pos.getIndex());
    }
    return result;
  }

  public abstract E parseObject(String text, ParsePosition pos);

}

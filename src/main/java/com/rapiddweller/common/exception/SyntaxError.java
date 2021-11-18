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

package com.rapiddweller.common.exception;

/**
 * {@link ParseException} child class that represents a syntax error.
 * Created: 24.03.2011 11:49:34
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class SyntaxError extends ParseException {

  public SyntaxError(String message) {
    this(message, null);
  }

  public SyntaxError(String message, String parsedText) {
    super(message, parsedText);
  }

  public SyntaxError(String message, String parsedText, int line, int column) {
    super(message, parsedText, line, column);
  }

  public SyntaxError(String message, Throwable cause, String parsedText, int line, int column) {
    super(message, cause, parsedText, line, column);
  }

}

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
 * Indicates a parsing error.
 * Created at 30.12.2008 08:23:05
 * @author Volker Bergmann
 * @since 0.5.7
 */
public class ParseException extends ApplicationException {

  private final Object source;
  private final SourceType sourceType;
  private final int line;
  private final int column;

  public static ParseException forText(String message, Throwable cause, String errorId,
                                       String text, int line, int column) {
    return new ParseException(message, cause, errorId, text, SourceType.TEXT, line, column);
  }

  // constructors ----------------------------------------------------------------------------------------------------
/*
  private ParseException(String message, String parsedText) {
    this(message, parsedText, -1, -1);
  }

  private ParseException(String message, String parsedText, int line, int column) {
    this(message, null, parsedText, line, column);
  }

  private ParseException(String message, Throwable cause, String parsedText, int line, int column) {
    this(null, message, cause, null, parsedText, line, column);
  }
*/
  protected ParseException(String message, Throwable cause, String errorId,
                           Object source, SourceType sourceType, int line, int column) {
    super(errorId, ExitCodes.SYNTAX_ERROR, message, cause);
    this.source = source;
    this.sourceType = sourceType;
    this.line = line;
    this.column = column;
  }


  // properties ------------------------------------------------------------------------------------------------------

  public Object getSource() {
    return source;
  }

  public SourceType getSourceType() {
    return sourceType;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

}

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

import com.rapiddweller.common.ExceptionUtil;
import com.rapiddweller.common.TextFileLocation;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * {@link ParseException} child class that represents a syntax error.
 * Created: 24.03.2011 11:49:34
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class SyntaxError extends ApplicationException {

  private final Object source;
  private final SourceType sourceType;
  private final TextFileLocation location;

  public static SyntaxError forXmlDocument(String message, Throwable cause, String uri, int line, int column) {
    return new SyntaxError(message, cause, CommonErrorIds.XML_SYNTAX, uri, SourceType.URI, createLocation(line, column));
  }

  public static SyntaxError forXmlDocument(String message, String uri, String errorId) {
    return new SyntaxError(message, null, errorId, uri, SourceType.URI, createLocation(uri));
  }

  public static SyntaxError forText(String message, Throwable cause, String errorId,
                                    String text, int line, int column) {
    return new SyntaxError(message, cause, errorId, text, SourceType.TEXT, createLocation(line, column));
  }

  public static SyntaxError forText(String message, Throwable cause, String errorId, String text) {
    return new SyntaxError(message, cause, errorId, text, SourceType.TEXT, null);
  }

  public static SyntaxError forMissingInfo(String message) {
    return new SyntaxError(message, null, CommonErrorIds.INFO_MISSING, null, SourceType.MISSING, null);
  }

  public static SyntaxError forXmlAttribute(String message, Throwable cause, String errorId, Attr attribute) {
    if (message == null) {
      message = "Illegal attribute value for " + attribute.getOwnerElement().getNodeName() + '.'
          + attribute.getName() + ": '" + attribute.getValue() + "'";
    }
    return new SyntaxError(message, cause, errorId, attribute, SourceType.XML_ATTRIBUTE,
        getLocation(attribute.getOwnerElement()));
  }

  public static SyntaxError forXmlElement(String message, Throwable cause, String errorId, Element element) {
    return new SyntaxError(message, cause, errorId, element, SourceType.XML_ELEMENT, getLocation(element));
  }

  private SyntaxError(String message, Throwable cause, String errorId,
                        Object source, SourceType sourceType, TextFileLocation location) {
    super(ExceptionUtil.formatMessageWithLocation(message, location), cause, errorId, ExitCodes.SYNTAX_ERROR);
    this.source = source;
    this.sourceType = sourceType;
    this.location = location;
  }

  public Object getSource() {
    return source;
  }

  public SourceType getSourceType() {
    return sourceType;
  }

  public TextFileLocation getLocation() {
    return location;
  }

  private static TextFileLocation getLocation(Element element) {
    TextFileLocation textFileLocation = (TextFileLocation) element.getUserData(TextFileLocation.LOCATION_DATA_KEY);
    if (textFileLocation == null) {
      textFileLocation = new TextFileLocation(null, -1, -1, -1, -1);
    }
    return textFileLocation;
  }

  private static TextFileLocation createLocation(String uri) {
    return new TextFileLocation(uri, -1, -1, -1, -1);
  }

  private static TextFileLocation createLocation(int line, int column) {
    return new TextFileLocation(null, line, column, line, column);
  }
}

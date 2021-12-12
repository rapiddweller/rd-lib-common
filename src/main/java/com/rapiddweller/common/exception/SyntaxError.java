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

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * {@link ParseException} child class that represents a syntax error.
 * Created: 24.03.2011 11:49:34
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class SyntaxError extends ParseException {

  public static SyntaxError forUri(String message, String uri, String errorId) {
    return forUri(message, null, errorId, uri, -1, -1);
  }

  public static SyntaxError forUri(String message, Throwable cause, String errorId,
                                   String uri, int line, int column) {
    return new SyntaxError(message, cause, errorId, uri, SourceType.URI, line, column);
  }

  public static SyntaxError forText(String message, Throwable cause, String errorId,
                                    String text, int line, int column) {
    return new SyntaxError(message, cause, errorId, text, SourceType.TEXT, line, column);
  }

  public static SyntaxError forNothing(String message, Throwable cause) {
    return forNothing(message, cause, null);
  }

  public static SyntaxError forNothing(String message, Throwable cause, String errorId) {
    return new SyntaxError(message, cause, errorId, null, SourceType.NOTHING, -1, -1);
  }

  public static SyntaxError forXmlElement(String message, Throwable cause, Element element) {
    return forXmlElement(message, cause, null, element);
  }

  public static SyntaxError forXmlElement(String message, Throwable cause, String errorId, Element element) {
    return new SyntaxError(message, cause, errorId, element, SourceType.XML_ELEMENT, -1, -1);
  }

  public static SyntaxError forXmlAttribute(String message, Attr attribute) {
    return new SyntaxError(message, null, null, attribute, SourceType.XML_ATTRIBUTE, -1, -1);
  }

  public static SyntaxError forXmlAttribute(String message, Throwable cause, String errorId, Attr attribute) {
    if (message == null) {
      message = "Illegal attribute value for " + attribute.getOwnerElement().getNodeName() + '.'
          + attribute.getName() + ": '" + attribute.getValue() + "'";
    }
    return new SyntaxError(message, cause, errorId, attribute, SourceType.XML_ATTRIBUTE, -1, -1);
  }

  protected SyntaxError(String message, Throwable cause, String errorId,
                        Object source, SourceType sourceType, int line, int column) {
    super(message, cause, errorId, source, sourceType, line, column);
  }

}

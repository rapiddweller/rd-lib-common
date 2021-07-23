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

package com.rapiddweller.common.ui;

/**
 * Error with localizable message.
 * Created at 14.12.2008 13:23:51
 *
 * @author Volker Bergmann
 * @since 0.4.7
 */
public class I18NError extends RuntimeException {

  private static final long serialVersionUID = -7876200178254927951L;

  private Object[] parameters;

  /**
   * Instantiates a new 18 n error.
   */
  public I18NError() {
    super();
  }

  /**
   * Instantiates a new 18 n error.
   *
   * @param code the code
   */
  public I18NError(String code) {
    super(code);
  }

  /**
   * Instantiates a new 18 n error.
   *
   * @param cause the cause
   */
  public I18NError(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new 18 n error.
   *
   * @param code       the code
   * @param cause      the cause
   * @param parameters the parameters
   */
  public I18NError(String code, Throwable cause, Object... parameters) {
    super(code, cause);
    this.parameters = parameters;
  }

  /**
   * Render message string.
   *
   * @param i18n the 18 n
   * @return the string
   */
  public String renderMessage(I18NSupport i18n) {
    String message = getMessage();
    return renderMessage(message, i18n, parameters);
  }

  /**
   * Render message string.
   *
   * @param message    the message
   * @param i18n       the 18 n
   * @param parameters the parameters
   * @return the string
   */
  public static String renderMessage(String message, I18NSupport i18n, Object... parameters) {
    return i18n.format("error." + message, parameters);
  }

}

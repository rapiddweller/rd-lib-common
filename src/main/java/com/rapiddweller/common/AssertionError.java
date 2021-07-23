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

/**
 * Child class of {@link java.lang.AssertionError} that indicates the failure of an assertion.
 * Created: 19.05.2011 16:25:34
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class AssertionError extends java.lang.AssertionError {

  private static final long serialVersionUID = -1564741282379380659L;

  /**
   * Instantiates a new Assertion error.
   *
   * @param message the message
   */
  public AssertionError(String message) {
    super(message);
  }

}

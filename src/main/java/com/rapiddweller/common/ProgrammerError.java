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
 * {@link RuntimeException} child class that indicates a fault made by the programmer.
 * Created: 23.03.2011 12:18:46
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ProgrammerError extends RuntimeException {

  private static final long serialVersionUID = -5982302088793372294L;

  /**
   * Instantiates a new Programmer error.
   */
  public ProgrammerError() {
    super();
  }

  /**
   * Instantiates a new Programmer error.
   *
   * @param message the message
   * @param cause   the cause
   */
  public ProgrammerError(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new Programmer error.
   *
   * @param message the message
   */
  public ProgrammerError(String message) {
    super(message);
  }

  /**
   * Instantiates a new Programmer error.
   *
   * @param cause the cause
   */
  public ProgrammerError(Throwable cause) {
    super(cause);
  }

}

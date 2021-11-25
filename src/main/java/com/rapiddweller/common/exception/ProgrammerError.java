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
 * {@link RuntimeException} child class that indicates a fault made by the programmer.
 * Created: 23.03.2011 12:18:46
 * @author Volker Bergmann
 * @since 0.5.8
 */
public abstract class ProgrammerError extends ApplicationException {

  protected ProgrammerError(String errorCode, String message) {
    this(errorCode, message, null);
  }

  protected ProgrammerError(String errorCode, String message, Throwable cause) {
    super(errorCode, ExitCodes.INTERNAL_SOFTWARE_ERROR, message, cause);
  }

}

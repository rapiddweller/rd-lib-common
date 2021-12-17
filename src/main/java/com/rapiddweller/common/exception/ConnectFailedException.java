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
 * Indicates a failure in establishing a connection.
 * Created at 03.05.2008 09:27:34
 * @author Volker Bergmann
 * @since 0.4.3
 */
public class ConnectFailedException extends ApplicationException {

  public ConnectFailedException(String message) {
    this(message, null);
  }

  public ConnectFailedException(String message, Throwable cause) {
    this(message, cause, null);
  }

  public ConnectFailedException(String message, Throwable cause, String errorId) {
    super(message, cause, errorId, ExitCodes.MISCELLANEOUS_ERROR);
  }

}

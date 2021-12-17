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

import com.rapiddweller.common.exception.ApplicationException;

/**
 * Parent exception class for operation failures.<br><br>
 * Created: 27.07.2019 23:58:38
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class OperationFailed extends ApplicationException {

  public OperationFailed(String errorId, int exitCode, String message) {
    this(message, null, errorId, exitCode);
  }

  public OperationFailed(String message, Throwable cause, String errorId, int exitCode) {
    super(message, cause, errorId, exitCode);
  }

}

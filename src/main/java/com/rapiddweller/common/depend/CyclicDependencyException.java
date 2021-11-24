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

package com.rapiddweller.common.depend;

import com.rapiddweller.common.exception.ApplicationException;
import com.rapiddweller.common.exception.ExitCodes;

/**
 * Exception that signals a cyclic dependency.
 * @author Volker Bergmann
 * @since 0.3.04
 */
public class CyclicDependencyException extends ApplicationException {

  public CyclicDependencyException(String message) {
    this(message, null);
  }

  public CyclicDependencyException(String message, Throwable cause) {
    super(null, ExitCodes.INTERNAL_SOFTWARE_ERROR, message, cause);
  }

}

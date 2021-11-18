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
import com.rapiddweller.common.exception.ExitCodes;

/**
 * Signals that an object was not found.<br/><br/>
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class ObjectNotFoundException extends ApplicationException {

  public ObjectNotFoundException(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

  public ObjectNotFoundException(String message) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message);
  }

}

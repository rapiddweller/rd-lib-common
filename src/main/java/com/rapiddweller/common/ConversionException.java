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

import com.rapiddweller.common.exception.ExitCodes;

/**
 * Indicates failure of a conversion operation.<br/><br/>
 * Created: 08.03.2006 14:56:58
 * @author Volker Bergmann
 */
public class ConversionException extends OperationFailed {

  public ConversionException(String message) {
    this(message, null);
  }

  public ConversionException(String message, Throwable cause) {
    super(message, cause, null, ExitCodes.INTERNAL_SOFTWARE_ERROR);
  }

}

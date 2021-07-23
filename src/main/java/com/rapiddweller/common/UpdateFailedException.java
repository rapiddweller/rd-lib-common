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
 * Indicates that the update of some data failed.
 * Created: 18.01.2007 20:35:12
 *
 * @author Volker Bergmann
 */
public class UpdateFailedException extends OperationFailedException {

  private static final long serialVersionUID = 1301603724337727327L;

  /**
   * Instantiates a new Update failed exception.
   */
  public UpdateFailedException() {
  }

  /**
   * Instantiates a new Update failed exception.
   *
   * @param message the message
   */
  public UpdateFailedException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Update failed exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public UpdateFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new Update failed exception.
   *
   * @param cause the cause
   */
  public UpdateFailedException(Throwable cause) {
    super(cause);
  }

}

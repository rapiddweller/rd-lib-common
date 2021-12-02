/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that an illegal argument was used.<br/><br/>
 * Created: 19.11.2021 14:38:45
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class IllegalArgumentError extends ApplicationException {

  public IllegalArgumentError(String message) {
    this(message, null);
  }

  public IllegalArgumentError(String message, Throwable cause) {
    this(message, cause, null);
  }

  public IllegalArgumentError(String message, Throwable cause, String errorId) {
    super(errorId, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

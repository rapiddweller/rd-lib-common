/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that the .<br/><br/>
 * Created: 23.11.2021 16:45:44
 * @author Volker Bergmann
 * @since 2.1.0
 */
public class IllegalOperationError extends ProgrammerError {

  public IllegalOperationError(String message) {
    this(message, null);
  }

  public IllegalOperationError(String message, Throwable cause) {
    super(null, message, cause);
  }

}

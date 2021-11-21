/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that an operation was cancelled.<br/><br/>
 * Created: 21.11.2021 06:43:56
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OperationCancelledException extends ApplicationException {

  public OperationCancelledException(String message) {
    this(message, null);
  }

  public OperationCancelledException(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

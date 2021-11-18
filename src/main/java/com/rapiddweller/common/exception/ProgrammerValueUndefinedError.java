/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that a programmer left a value undefined.<br/><br/>
 * Created: 18.11.2021 08:34:45
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ProgrammerValueUndefinedError extends ProgrammerError {

  public ProgrammerValueUndefinedError(String message) {
    this(message, null);
  }

  public ProgrammerValueUndefinedError(String message, Throwable cause) {
    super(CommonErrorCodes.PROGRAMMER_VALUE_UNDEFINED, message, cause);
  }

}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that a programmer has left an object in a state which is unexpected or problematic.<br/><br/>
 * Created: 18.11.2021 08:39:35
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ProgrammerStateError extends ProgrammerError {

  public ProgrammerStateError(String message) {
    this(message, null);
  }

  public ProgrammerStateError(String message, Throwable cause) {
    super(CommonErrorCodes.PROGRAMMER_STATE_ERROR, message, cause);
  }

}

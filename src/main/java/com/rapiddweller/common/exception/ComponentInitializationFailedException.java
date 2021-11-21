/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailedException;

/**
 * Signals that the initialization of a component failed.<br/><br/>
 * Created: 20.11.2021 19:03:38
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ComponentInitializationFailedException extends OperationFailedException {

  public ComponentInitializationFailedException(String message) {
    this(message, null);
  }

  public ComponentInitializationFailedException(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

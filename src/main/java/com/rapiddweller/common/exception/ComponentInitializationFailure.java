/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailed;

/**
 * Signals that the initialization of a component failed.<br/><br/>
 * Created: 20.11.2021 19:03:38
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ComponentInitializationFailure extends OperationFailed {

  public ComponentInitializationFailure(String message) {
    this(message, null);
  }

  public ComponentInitializationFailure(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

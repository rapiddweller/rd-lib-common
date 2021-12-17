/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailed;

/**
 * Signals that an operation was invoked in an illegal manner,
 * eg. multithreaded invocation on a not thread-safe operation
 * or invocation of a private method.<br/><br/>
 * Created: 24.11.2021 11:27:12
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class IllegalAccess extends OperationFailed {

  public IllegalAccess(String message) {
    this(message, null);
  }

  public IllegalAccess(String message, Throwable cause) {
    super(message, cause, null, ExitCodes.INTERNAL_SOFTWARE_ERROR);
  }

}

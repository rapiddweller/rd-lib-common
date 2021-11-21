/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailedException;

/**
 * TODO JavaDoc.<br/><br/>
 * Created: 19.11.2021 22:59:22
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class AccessFailedException extends OperationFailedException {

  public AccessFailedException(String message) {
    this(message, null);
  }

  public AccessFailedException(String message, Throwable cause) {
    super(null, ExitCodes.INTERNAL_SOFTWARE_ERROR, message, cause);
  }

}

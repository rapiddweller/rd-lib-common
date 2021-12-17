/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailed;

/**
 * Signals that access to a software element failed.<br/><br/>
 * Created: 19.11.2021 22:59:22
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class AccessFailed extends OperationFailed {

  public AccessFailed(String message) {
    this(message, null);
  }

  public AccessFailed(String message, Throwable cause) {
    super(message, cause, null, ExitCodes.INTERNAL_SOFTWARE_ERROR);
  }

}

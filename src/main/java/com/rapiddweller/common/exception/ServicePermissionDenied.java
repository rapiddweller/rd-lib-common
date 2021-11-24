/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that a service operation was requested without having the necessary permissions.<br/><br/>
 * Created: 24.11.2021 09:47:49
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ServicePermissionDenied extends ServiceException {

  protected ServicePermissionDenied(String message) {
    this(message, null);
  }

  protected ServicePermissionDenied(String message, Throwable cause) {
    super(null, ExitCodes.PERMISSION_ERROR, message, cause);
  }

}

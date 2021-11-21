/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that a service is not available.<br/><br/>
 * Created: 19.11.2021 14:44:56
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ServiceUnavailableException extends ServiceException {

  protected ServiceUnavailableException(String message) {
    this(message, null);
  }

  protected ServiceUnavailableException(String message, Throwable cause) {
    super(null, ExitCodes.SERVICE_UNAVAILABLE, message, cause);
  }

}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that an operation failed on a service which has been connected
 * and seemed to be working properly.<br/><br/>
 * Created: 19.11.2021 15:14:15
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ServiceFailedException extends ServiceException {

  protected ServiceFailedException(String message) {
    this(message, null);
  }

  protected ServiceFailedException(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

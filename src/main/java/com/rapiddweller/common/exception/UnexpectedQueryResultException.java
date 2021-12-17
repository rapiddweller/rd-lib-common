/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals an unexpected result of a query.<br/><br/>
 * Created: 18.11.2021 19:16:56
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class UnexpectedQueryResultException extends ApplicationException {

  public UnexpectedQueryResultException(String message) {
    this(message, null);
  }

  public UnexpectedQueryResultException(String message, Throwable cause) {
    super(message, cause, null, ExitCodes.MISCELLANEOUS_ERROR);
  }

}

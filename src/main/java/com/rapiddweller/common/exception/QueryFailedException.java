/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailedException;

/**
 * Signals that a query has failed.<br/><br/>
 * Created: 20.11.2021 18:55:57
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class QueryFailedException extends OperationFailedException {

  public QueryFailedException(String message) {
    this(message, null);
  }

  public QueryFailedException(String message, Throwable cause) {
    super(null, ExitCodes.INTERNAL_SOFTWARE_ERROR, message, cause);
  }

}

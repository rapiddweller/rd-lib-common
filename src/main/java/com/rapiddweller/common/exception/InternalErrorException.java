/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals en internal error. This may be used as a space holder for error conditions
 * which still need to be analysed and assigned to a more concrete exception type in the future.<br/><br/>
 * Created: 19.11.2021 15:00:53
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class InternalErrorException extends ApplicationException {

  public InternalErrorException(String message) {
    this(message, null);
  }

  public InternalErrorException(String message, Throwable cause) {
    super(message, cause, null, ExitCodes.INTERNAL_SOFTWARE_ERROR);
  }

}

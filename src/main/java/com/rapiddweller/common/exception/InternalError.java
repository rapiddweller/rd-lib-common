/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals en internal error. This may be used as a space holder for error conditions
 * which still need to be analysed and assigned to a more concrete exception type in the future.<br/><br/>
 * Created: 19.11.2021 15:00:53
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class InternalError extends ApplicationException {

  public InternalError(String message) {
    this(message, null);
  }

  public InternalError(String message, Throwable cause) {
    super(null, ExitCodes.INTERNAL_SOFTWARE_ERROR, message, cause);
  }

}

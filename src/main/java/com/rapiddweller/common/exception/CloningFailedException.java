/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailedException;

/**
 * Signals that cloning failed.<br/><br/>
 * Created: 19.11.2021 20:44:24
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CloningFailedException extends OperationFailedException {

  protected CloningFailedException(String message) {
    this(message, null);
  }

  protected CloningFailedException(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

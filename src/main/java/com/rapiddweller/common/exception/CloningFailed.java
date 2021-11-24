/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailed;

/**
 * Signals that cloning failed.<br/><br/>
 * Created: 19.11.2021 20:44:24
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CloningFailed extends OperationFailed {

  protected CloningFailed(String message) {
    this(message, null);
  }

  protected CloningFailed(String message, Throwable cause) {
    super(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

}

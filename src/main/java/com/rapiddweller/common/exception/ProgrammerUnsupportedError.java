/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that code was invoked which is not yet supported by the programmer.<br/><br/>
 * Created: 18.11.2021 08:52:11
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ProgrammerUnsupportedError extends ProgrammerError {

  protected ProgrammerUnsupportedError(String message) {
    super(CommonErrorCodes.PROGRAMMER_UNSUPPORTED, message);
  }

}

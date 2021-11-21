/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.CommonErrorCodes;

/**
 * Signals that a command line argument is missing.<br/><br/>
 * Created: 17.11.2021 22:55:23
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CLIMissingArgumentException extends CLIException {

  public CLIMissingArgumentException(String message) {
    super(CommonErrorCodes.MISSING_CLI_ARGUMENT, message);
  }

}

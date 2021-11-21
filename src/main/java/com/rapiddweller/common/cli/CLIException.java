/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.ExitCodes;
import com.rapiddweller.common.exception.ApplicationException;

/**
 * Signals an error concerning command line arguments.<br/><br/>
 * Created: 17.11.2021 22:38:32
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CLIException extends ApplicationException {

  public CLIException(String errorId, String message) {
    super(errorId, ExitCodes.COMMAND_LINE_USAGE_ERROR, message);
  }

}

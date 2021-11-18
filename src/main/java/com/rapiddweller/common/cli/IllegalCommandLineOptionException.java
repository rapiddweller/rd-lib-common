/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.CommonErrorCodes;

/**
 * Signals the use of an illegal command line option.<br/><br/>
 * Created: 17.11.2021 22:40:36
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class IllegalCommandLineOptionException extends CommandLineArgumentException {

  public IllegalCommandLineOptionException(String message) {
    super(CommonErrorCodes.ILLEGAL_CLI_OPTION, message);
  }

}

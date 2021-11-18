/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.CommonErrorCodes;

/**
 * Signals an illegal command line argument.<br/><br/>
 * Created: 18.11.2021 17:54:33
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class IllegaCommandLineArgumentException extends CommandLineArgumentException {

  public IllegaCommandLineArgumentException(String message) {
    super(CommonErrorCodes.ILLEGAL_CLI_OPTION, message);
  }

}
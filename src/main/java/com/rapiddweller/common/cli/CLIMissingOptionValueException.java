/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.CommonErrorIds;

/**
 * Signals that the value is missing for a command line option.<br/><br/>
 * Created: 18.11.2021 07:34:51
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CLIMissingOptionValueException extends CLIException {

  public CLIMissingOptionValueException(String message) {
    super(CommonErrorIds.CLI_MISSING_OPTION_VALUE, message);
  }

}

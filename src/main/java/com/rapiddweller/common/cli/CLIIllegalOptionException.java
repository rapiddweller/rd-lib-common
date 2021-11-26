/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.CommonErrorIds;

/**
 * Signals the use of an illegal command line option.<br/><br/>
 * Created: 17.11.2021 22:40:36
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CLIIllegalOptionException extends CLIException {

  private final String optionName;

  public CLIIllegalOptionException(String optionName) {
    this(optionName, null);
  }

  public CLIIllegalOptionException(String optionName, String errorId) {
    super(errorId, "Illegal command line option: " + optionName);
    this.optionName = optionName;
  }

  public String getOptionName() {
    return optionName;
  }

}

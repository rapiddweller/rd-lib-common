/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.exception.CommonErrorIds;

/**
 * Signals that an illegal value was used for a command line option.<br/><br/>
 * Created: 25.11.2021 07:31:22
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CLIIllegalOptionValueException extends CLIException {

  private final String name;
  private final String value;

  public CLIIllegalOptionValueException(String name, String value) {
    this(name, value, CommonErrorIds.CLI_ILLEGAL_OPTION_VALUE, null);
  }

  public CLIIllegalOptionValueException(String name, String value, String errorId, String predefMessage) {
    super(errorId, haveMessage(name, value, predefMessage));
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  private static String haveMessage(String name, String value, String predefMessage) {
    return (predefMessage != null ? predefMessage : "Illegal value for command line option " + name + ": " + value);
  }

}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

/**
 * Represents a command line argument.<br/><br/>
 * Created: 21.10.2021 15:42:04
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class CommandLineArgument {

  private final String property;
  private final boolean required;

  public CommandLineArgument(String property, boolean required) {
    this.property = property;
    this.required = required;
  }

  public String getProperty() {
    return property;
  }

  public boolean isRequired() {
    return required;
  }

}

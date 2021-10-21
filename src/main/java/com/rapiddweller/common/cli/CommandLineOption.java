/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

/**
 * Represents a parameterized option on the command line.<br/><br/>
 * Created: 21.10.2021 15:40:51
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class CommandLineOption extends CommandLineItem {

  public CommandLineOption(String property, String longName, String shortName) {
    super(property, longName, shortName);
  }

}

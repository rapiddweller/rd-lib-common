/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

/**
 * Represents a command line flag which may be true or false.<br/><br/>
 * Created: 21.10.2021 15:36:51
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class CommandLineFlag extends CommandLineItem {

  public CommandLineFlag(String property, String longName, String shortName) {
    super(property, longName, shortName);
  }

}

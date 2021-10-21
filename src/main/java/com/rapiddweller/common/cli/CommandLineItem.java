/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

/**
 * Parent class for command line items.<br/><br/>
 * Created: 21.10.2021 15:06:14
 * @author Volker Bergmann
 * @since 1.1.4
 */
public abstract class CommandLineItem {

  private final String property;
  private final String longName;
  private final String shortName;

  protected CommandLineItem(String property, String longName, String shortName) {
    this.property = property;
    this.longName = longName;
    this.shortName = shortName;
  }

  public String getProperty() {
    return property;
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

}

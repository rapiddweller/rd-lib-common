/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

/**
 * Parent class for command line configurations.<br/><br/>
 * Created: 21.10.2021 16:10:12
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class CommandLineConfig {

  private boolean help;
  private boolean version;

  public boolean isHelp() {
    return help;
  }

  public void setHelp(boolean help) {
    this.help = help;
  }

  public boolean isVersion() {
    return version;
  }

  public void setVersion(boolean version) {
    this.version = version;
  }

}

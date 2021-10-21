/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

/**
 * JavaBean for command line parsing tests.<br/><br/>
 * Created: 21.10.2021 15:08:48
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class SampleConfig extends CommandLineConfig {

  private boolean help;
  private boolean version;
  private SampleEnum sample = SampleEnum.VAL1;
  private OldEnum old = OldEnum.OLD1;
  private String file = "default.txt";
  private String addendum;

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

  public SampleEnum getSample() {
    return sample;
  }

  public void setSample(SampleEnum sample) {
    this.sample = sample;
  }

  public OldEnum getOld() {
    return old;
  }

  public void setOld(OldEnum old) {
    this.old = old;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getAddendum() {
    return addendum;
  }

  public void setAddendum(String addendum) {
    this.addendum = addendum;
  }

}

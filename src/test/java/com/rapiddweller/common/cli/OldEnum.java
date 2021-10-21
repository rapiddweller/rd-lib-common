/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * 'Old style' enumeration class for testing.<br/><br/>
 * Created: 21.10.2021 15:11:43
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class OldEnum {

  private static final Map<String, OldEnum> INSTANCES = new HashMap<>();

  public static final OldEnum OLD1 = new OldEnum("old1");
  public static final OldEnum OLD2 = new OldEnum("old2");

  public static OldEnum getInstance(String name) {
    return INSTANCES.get(name);
  }

  private String name;

  private OldEnum(String name) {
    this.name = name;
    INSTANCES.put(name, this);
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}

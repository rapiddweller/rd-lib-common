/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides configuration-related features.<br/><br/>
 * Created: 14.10.2021 16:38:30
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class ConfigUtil {

  private static Map<String, String> testSettings;

  static {
    try {
      testSettings = IOUtil.readProperties(userConfigFolder() + "/testing.properties");
    } catch (ConfigurationError | IOException e) {
      testSettings = new HashMap<>();
    }
  }

  private ConfigUtil() {
    // private constructor to prevent instantiation.
  }

  public static String userConfigFolder() {
    return SystemInfo.getUserHome() + SystemInfo.getFileSeparator() + "rapiddweller";
  }

  /** Feature to disable tests which is activated by having a file ~/rapiddweller/testing.properties
   *  and assigning false to a test code, like mysql=false */
  public static boolean isTestActive(String code) {
    String setting = testSettings.get(code);
    return (!"false".equalsIgnoreCase(setting));
  }
}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

  public static String configFilePathDefaultLocations(String filename, String projectFolder) throws IOException {
    projectFolder = stripTrailingFileSeparator(projectFolder);
    return configFilePathMulti(filename, defaultConfigLocations(projectFolder));
  }

  public static String[] defaultConfigLocations(String projectFolder) {
    return new String[] {
        projectFolder,
        projectFolder + SystemInfo.getFileSeparator() + "conf",
        ".",
        userConfigFolder().getAbsolutePath()
    };
  }

  /** Searches a configuration file in different paths and returns the relative or absolute path
   *  (including file name) where it was found. The search is performed in the file system
   *  as well as in the class path.
   *  @throws ConfigurationError if the file was not found in any of the search locations */
  public static String configFilePathMulti(String filename, String... searchLocations) throws IOException {
    for (String folder : searchLocations) {
      String path = filePath(filename, folder);
      if (path != null) {
        return path;
      }
    }
    // if nothing was found, then throw an exception
    throw new ConfigurationError("No config file '" + filename + "' found in " + Arrays.toString(searchLocations));
  }

  /** Checks if a file of given filename exists in the specified folder. If a file is found, its path is returned,
   *  otherwise null. The search is performed in the file system as well as in the class path. */
  public static String filePath(String filename, String folder) throws IOException {
    // first search in file system...
    File file = FileUtil.getFileIgnoreCase(new File(folder, filename), false);
    if (file.exists()) {
      return file.getCanonicalPath();
    }
    // ...then search in class path
    String path = (".".equals(folder) ? filename : folder + '/' + filename);
    if (IOUtil.isURIAvailable(path)) {
      return path;
    }
    // if the file was not found, then return null
    return null;
  }

  /** Feature to disable tests which is activated by having a file ~/rapiddweller/testing.properties
   *  and assigning false to a test code, like mysql=false */
  public static boolean isTestActive(String code) {
    String setting = testSettings.get(code);
    return (!"false".equalsIgnoreCase(setting));
  }

  /** Returns a {@link java.io.File} object that points to the root of all cache folders.
   *  If that folder is not present yet, it is created. */
  public static File commonCacheFolder() {
    File result = new File(ConfigUtil.userConfigFolder(), "cache");
    FileUtil.ensureDirectoryExists(result);
    return result;
  }

  public static File userConfigFolder() {
    return new File(SystemInfo.getUserHome(), "rapiddweller");
  }

  private static String stripTrailingFileSeparator(String path) {
    char fs = SystemInfo.getFileSeparator();
    if (path.length() > 1 && path.charAt(path.length() - 1) == fs) {
      path = path.substring(0, path.length() - 1);
    }
    return path;
  }

}

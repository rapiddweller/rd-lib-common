/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Provides the user with the Java system properties related to the Runtime System.
 * Created: 06.01.2007 19:10:02
 * @author Volker Bergmann
 */
public final class SystemInfo {

  public static final String USER_LANGUAGE_KEY = "user.language";
  public static final String FILE_ENCODING_KEY = "file.encoding";
  public static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
  public static final String USER_HOME_KEY = "user.home";
  public static final String USER_NAME_KEY = "user.name";
  public static final String USER_DIR_KEY = "user.dir";
  public static final String FILE_SEPARATOR_KEY = "file.separator";
  public static final String PATH_SEPARATOR_KEY = "path.separator";
  public static final String LINE_SEPARATOR_KEY = "line.separator";
  public static final String OS_VERSION_KEY = "os.version";
  public static final String OS_ARCH_KEY = "os.arch";
  public static final String OS_NAME_KEY = "os.name";

  public static final String LF = getLineSeparator();

  /** private constructor to prevent instantiation of this utility class. */
  private SystemInfo() {
    // private constructor to prevent instantiation of this utility class
  }

  public static String getOsName() {
    return System.getProperty(OS_NAME_KEY);
  }

  public static String getOsArchitecture() {
    return System.getProperty(OS_ARCH_KEY);
  }

  public static String getOsVersion() {
    return System.getProperty(OS_VERSION_KEY);
  }

  public static String getLineSeparator() {
    return System.getProperty(LINE_SEPARATOR_KEY);
  }

  public static void setLineSeparator(String lineSeparator) {
    System.setProperty(LINE_SEPARATOR_KEY, lineSeparator);
  }

  public static String getPathSeparator() {
    return System.getProperty(PATH_SEPARATOR_KEY);
  }

  public static char getFileSeparator() {
    return System.getProperty(FILE_SEPARATOR_KEY).charAt(0);
  }

  public static File getCurrentDirFile() {
    return new File(getCurrentDir());
  }

  public static String getCurrentDir() {
    return System.getProperty(USER_DIR_KEY);
  }

  public static String getUserName() {
    return System.getProperty(USER_NAME_KEY);
  }

  public static String getUserHome() {
    return System.getProperty(USER_HOME_KEY);
  }

  public static String getTempDir() {
    return System.getProperty(JAVA_IO_TMPDIR_KEY);
  }

  public static String getFileEncoding() {
    return System.getProperty(FILE_ENCODING_KEY);
  }

  public static void setFileEncoding(String encoding) {
    System.setProperty(FILE_ENCODING_KEY, encoding);
  }

  public static String getUserLanguage() {
    return System.getProperty(USER_LANGUAGE_KEY);
  }

  public static boolean isWindows() {
    return getOsName().toLowerCase().startsWith("win");
  }

  public static boolean isMacOsx() {
    return getOsName().toLowerCase().startsWith("mac");
  }

  public static boolean isLinux() {
    return getOsName().toLowerCase().startsWith("linux");
  }

  public static boolean isSolaris() {
    return getOsName().toLowerCase().startsWith("sun");
  }

  public static Charset getCharset() {
    return Charset.forName(getFileEncoding());
  }

}

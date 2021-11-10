/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 * Executes shell commands and shell files.
 * Created at 05.08.2008 07:40:00
 * @author Volker Bergmann
 * @since 0.4.5
 */
public class ShellUtil {

  private static final Logger logger = LoggerFactory.getLogger(ShellUtil.class);

  public static int runShellCommands(ReaderLineIterator iterator, String shell, Writer outputWriter, ErrorHandler errorHandler) {
    int result = 0;
    while (iterator.hasNext()) {
      String command = iterator.next().trim();
      if (command.length() > 0) {
        result = runShellCommand(command, shell, outputWriter, errorHandler);
      }
    }
    return result;
  }

  public static int runShellCommand(String command, String shell, Writer outputWriter, ErrorHandler errorHandler) {
    return runShellCommand(command, shell, outputWriter, new File(SystemInfo.getCurrentDir()), errorHandler);
  }

  public static int runShellCommand(String command, String shell, Writer outputWriter, File directory, ErrorHandler errorHandler) {
    logger.debug(command);
    try {
      String[] invocation = (StringUtil.isEmpty(shell) ? defaultShellInvocation() : shellInvocation(shell));
      Process process = Runtime.getRuntime().exec(new String[] { invocation[0], invocation[1], command }, null, directory);
      return execute(process, command, outputWriter, errorHandler);
    } catch (FileNotFoundException e) {
      handleError(e, command, errorHandler);
      return 2;
    } catch (Exception e) {
      handleError(e, command, errorHandler);
      return 1;
    }
  }

  private static String[] shellInvocation(String shell) {
    if (StringUtil.isEmpty(shell)) {
      return defaultShellInvocation();
    } else if (shell.toLowerCase().contains("sh")) {
      return new String[] { shell, "-c"};
    } else {
      return new String[] { shell, "/c" };
    }
  }

  private static String[] defaultShellInvocation() {
    if (SystemInfo.isWindows()) {
      return new String[] { "cmd.exe", "/C" };
    } else {
      return new String[] { "sh", "-c" };
    }
  }

  public static int runShellCommand(String[] cmdArray, Writer outputWriter, File directory, ErrorHandler errorHandler) {
    String description = renderCmdArray(cmdArray);
    if (logger.isDebugEnabled()) {
      logger.debug(description);
    }
    try {
      Process process = Runtime.getRuntime().exec(cmdArray, null, directory);
      return execute(process, description, outputWriter, errorHandler);
    } catch (Exception e) {
      handleError(e, description, errorHandler);
      return -1;
    }
  }

  private static void handleError(Exception e, String description, ErrorHandler errorHandler) {
    errorHandler.handleError("Error in shell invocation: " + description, e);
  }

  // private helpers ---------------------------------------------------------

  private static int execute(Process process, String description, Writer outputWriter, ErrorHandler errorHandler)
      throws IOException, InterruptedException {
    String lf = SystemInfo.getLineSeparator();
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    // read the output from the command
    boolean firstLine = true;
    String s;
    while ((s = stdIn.readLine()) != null) {
      if (outputWriter != null) {
        if (firstLine) {
          firstLine = false;
        } else {
          outputWriter.write(lf);
        }
        outputWriter.write(s);
      }
    }
    if (outputWriter != null) {
      outputWriter.flush();
    }
    // read any errors from the attempted command
    while ((s = stdErr.readLine()) != null) {
      errorHandler.handleError(s);
    }
    process.waitFor();
    int exitValue = process.exitValue();
    if (exitValue != 0) {
      errorHandler.handleError("Process (" + description + ") did not terminate normally: Return code " + exitValue);
    }
    return exitValue;
  }

  private static String renderCmdArray(String[] cmdArray) {
    return ArrayFormat.format(" ", cmdArray);
  }

}

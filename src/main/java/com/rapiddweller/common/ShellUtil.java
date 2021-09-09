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
 *
 * @author Volker Bergmann
 * @since 0.4.5
 */
public class ShellUtil {

  private static final Logger logger = LoggerFactory.getLogger(ShellUtil.class);

  /**
   * Run shell commands int.
   *
   * @param iterator     the iterator
   * @param outputWriter the output writer
   * @param errorHandler the error handler
   * @return the int
   */
  public static int runShellCommands(ReaderLineIterator iterator, Writer outputWriter, ErrorHandler errorHandler) {
    int result = 0;
    while (iterator.hasNext()) {
      String command = iterator.next().trim();
      if (command.length() > 0) {
        result = runShellCommand(command, outputWriter, errorHandler);
      }
    }
    return result;
  }

  /**
   * Run shell command int.
   *
   * @param command      the command
   * @param outputWriter the output writer
   * @param errorHandler the error handler
   * @return the int
   */
  public static int runShellCommand(String command, Writer outputWriter, ErrorHandler errorHandler) {
    return runShellCommand(command, outputWriter, new File(SystemInfo.getCurrentDir()), errorHandler);
  }

  /**
   * Run shell command int.
   *
   * @param command      the command
   * @param outputWriter the output writer
   * @param directory    the directory
   * @param errorHandler the error handler
   * @return the int
   */
  public static int runShellCommand(String command, Writer outputWriter, File directory, ErrorHandler errorHandler) {
    logger.debug(command);
    try {
      Process process = Runtime.getRuntime().exec(command, null, directory);
      return execute(process, command, outputWriter, errorHandler);
    } catch (FileNotFoundException e) {
      errorHandler.handleError("Error in shell invocation: " + command, e);
      return 2;
    } catch (Exception e) {
      errorHandler.handleError("Error in shell invocation: " + command, e);
      return 1;
    }
  }

  /**
   * Run shell command int.
   *
   * @param cmdArray     the cmd array
   * @param outputWriter the output writer
   * @param directory    the directory
   * @param errorHandler the error handler
   * @return the int
   */
  public static int runShellCommand(String[] cmdArray, Writer outputWriter, File directory, ErrorHandler errorHandler) {
    String description = renderCmdArray(cmdArray);
    if (logger.isDebugEnabled()) {
      logger.debug(description);
    }
    try {
      Process process = Runtime.getRuntime().exec(cmdArray, null, directory);
      return execute(process, description, outputWriter, errorHandler);
    } catch (Exception e) {
      errorHandler.handleError("Error in shell invocation: " + description, e);
      return -1;
    }
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

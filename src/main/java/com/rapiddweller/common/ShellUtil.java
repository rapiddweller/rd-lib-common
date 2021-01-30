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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 * Executes shell commands and shell files.
 * Created at 05.08.2008 07:40:00
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class ShellUtil {
	
	private static final Logger logger = LogManager.getLogger(ShellUtil.class);

	public static int runShellCommands(ReaderLineIterator iterator, Writer outputWriter, ErrorHandler errorHandler) {
		int result = 0;
		while (iterator.hasNext()) {
			String command = iterator.next().trim();
			if (command.length() > 0)
				result = runShellCommand(command, outputWriter, errorHandler);
		}
		return result;
    }

	public static int runShellCommand(String command, Writer outputWriter, ErrorHandler errorHandler) {
		return runShellCommand(command, outputWriter, new File(SystemInfo.getCurrentDir()), errorHandler);
	}
	
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

	public static int runShellCommand(String[] cmdArray, Writer outputWriter, File directory, ErrorHandler errorHandler) {
		String description = renderCmdArray(cmdArray);
		if (logger.isDebugEnabled())
			logger.debug(description);
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
		    	if (firstLine)
		    		firstLine = false;
	            else
		            outputWriter.write(lf);
		        outputWriter.write(s);
	    	}	        
	    }
	    if (outputWriter != null)
	    	outputWriter.flush();
	    // read any errors from the attempted command
	    while ((s = stdErr.readLine()) != null) {
	        errorHandler.handleError(s);
	    }
	    process.waitFor();
	    int exitValue = process.exitValue();
	    if (exitValue != 0)
	    	errorHandler.handleError("Process (" + description + ") did not terminate normally: Return code " + exitValue);
	    return exitValue;
    }

	private static String renderCmdArray(String[] cmdArray) {
	    return ArrayFormat.format(" ", cmdArray);
    }

}

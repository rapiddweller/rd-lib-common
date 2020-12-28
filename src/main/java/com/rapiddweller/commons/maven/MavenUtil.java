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
package com.rapiddweller.commons.maven;

import java.io.File;

import com.rapiddweller.commons.ArrayBuilder;
import com.rapiddweller.commons.ErrorHandler;
import com.rapiddweller.commons.ShellUtil;
import com.rapiddweller.commons.SystemInfo;

/**
 * Provides utility methods for Maven invocation and repository access.
 * Created at 17.12.2008 10:32:32
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class MavenUtil {

	public static void invoke(String goal, File folder, boolean online) {
		ArrayBuilder<String> cmdBuilder = new ArrayBuilder<>(String.class);
		
		// maven invocation
		if (SystemInfo.isWindows())
			cmdBuilder.add("mvn.bat");
		else
			cmdBuilder.add("mvn");
		
		// offline parameter?
		if (!online)
			cmdBuilder.add("-o");
		
		// goal
		cmdBuilder.add(goal);
		
		// run
		ShellUtil.runShellCommand(cmdBuilder.toArray(), null, folder, new ErrorHandler(MavenUtil.class));
	}
	
}

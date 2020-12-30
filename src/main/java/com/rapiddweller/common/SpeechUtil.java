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

import java.io.OutputStreamWriter;

/**
 * Provides speech support on Mac systems.
 * Created: 14.09.2010 12:46:10
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class SpeechUtil {

	public static boolean speechSupported() {
		return SystemInfo.isMacOsx();
	}
	
	public static void say(String text) {
		say(text, ErrorHandler.getDefault());
	}
		
	public static void say(String text, ErrorHandler errorHandler) {
		if (!speechSupported())
			errorHandler.handleError("Speech is not supported on this system");
		ShellUtil.runShellCommand("say -v Alex \"" + text + "\"", new OutputStreamWriter(System.out), errorHandler);
	}
	
}

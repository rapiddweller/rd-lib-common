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

import java.util.Collection;

/**
 * Provides utility methods for threading.
 * Created: 26.03.2010 19:26:07
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class ThreadUtil {
	
	private ThreadUtil() {}

	public static <C extends Collection<T>, T extends ThreadAware> boolean allThreadSafe(C elements) {
		for (ThreadAware element : elements)
			if (!element.isThreadSafe())
				return false;
		return true;
    }

	public static <T extends ThreadAware> boolean allThreadSafe(T[] elements) {
		for (ThreadAware element : elements)
			if (!element.isThreadSafe())
				return false;
		return true;
    }

	public static <C extends Collection<T>, T extends ThreadAware> boolean allParallelizable(C elements) {
		for (ThreadAware element : elements)
			if (!element.isParallelizable())
				return false;
		return true;
    }

	public static <T extends ThreadAware> boolean allParallelizable(T[] elements) {
		for (ThreadAware element : elements)
			if (!element.isParallelizable())
				return false;
		return true;
    }

	public static String currentStackTraceAsString() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StringBuilder builder = new StringBuilder();
		builder.append("Current stack trace:").append(SystemInfo.LF);
		for (int i = 2; i < stackTrace.length; i++)
			builder.append(stackTrace[i]).append(SystemInfo.LF);
		return builder.toString();
	}
	
}

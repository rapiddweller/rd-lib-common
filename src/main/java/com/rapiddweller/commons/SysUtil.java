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
package com.rapiddweller.commons;

import java.util.concurrent.Callable;

/**
 * Provides system related utility methods.
 * Created: 21.10.2009 19:26:24
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class SysUtil {

	private SysUtil() { }
	
	public static void runWithSystemProperty(String name, String value, Runnable runner) {
		String oldValue = System.getProperty(name);
		try {
			System.setProperty(name, value);
			runner.run();
		} finally {
			System.setProperty(name, (oldValue != null ? oldValue : ""));
		}
	}
	
	public static <T> T callWithSystemProperty(String name, String value, Callable<T> callee) throws Exception {
		String oldValue = System.getProperty(name);
		try {
			System.setProperty(name, value);
			return callee.call();
		} finally {
			System.setProperty(name, oldValue);
		}
	}

}

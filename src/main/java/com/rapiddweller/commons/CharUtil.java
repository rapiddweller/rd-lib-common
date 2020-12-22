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

/**
 * Provides character related utility methods.
 * Created: 04.08.2011 13:20:52
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class CharUtil {

	private CharUtil() {}
	
	public static int ordinal(char c) {
		return c;
	}
	
	public static char character(int ordinal) {
		return (char) ordinal;
	}
	
}

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utility methods regarding regular expressions.<br><br>
 * Created: 22.09.2019 07:21:55
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class RegexUtil {

	public static String[] parse(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (!matcher.find())
			return null;
		int groupCount = matcher.groupCount();
		String[] result = new String[groupCount];
		for (int i = 0; i < groupCount; i++)
			result[i] = matcher.group(i + 1);
		return result;
	}

	public static boolean matches(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

}

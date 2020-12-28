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
package com.rapiddweller.commons.version;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.commons.Parser;
import com.rapiddweller.commons.StringUtil;

/**
 * Parses a {@link VersionNumber}.
 * Created: 10.03.2011 16:28:06
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class VersionNumberParser extends Parser<VersionNumber>{

	@Override
	public VersionNumber parseObject(String text, ParsePosition pos) {
		List<VersionNumberComponent> components = new ArrayList<>();
		List<String> delimiters = new ArrayList<>();
		if (StringUtil.isEmpty(text)) {
			components.add(new NumberVersionNumberComponent(1));
		} else {
			String delimiter;
			do {
				components.add(parseComponent(text, pos));
				delimiter = parseDelimiter(text, pos);
				if (delimiter != null)
					delimiters.add(delimiter);
			} while (delimiter != null);
		}
		return new VersionNumber(components, delimiters);
	}

	private static String parseDelimiter(String number, ParsePosition pos) {
		int index = pos.getIndex();
		if (index >= number.length())
			return null;
		char c = number.charAt(index);
		if (c == '.' || c == '-' || c == '_') {
			pos.setIndex(pos.getIndex() + 1);
			return String.valueOf(c);
		} else
			return (Character.isLetterOrDigit(c) ? "" : null);
	}

	private static VersionNumberComponent parseComponent(String number, ParsePosition pos) {
		char c = number.charAt(pos.getIndex());
		if (Character.isDigit(c))
			return parseNumberOrDateComponent(number, pos);
		else
			return new StringVersionNumberComponent(parseLetters(number, pos));
	}

	private static VersionNumberComponent parseNumberOrDateComponent(String text, ParsePosition pos) {
	    String number = parseNonNegativeInteger(text, pos);
	    if (number.length() == 8) {
	    	try {
	    		return new DateVersionNumberComponent(number);
	    	} catch (ParseException e) {
	    		// oops - no date. Fall back to NumberVersionNumberComponent in the following code
	    	}
	    }
    	return new NumberVersionNumberComponent(number);
    }

	private static String parseNonNegativeInteger(String number, ParsePosition pos) {
		int index = pos.getIndex();
		StringBuilder result = new StringBuilder(2);
		char c;
		while (index < number.length() && Character.isDigit(c = number.charAt(index))) {
			result.append(c);
			index++;
		}
		pos.setIndex(index);
		return result.toString();
	}
	
	private static String parseLetters(String number, ParsePosition pos) {
		int index = pos.getIndex();
		StringBuilder result = new StringBuilder(10);
		char c;
		while (index < number.length() && Character.isLetter(c = number.charAt(index))) {
			result.append(c);
			index++;
		}
		pos.setIndex(index);
		return result.toString();
	}

}

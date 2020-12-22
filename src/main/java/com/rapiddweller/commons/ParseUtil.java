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

import java.io.PushbackReader;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;

/**
 * Provides methods for parsing PushbackReaders and Strings.
 * Created: 20.03.2005 16:32:00
 * @author Volker Bergmann
 */
public final class ParseUtil {

    public static String parseWord(PushbackReader reader) throws IOException {
    	StringBuilder builder = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1 && Character.isAlphabetic(c))
            builder.append((char) c);
        if (c != -1)
            reader.unread(c);
        return builder.toString();
    }

    public static String parseDoubleQuotedString(PushbackReader reader) throws IOException, ParseException {
    	StringBuilder builder = new StringBuilder();
        int c;
        if ((c = reader.read()) != '"')
            throw new ParseException("Opening quote (\") expected", 0);
        while ((c = reader.read()) != -1 && c != '"')
            builder.append((char) c);
        if (c != '"')
            throw new ParseException("Closing quote (\") expected", 0);
        return builder.toString();
    }

    public static double parseDecimal(PushbackReader reader) throws IOException, ParseException {
        double result = parseInteger(reader);
        double postfix = parseOptionalPostfix(reader);
        if (result >= 0)
            result += postfix;
        else
            result -= postfix;
        return result;
    }

    public static double parseOptionalPostfix(PushbackReader reader) throws IOException {
        int c = reader.read();
        if (c != '.') {
            if (c != -1)
                reader.unread(c);
            return 0.;
        }
        double result = 0;
        double base = 0.1;
        while ((c = reader.read()) != -1 && Character.isDigit((char)c)) {
            result += (c - '0') * base;
            base *= 0.1;
        }
        if (c != -1)
            reader.unread(c);
        return result;
    }

    public static long parseInteger(PushbackReader reader) throws IOException, ParseException {
        boolean negative = parseOptionalSign(reader);
        return parseNonNegativeInteger(reader) * (negative ? -1 : 1);
    }

    public static long parseNonNegativeInteger(String source, ParsePosition pos) throws ParseException {
        int digit;
        if (pos.getIndex() > source.length() || !Character.isDigit(digit = source.charAt(pos.getIndex())))
            throw new ParseException("Number expected", 0);
        pos.setIndex(pos.getIndex() + 1);
        long result = digit - '0';
        while(pos.getIndex() < source.length() && Character.isDigit(digit = source.charAt(pos.getIndex()))) {
            result = result * 10 + digit - '0';
            pos.setIndex(pos.getIndex() + 1);
        }
        return result;
    }

    public static long parseNonNegativeInteger(PushbackReader reader) throws IOException, ParseException {
        int digit;
        if ((digit = reader.read()) == -1 || !Character.isDigit((char)digit))
            throw new ParseException("Long expected", 0);
        long result = digit - '0';
        while((digit = reader.read()) != -1 && Character.isDigit((char)digit))
            result = result * 10 + digit - '0';
        if (digit != -1)
            reader.unread(digit);
        return result;
    }

    public static boolean parseOptionalSign(PushbackReader reader) throws IOException {
        skipWhitespace(reader);
        int optionalSign = reader.read();
        if (optionalSign == '-')
            return true;
        if (optionalSign != -1)
            reader.unread(optionalSign);
        return false;
    }

    public static void skipWhitespace(PushbackReader reader) throws IOException {
        int c;
        do {
            c = reader.read();
        } while (c != -1 && Character.isWhitespace((char)c));
        if (c != -1)
            reader.unread(c);
    }
    
    public static String parseUnit(PushbackReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1 && Character.isUpperCase((char)c))
            result.append((char)c);
        if (c != -1)
            reader.unread(c);
        return (result.length() > 0 ? result.toString() : null);
    }
    
    public static boolean parseEstimated(PushbackReader reader) throws IOException {
        int c = reader.read();
        if (c == 'e')
            return true;
        if (c != -1)
            reader.unread(c);
        return false;
    }

    public static boolean isEmpty(Object object) {
        return (object == null || StringUtil.isEmpty((String) object));
    }

    public static int nextNonWhitespaceIndex(String source, int startIndex) {
        int i;
        for (i = startIndex; i < source.length() && Character.isWhitespace(source.charAt(i)); i++) {
        }
        if (i >= source.length())
            i = -1;
        return i;
    }

    public static String[][] parseEmptyLineSeparatedFile(Reader src) throws IOException {
        BufferedReader reader = null;
        List<List<String>> sections = new ArrayList<List<String>>();
        List<String> lines = null;
        try {
            reader = new BufferedReader(src);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    if (lines == null) {
                        // start a new section
                        lines = new ArrayList<String>();
                        sections.add(lines);
                    }
                    lines.add(line);
                } else {
                    // end of the section
                    if (lines != null) {
                        lines = null;
                    } else {
                        // add empty section
                        sections.add(new ArrayList<String>());
                    }
                }
            }
            return StringUtil.toArrayArray(sections);
        } finally {
            IOUtil.close(reader);
        }
    }

    public static Object[] splitNumbers(String text) {
        List<Object> parts = new ArrayList<Object>();
        Boolean numMode = null;
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean newMode = Character.isDigit(c);
            if (numMode != null && !numMode.equals(newMode)) {
                String partString = text.substring(start, i);
                addPart(partString, parts, numMode);
                start = i;
            }
            numMode = newMode;
        }
        addPart(text.substring(start), parts, numMode);
        return parts.toArray();
    }

    public static boolean isNonNegativeNumber(String text) {
        for (int i = 0; i < text.length(); i++)
            if (!Character.isDigit(text.charAt(i)))
                return false;
        return true;
    }

    public static String from(String s, String separator) {
        return from(s, separator, null);
    }

    public static String from(String s, String separator, String notFoundValue) {
        int separatorIndex = s.indexOf(separator);
        return (separatorIndex >= 0 ? s.substring(separatorIndex) : notFoundValue);
    }

    public static String before(String s, String separator) {
        int separatorIndex = s.indexOf(separator);
        return (separatorIndex >= 0 ? s.substring(0, separatorIndex) : s);
    }

    public static boolean isHex(String s) {
        for (int i = s.length() - 1; i >= 0; i--)
            if (!isHex(s.charAt(i)))
                return false;
        return true;
    }

    public static boolean isHex(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    public static String[] parseAssignment(String line, String operator, boolean lhsRequired) {
    	if (line == null)
    		return null;
        int sep = line.indexOf(operator);
        if (sep < 0 || (lhsRequired && sep == 0))
            return null;
        return new String[] {
                line.substring(0, sep).trim(),
                (sep < line.length() - 1 ? line.substring(sep + 1) : null)
        };
    }
    
    public static boolean isNMToken(String testName) {
        if (testName == null || testName.length() == 0)
            return false;
        char c = testName.charAt(0);
        if (!isNMStartChar(c))
        	return false;
        for (int i = 1; i < testName.length(); i++) {
            c = testName.charAt(i);
            if (!isNMAfterStartChar(c))
            	return false;
        }
        return true;
    }

    public static boolean isNMStartChar(char c) {
		return (Character.isLetter(c) || c == '_' || c == ':');
	}

    public static boolean isNMAfterStartChar(char c) {
		return (Character.isLetterOrDigit(c) || c == '.' || c == '-' || c == '_' || c == ':');
	}

	public static void skipWhiteSpace(String text, ParsePosition pos) {
		int i;
		while ((i = pos.getIndex()) < text.length() && Character.isWhitespace(text.charAt(i)))
			pos.setIndex(i + 1);
	}

    public static Boolean parseBoolean(String s) {
    	return parseBoolean(s, false);
    }

    public static Boolean parseBoolean(String s, boolean acceptWhitespace) {
    	if (s == null)
    		return null;
    	if (acceptWhitespace)
    		s = s.trim();
    	if ("true".equalsIgnoreCase(s))
    		return true;
    	else if ("false".equalsIgnoreCase(s))
    		return false;
    	else
    		throw new SyntaxError("Not a boolean value", s);
    }

	// private helpers -------------------------------------------------------------------------------------------------

    private static void addPart(String partString, List<Object> parts, Boolean numMode) {
        Object part = (numMode ? new BigInteger(partString) : partString);
        parts.add(part);
    }

}

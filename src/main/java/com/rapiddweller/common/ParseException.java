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

/**
 * Indicates a parsing error.
 * Created at 30.12.2008 08:23:05
 * @since 0.5.7
 * @author Volker Bergmann
 */

public class ParseException extends RuntimeException {
	
	private static final long serialVersionUID = -3893735778927506664L;
	
	private final String parsedText;
	private final int line;
	private final int column;
	
	
	
	// constructors ----------------------------------------------------------------------------------------------------

	public ParseException(String message, String parsedText) {
		this(message, parsedText, -1, -1);
	}

	public ParseException(String message, String parsedText, int line, int column) {
		this(message, null, parsedText, line, column);
	}
	
	public ParseException(String message, Throwable cause, String parsedText, int line, int column) {
		super(message, cause);
		this.parsedText = parsedText;
		this.line = line;
		this.column = column;
	}
	
	
	
	// properties ------------------------------------------------------------------------------------------------------

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public String getParsedText() {
    	return parsedText;
    }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getMessage());
		if (line >= 0 && column >= 0)
			builder.append(" at line ").append(line).append(", column ").append(column);
		if (parsedText != null)
			builder.append(" in ").append(parsedText);
	    return builder.toString();
	}
	
}

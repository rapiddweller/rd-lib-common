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
 * Supports iterating the characters of a String.
 * This is especially useful for writing parsers that iterate over Strings,
 * since it encapsulates the cursor index.
 * Created: 18.08.2006 19:21:45
 * @author Volker Bergmann
 */
public class StringCharacterIterator implements CharacterIterator {

    /** The String to iterate */
    private String source;

    /** The cursor offset */
    private int offset;

    private int line;
    
    private int column;
    
    private int lastLineLength;
    
    // constructors ----------------------------------------------------------------------------------------------------

    /** Creates an iterator that starts at the String's beginning 
     * @param source the text to iterate */
    public StringCharacterIterator(String source) {
        this(source, 0);
    }

    /** Creates an iterator that starts at a specified position 
     * @param source the text to iterate
     * @param offset the offset at witch to begin iteration */
    public StringCharacterIterator(String source, int offset) {
        if (source == null)
            throw new IllegalArgumentException("source string must not be null");
        this.source = source;
        this.offset = offset;
        this.line = 1;
        this.column = 1;
        this.lastLineLength = 1;
    }

    // java.util.Iterator interface ------------------------------------------------------------------------------------

    /**
     * Tells if the iterator has not reached the String's end.
     * java.util.Iterator#hasNext()
     * @return true if there are more characters available, false, if the end was reached.
     */
    @Override
	public boolean hasNext() {
        return offset < source.length();
    }

    /**
     * @see java.util.Iterator#next()
     * @return the next character.
     */
    @Override
	public char next() {
        if (offset >= source.length())
            throw new IllegalStateException("Reached the end of the string");
        if (source.charAt(offset) == '\n') {
        	lastLineLength = column;
        	line++;
        	column = 1;
        } else
        	column++;
        return source.charAt(offset++);
    }

    /**
     * Implements the remove() operation of the Iterator interface,
     * raising an UnsupportedOperationException.
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    // Convenience interface -------------------------------------------------------------------------------------------
    
	public char peekNext() {
		if (!hasNext())
			return 0;
        return source.charAt(offset);
	}

    /** Pushes back the cursor by one character. */
    public void pushBack() {
        if (offset > 0) {
            if (offset - 1 < source.length() && source.charAt(offset - 1) == '\n') {
            	line--;
            	column = lastLineLength;
            } else
                column--;
            offset--;
        } else
            throw new IllegalStateException("cannot pushBack before start of string: " + source);
    }

    /** @return the cursor offset. */
    public int getOffset() {
        return offset;
    }
    
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
    public void skipWhitespace() {
        while (offset < source.length() && Character.isWhitespace(source.charAt(offset)))
            offset++;
    }

    public String parseLetters() {
        StringBuilder builder = new StringBuilder();
        while (offset < source.length() && Character.isLetter(source.charAt(offset)))
            builder.append(source.charAt(offset++));
        return builder.toString();
    }

    public String remainingText() {
        return source.substring(offset);
    }

	public void assertNext(char c) {
		if (!hasNext())
			throw new ParseException("Expected '" + c + "', but no more character is available", source, line, column);
		char next = next();
		if (next != c)
			throw new ParseException("Expected '" + c + "', but found '" + next + "'", source, line, column);
	}
	
	public int line() {
		return line;
	}
	
	public int column() {
		return column;
	}

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    /**
     * @return the String that is iterated.
     */
    @Override
	public String toString() {
        return source;
    }

}

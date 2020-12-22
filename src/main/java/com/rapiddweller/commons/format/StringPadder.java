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
package com.rapiddweller.commons.format;


import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.converter.ThreadSafeConverter;

/**
 * Pads a string with a pad character to a given length with left/center or right alignment.
 * Created: 13.03.2014 11:40:07
 * @since 0.5.28
 * @author Volker Bergmann
 */

public class StringPadder extends ThreadSafeConverter<String, String> {

	private int length;
    private Alignment alignment;
    private char padChar;

    public StringPadder(int length, Alignment alignment, char padChar) {
    	super(String.class, String.class);
    	
    	// check preconditions
    	if (length < 1)
    		throw new IllegalArgumentException("Not a supported padding length: " + length);
    	Assert.notNull(alignment, "alignment");
    	if (padChar == 0)
    		throw new IllegalArgumentException("padChar must not be null");
    	
    	// initialize attributes
        this.length = length;
        this.alignment = alignment;
        this.padChar = padChar;
    }
    
	
    // properties ------------------------------------------------------------------------------------------------------
	
    public int getLength() {
        return length;
    }
    
    public Alignment getAlignment() {
        return alignment;
    }
    
    public char getPadChar() {
        return padChar;
    }
    
    
    // Converter implementation ----------------------------------------------------------------------------------------
    
	@Override
	public String convert(String text) throws ConversionException {
        int padLength = length - text.length();
        if (padLength < 0)
        	throw new IllegalArgumentException("Text is longer that the pad length of " + length + " characters: '" + text + "'");
        switch (alignment) {
            case LEFT   : return text + StringUtil.padString(padChar, padLength);
            case RIGHT  : boolean neg = (padChar == '0' && text.length() > 0 && text.charAt(0) == '-');
                          if (neg)
                              return "-" + StringUtil.padString('0', padLength) + text.substring(1);
                          else
                                return StringUtil.padString(padChar, padLength) + text;
            case CENTER : return StringUtil.padString(padChar, padLength / 2) + text + StringUtil.padString(padChar, padLength - padLength / 2);
            default     : throw new IllegalArgumentException("Not a supported Alignement: " + alignment);
        }
    }
	
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
    
	@Override
	public int hashCode() {
		return ((alignment.hashCode() * 31) +  length) * 31 + padChar;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final StringPadder that = (StringPadder) obj;
		return (this.alignment.equals(that.alignment) 
				&& this.length == that.length 
				&& padChar == that.padChar);
	}
	
	@Override
	public String toString() {
		return "" + length + alignment.getId() + padChar;
	}
	
}

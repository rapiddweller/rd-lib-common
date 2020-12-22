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

import com.rapiddweller.commons.NullSafeComparator;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.converter.ToStringConverter;

import java.text.*;

/**
 * {@link Format} implementation that applies padding for formatting Strings with a fixed width.
 * Created: 07.06.2007 13:23:37
 * @author Volker Bergmann
 */
public class PadFormat extends Format {

    private static final long serialVersionUID = -8263536650454913565L;
    
    private String nullString;
	private Format format;
    private StringPadder padder;

    public PadFormat(String nullString, int length, Alignment alignment, char padChar) {
        this(null, nullString, length, alignment, padChar);
    }

    public PadFormat(Format format, String nullString, int length, Alignment alignment, char padChar) {
    	assert alignment != null;
    	assert padChar != 0;
        this.format = format;
        this.nullString = nullString;
        this.padder = (length > 0 ? new StringPadder(length, alignment, padChar) : null);
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public int getLength() {
        return padder.getLength();
    }

    public Alignment getAlignment() {
        return padder.getAlignment();
    }

    public char getPadChar() {
        return padder.getPadChar();
    }
    
	
	// Format interface implementation ---------------------------------------------------------------------------------
	
    @Override
    public StringBuffer format(Object object, StringBuffer toAppendTo, FieldPosition pos) {
    	String text;
    	if (object == null)
    		text = nullString;
    	else if (format != null)
    		text = format.format(object);
    	else
    		text = ToStringConverter.convert(object, nullString);
        if (padder != null)
        	text = padder.convert(text);
        return toAppendTo.append(text);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        if (source == null) {
            pos.setIndex(1);
            return null;
        }
        String tmp = source.substring(pos.getIndex());
        char padChar = getPadChar();
        switch (getAlignment()) {
            case LEFT   : tmp =  StringUtil.trimRight(tmp, padChar); break;
            case RIGHT  : boolean neg = (padChar == '0' && tmp.length() > 0 && tmp.charAt(0) == '-');
                          if (neg)
                        	  tmp =  '-' + StringUtil.trimLeft(tmp.substring(1), padChar);
                          else
                        	  tmp = StringUtil.trimLeft(tmp, padChar);
                          break;
            case CENTER : tmp = StringUtil.trim(tmp, padChar); break;
            default     : throw new IllegalArgumentException("Illegal Alignement: " + getAlignment());
        }
        Object result;
        if (format != null) {
        	result = format.parseObject(tmp, pos);
        } else {
        	result = tmp;
            if (StringUtil.isEmpty(source))
                pos.setIndex(1);
            else
            	pos.setIndex(source.length());
        }
        return result;
    }
    
    
	// java.lang.Object overrides --------------------------------------------------------------------------------------
    
	@Override
	public int hashCode() {
		return padder.hashCode() * 31 +  format.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final PadFormat that = (PadFormat) obj;
		return (NullSafeComparator.equals(this.padder, that.padder) && 
				NullSafeComparator.equals(this.format, that.format));
	}
	
	@Override
	public String toString() {
		return padder.toString();
	}
	
}

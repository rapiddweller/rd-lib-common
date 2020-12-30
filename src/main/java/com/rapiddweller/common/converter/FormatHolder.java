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
package com.rapiddweller.common.converter;

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.Patterns;

/**
 * Holds format strings for date and number objects.
 * Created at 01.10.2009 12:18:59
 * @since 0.5.0
 * @author Volker Bergmann
 */

public abstract class FormatHolder implements Patterns { 

    // attributes ------------------------------------------------------------------------------------------------------

	/** The string used to represent null values */
    protected String nullString;
    
    protected String datePattern;

    protected Capitalization dateCapitalization;

    protected String dateTimePattern;

    protected Capitalization dateTimeCapitalization;

    protected String timePattern;

    protected String timestampPattern;
    
    protected Capitalization timestampCapitalization;

    protected NumberFormatter decimalConverter;
    
    protected NumberFormatter integralConverter;
    
    protected String charQuote;
    
    protected String stringQuote;
    
    // constructors ----------------------------------------------------------------------------------------------------

    /** Default constructor that uses an isEmpty String as null representation */
    public FormatHolder() {
        this(DEFAULT_NULL_STRING);
    }

    /**
     * Constructor that initializes the null replacement to the specified parameter.
     * @param nullString the String to use for replacing null values.
     */
    public FormatHolder(String nullString) {
        this(nullString, DEFAULT_DATE_PATTERN, DEFAULT_TIMESTAMP_PATTERN);
    }

    public FormatHolder(String nullString, String datePattern, String timestampPattern) {
        this.nullString = nullString;
        this.datePattern = datePattern;
        this.dateCapitalization = Capitalization.mixed;
        this.timestampPattern = timestampPattern;
        this.timestampCapitalization = Capitalization.mixed;
        this.timePattern = DEFAULT_TIME_PATTERN;
        this.dateTimePattern = DEFAULT_DATETIME_PATTERN;
        this.dateTimeCapitalization = Capitalization.mixed;
        this.integralConverter = null;
        this.decimalConverter = null;
        this.stringQuote = null;
        this.charQuote = null;
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public String getNullString() {
        return nullString;
    }

    public void setNullString(String nullResult) {
        this.nullString = nullResult;
    }
    
    public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String pattern) {
		this.datePattern = pattern;
	}

	public Capitalization getDateCapitalization() {
		return dateCapitalization;
	}
	
	public void setDateCapitalization(Capitalization dateCapitalization) {
		this.dateCapitalization = dateCapitalization;
	}
	
	public String getDateTimePattern() {
		return dateTimePattern;
	}

	public void setDateTimePattern(String pattern) {
		this.dateTimePattern = pattern;
	}

	public Capitalization getDateTimeCapitalization() {
		return dateTimeCapitalization;
	}

	public void setDateTimeCapitalization(Capitalization dateTimeCapitalization) {
		this.dateTimeCapitalization = dateTimeCapitalization;
	}
	
	public String getTimePattern() {
		return timePattern;
	}
	
    public void setTimePattern(String timePattern) {
	    this.timePattern = timePattern;
    }

	public String getTimestampPattern() {
		return timestampPattern;
	}

	public void setTimestampPattern(String pattern) {
		this.timestampPattern = pattern;
	}

	public Capitalization getTimestampCapitalization() {
		return timestampCapitalization;
	}
	
	public void setTimestampCapitalization(
			Capitalization timestampCapitalization) {
		this.timestampCapitalization = timestampCapitalization;
	}
	
	public String getDecimalPattern() {
		return decimalConverter.getPattern();
	}

	public void setDecimalPattern(String pattern) {
		if (decimalConverter == null)
			decimalConverter = new NumberFormatter(pattern);
		decimalConverter.setPattern(pattern);
	}

	public char getGroupingSeparator() {
	    return decimalConverter.getDecimalSeparator();
    }

	public void setGroupingSeparator(char groupingSeparator) {
		if (decimalConverter == null)
			decimalConverter = new NumberFormatter();
		decimalConverter.setGroupingSeparator(groupingSeparator);
    }

	public char getDecimalSeparator() {
    	return decimalConverter.getDecimalSeparator();
    }

	public void setDecimalSeparator(char separator) {
		if (decimalConverter == null)
			decimalConverter = new NumberFormatter();
		decimalConverter.setDecimalSeparator(separator);
    }

    public String getIntegralPattern() {
    	return integralConverter.getPattern();
    }

    public void setIntegralPattern(String pattern) {
    	if (integralConverter == null)
    		integralConverter = new NumberFormatter();
    	integralConverter.setPattern(pattern);
    }
    
	public String getCharQuote() {
    	return charQuote;
    }

	public void setCharQuote(String charQuote) {
    	this.charQuote = charQuote;
    }

	public String getStringQuote() {
    	return stringQuote;
    }

	public void setStringQuote(String stringQuote) {
    	this.stringQuote = stringQuote;
    }

	@Override
    public Object clone() {
		try {
	        FormatHolder copy = (FormatHolder) super.clone();
	        copy.decimalConverter = (NumberFormatter) decimalConverter.clone();
	        copy.integralConverter = (NumberFormatter) integralConverter.clone();
			return copy;
        } catch (CloneNotSupportedException e) {
        	throw new RuntimeException(e);
        }
	}

}

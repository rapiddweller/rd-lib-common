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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.NullSafeComparator;

/**
 * Holds a {@link NumberFormat} and exhibits properties for its configuration.
 * Created: 26.02.2010 08:37:23
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public abstract class NumberFormatBasedConverter<S, T> extends AbstractConverter<S, T> implements Cloneable {
	
	// constants -------------------------------------------------------------------------------------------------------
	
	protected static final String DEFAULT_DECIMAL_PATTERN = "0.#";
	protected static final char DEFAULT_DECIMAL_SEPARATOR = '.';
	protected static final char DEFAULT_GROUPING_SEPARATOR = ',';
	protected static final String DEFAULT_NULL_STRING = "";
	
	// attributes ------------------------------------------------------------------------------------------------------

	private String pattern;
	private char decimalSeparator;
	private char groupingSeparator;
	protected DecimalFormat format;

	/** The string used to represent null values */
    private String nullString;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public NumberFormatBasedConverter(Class<S> sourceType, Class<T> targetType) {
		this(sourceType, targetType, DEFAULT_DECIMAL_PATTERN);
	}

	public NumberFormatBasedConverter(Class<S> sourceType, Class<T> targetType, String pattern) {
		super(sourceType, targetType);
		setPattern(pattern);
		setDecimalSeparator(DEFAULT_DECIMAL_SEPARATOR);
		setGroupingSeparator(DEFAULT_GROUPING_SEPARATOR);
		setNullString(DEFAULT_NULL_STRING);
	}
	
	// properties ------------------------------------------------------------------------------------------------------

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		this.format = new DecimalFormat(pattern);
		setDecimalSeparator(decimalSeparator);
	}

	public char getDecimalSeparator() {
    	return decimalSeparator;
    }

	public void setGroupingSeparator(char groupingSeparator) {
    	this.groupingSeparator = groupingSeparator;
    	updateFormat();
    }

	public char getGroupingSeparator() {
    	return decimalSeparator;
    }

	public void setDecimalSeparator(char decimalSeparator) {
    	this.decimalSeparator = decimalSeparator;
		updateFormat();
    }

	private void updateFormat() {
	    DecimalFormatSymbols newSymbols = new DecimalFormatSymbols();
		if (groupingSeparator != 0)
			newSymbols.setGroupingSeparator(groupingSeparator);
		newSymbols.setDecimalSeparator(this.decimalSeparator);
		format.setDecimalFormatSymbols(newSymbols);
    }

	public String getNullString() {
    	return nullString;
    }

	public void setNullString(String nullString) {
    	this.nullString = nullString;
    }
	
	protected String format(Number input) {
		return (input != null ? format.format(input) : nullString);
	}

	protected Number parse(String input) throws ConversionException {
		if (input == null || NullSafeComparator.equals(input, nullString))
			return null;
		try {
            return format.parse(input);
		} catch (ParseException e) {
			throw new ConversionException("Error parsing " + input + " as number");
		}
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
	    return getClass().getSimpleName() + '[' + pattern + ']';
	}
	
	@Override
	public boolean isThreadSafe() {
	    return false;
	}
	
	@Override
	public boolean isParallelizable() {
	    return true;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public Object clone() {
	    try {
	        NumberFormatBasedConverter<S, T> copy = (NumberFormatBasedConverter<S, T>) super.clone();
	        copy.format = (DecimalFormat) format.clone();
	        return copy;
        } catch (CloneNotSupportedException e) {
        	throw new RuntimeException(e);
        }
	}
	
}

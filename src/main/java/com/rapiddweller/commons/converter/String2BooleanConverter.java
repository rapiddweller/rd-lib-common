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
package com.rapiddweller.commons.converter;

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.StringUtil;

/**
 * Parses a {@link String} as a {@link Boolean}.
 * Created: 27.02.2010 11:44:57
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class String2BooleanConverter extends ThreadSafeConverter<String, Boolean> {
	
	private static final String DEFAULT_FALSE_STRING = "false";
	private static final String DEFAULT_TRUE_STRING = "true";
	private static final boolean DEFAULT_CASE_SENSITIVE = false;
	
	private final String trueString;
	private final String falseString;
	private final boolean caseSensitive;

	public String2BooleanConverter() {
	    this(DEFAULT_TRUE_STRING, DEFAULT_FALSE_STRING, DEFAULT_CASE_SENSITIVE);
    }

	public String2BooleanConverter(String trueString, String falseString, boolean caseSensitive) {
	    super(String.class, Boolean.class);
	    this.falseString = falseString;
		this.trueString = trueString;
		this.caseSensitive = caseSensitive;
    }

	@Override
	public Boolean convert(String sourceValue) throws ConversionException {
	    if (StringUtil.isEmpty(sourceValue))
	    	return null;
	    sourceValue = sourceValue.trim();
	    if (caseSensitive) {
		    if (trueString.equals(sourceValue))
		    	return true;
		    else if (falseString.equals(sourceValue))
		    	return false;
	    } else {
		    if (trueString.equalsIgnoreCase(sourceValue))
		    	return true;
		    else if (falseString.equalsIgnoreCase(sourceValue))
		    	return false;
	    }
	    if (StringUtil.isEmpty(sourceValue))
	    	return null;
    	throw new IllegalArgumentException("Not a boolean value: " + sourceValue);
	}
	
}

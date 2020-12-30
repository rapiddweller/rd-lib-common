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

import java.util.Formatter;
import java.util.Locale;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;

/**
 * {@link Converter} implementation that uses a {@link Formatter} 
 * to render argument objects in C-like printf format.
 * Created at 20.07.2009 07:18:43
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class PrintfConverter extends ThreadSafeConverter<Object, String> {
	
	private Locale locale;
	private String pattern;
	
	// constructors ----------------------------------------------------------------------------------------------------

    public PrintfConverter() {
	    this("");
    }
    
    public PrintfConverter(String pattern) {
	    this(pattern, Locale.getDefault());
    }
    
    public PrintfConverter(String pattern, Locale locale) {
	    super(Object.class, String.class);
	    this.pattern = pattern;
	    this.locale = locale;
    }
    
    // properties ------------------------------------------------------------------------------------------------------
    
    public Locale getLocale() {
    	return locale;
    }

	public void setLocale(Locale locale) {
    	this.locale = locale;
    }

	public String getPattern() {
    	return pattern;
    }

	public void setPattern(String pattern) {
    	this.pattern = pattern;
    }

	// converter interface ---------------------------------------------------------------------------------------------

    @Override
	public String convert(Object sourceValue) throws ConversionException {
	    if (sourceValue == null)
	    	return null;
	    Formatter formatter = new Formatter(locale);
	    try {
	    	return formatter.format(pattern, sourceValue).out().toString();
	    } finally {
	    	IOUtil.close(formatter);
	    }
    }

}

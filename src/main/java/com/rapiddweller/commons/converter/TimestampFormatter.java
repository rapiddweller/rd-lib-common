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

import java.sql.Timestamp;
import java.text.DateFormat;

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.Patterns;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.format.ConcurrentDateFormat;
import com.rapiddweller.commons.format.ConcurrentDecimalFormat;

/**
 * Formats a {@link Timestamp} as {@link String}.
 * Created: 18.02.2010 17:46:14
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class TimestampFormatter extends ThreadSafeConverter<Timestamp, String> {
	
	private final DateFormat prefixFormat;
	private final ConcurrentDecimalFormat postfixFormat;
	private long nanoDivisor;
	
	// constructors ----------------------------------------------------------------------------------------------------
	
	public TimestampFormatter() {
	    this(Patterns.DEFAULT_TIMESTAMP_PATTERN);
    }

	public TimestampFormatter(String pattern) {
		super(Timestamp.class, String.class);
		
		// calculate the number of postfix digits
		int lastPos = pattern.length() - 1;
		int sepPos = lastPos;
		while (pattern.charAt(sepPos) == 'S')
			sepPos--;
		
		// define prefix and postfix patterns
		String prefixPattern;
		prefixPattern = (sepPos < lastPos ? pattern.substring(0, sepPos) : pattern);
	    this.prefixFormat = new ConcurrentDateFormat(prefixPattern);
		int postfixDigits = lastPos - sepPos;
		if (postfixDigits > 0) {
			String postfixPattern = new String(StringUtil.fill(new char[postfixDigits], 0, postfixDigits, '0'));
		    this.postfixFormat = new ConcurrentDecimalFormat(postfixPattern);
		    this.nanoDivisor = (long) Math.pow(10, Math.round(9. - postfixDigits));
		} else
			this.postfixFormat = null;
    }
	
	// Converter interface implementation ------------------------------------------------------------------------------

	@Override
	public String convert(Timestamp sourceValue) throws ConversionException {
	    return format(sourceValue);
    }

	// static convenience method ---------------------------------------------------------------------------------------
	
	public String format(Timestamp timestamp) {
		if (timestamp == null)
			return null;
		String result = prefixFormat.format(timestamp);
		if (postfixFormat != null) {
			long postfixDigits = timestamp.getNanos() / nanoDivisor;
			result += '.' + postfixFormat.format(postfixDigits);
		}
		return result;
	}

}

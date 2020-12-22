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
import com.rapiddweller.commons.Patterns;
import com.rapiddweller.commons.StringUtil;

import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Converts Strings of standard date(time) format (yyyy-MM-dd[Thh:mm[:ss[.SSS]]]) to dates.
 * Created: 07.09.2007 09:07:12
 * @param <E> the {@link Date} type to convert to
 * @author Volker Bergmann
 */
public class String2DateConverter<E extends Date> extends ThreadSafeConverter<String, E> implements Patterns {
	
    // TODO support time zones (like 'Z', '+01:00' or '-01:30')

	private String pattern;
	private Locale locale;

    public String2DateConverter() {
        this(null);
    }

    public String2DateConverter(String pattern) {
        this(pattern, Locale.getDefault());
    }

    @SuppressWarnings("unchecked")
	public String2DateConverter(String pattern, Locale locale) {
        this(pattern, locale, (Class<E>) java.util.Date.class);
    }

    public String2DateConverter(String pattern, Locale locale, Class<E> targetType) {
        super(String.class, targetType);
        this.pattern = pattern;
        this.locale = locale;
    }

    @Override
	@SuppressWarnings("unchecked")
    public E convert(String sourceValue) {
        return (E) convert(sourceValue, pattern, locale, targetType);
    }

    public static <T extends Date> Date convert(String sourceValue, String pattern, Locale locale, Class<T> targetType) {
    	sourceValue = StringUtil.trimmedEmptyToNull(sourceValue);
    	if (sourceValue == null)
    		return null;
        try {
            DateFormat format;
            sourceValue = sourceValue.replace(' ', 'T');
            if (pattern != null) {
            	format = new SimpleDateFormat(pattern, locale);
            } else if (sourceValue.indexOf('T') > 0) {
                switch (sourceValue.length()) {
                    case 16 : format = new SimpleDateFormat(DEFAULT_DATETIME_MINUTES_PATTERN); break;
                    case 19 : format = new SimpleDateFormat(DEFAULT_DATETIME_SECONDS_PATTERN); break;
                    case 23 : format = new SimpleDateFormat(DEFAULT_DATETIME_MILLIS_PATTERN); break;
                    default : throw new IllegalArgumentException("Not a supported date format: " + sourceValue);
                }
            } else {
                format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
            }
            java.util.Date simpleDate = format.parse(sourceValue);
            if (targetType == java.util.Date.class)
                return simpleDate;
            else if (targetType == java.sql.Date.class)
                return new java.sql.Date(simpleDate.getTime());
            else if (targetType == java.sql.Timestamp.class)
                return new java.sql.Timestamp(simpleDate.getTime());
            else
                throw new UnsupportedOperationException("Not a supported target type: " + targetType);
        } catch (ParseException e) {
            throw new ConversionException(e);
        }
    }
    
}

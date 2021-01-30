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

import com.rapiddweller.common.Patterns;
import com.rapiddweller.common.StringUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Parses Strings into {@link ZonedDateTime} objects.<br><br>
 * Created: 03.03.2019 11:09:37
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class String2ZonedDateTimeConverter extends ThreadSafeConverter<String, ZonedDateTime> implements Patterns {
	
	private final DateTimeFormatter formatter;

	public String2ZonedDateTimeConverter(String pattern, ZoneId zoneId) {
        this(DateTimeFormatter.ofPattern(pattern).withZone(zoneId));
    }

	public String2ZonedDateTimeConverter(DateTimeFormatter formatter) {
        super(String.class, ZonedDateTime.class);
        this.formatter = formatter;
    }

    @Override
    public ZonedDateTime convert(String sourceValue) {
    	sourceValue = StringUtil.trimmedEmptyToNull(sourceValue);
    	if (sourceValue == null)
    		return null;
    	return ZonedDateTime.parse(sourceValue, formatter);
    }
    
}

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

import java.util.Date;
import java.util.TimeZone;

import com.rapiddweller.commons.ConversionException;

/**
 * Interprets {@link Long} values as milliseconds since 1970-01-01 and 
 * converts them to {@link Date} objects.
 * Created: 26.02.2010 08:19:48
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Long2DateConverter extends ThreadSafeConverter<Long, Date>{

	private TimeZone timeZone;
	
	// constructors ----------------------------------------------------------------------------------------------------

	public Long2DateConverter() {
		this(TimeZone.getDefault());
	}

	public Long2DateConverter(TimeZone timeZone) {
		super(Long.class, Date.class);
		this.timeZone = timeZone;
	}
	
	// properties ------------------------------------------------------------------------------------------------------

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	
	// BidirectionalConverter interface implementation -----------------------------------------------------------------

	@Override
	public Class<Date> getTargetType() {
		return Date.class;
	}

	@Override
	public Date convert(Long target) throws ConversionException {
		if (target == null)
			return null;
		return new Date(target - timeZone.getRawOffset());
	}

}

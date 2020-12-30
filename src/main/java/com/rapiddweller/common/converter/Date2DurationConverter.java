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

import java.util.Date;
import java.util.TimeZone;

import com.rapiddweller.common.ConversionException;

/**
 * Interprets a Date as duration specification, e.g. '0000-00-00T00:00:00.001' as one millisecond, 
 * '0001-00-00T00:00:00.000' as one year. Dates after 1970-01-01 will be interpreted relative to that date.
 * Created at 11.01.2009 06:39:28
 * @since 0.5.7
 * @author Volker Bergmann
 */

public class Date2DurationConverter extends ThreadSafeConverter<Date, Long> {

	public Date2DurationConverter() {
		super(Date.class, Long.class);
	}

	@Override
	public Long convert(Date sourceValue) throws ConversionException {
		if (sourceValue == null)
			return null;
		long source = sourceValue.getTime();
		// for time zone problems, see http://mail-archives.apache.org/mod_mbox/struts-user/200502.mbox/%3C42158AA9.3050001@gridnode.com%3E 
		Long result = source + TimeZone.getDefault().getOffset(0L); // That's relative to 1970-01-01
		if (result < 0) // if it's before 1970-01-01, interpret it relative to 0001-01-01
			result = source + TimeZone.getDefault().getOffset(-62170156800000L) + 62170156800000L;
		return result;
	}

}

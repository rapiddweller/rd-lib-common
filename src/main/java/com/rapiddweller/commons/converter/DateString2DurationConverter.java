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

/**
 * Converts a date in String format to a duration in milliseconds. This takes the dates 1970-01-01 or 0000-00-00 as base.
 * Created at 05.01.2009 18:37:23
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class DateString2DurationConverter extends ConverterChain<String, Long> {
	
	private static DateString2DurationConverter defaultInstance = new DateString2DurationConverter();
	
	public static DateString2DurationConverter defaultInstance() {
		return defaultInstance;
	}
	
	public DateString2DurationConverter() {
		super(
			new String2DateConverter<Date>(),
			new Date2DurationConverter()
		);
	}

}

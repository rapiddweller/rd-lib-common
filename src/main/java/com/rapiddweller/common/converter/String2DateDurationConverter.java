/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.time.DateDuration;

/**
 * Parses a {@link String} as {@link com.rapiddweller.common.time.DateDuration}.<br/><br/>
 * Created: 28.07.2022 12:07:24
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class String2DateDurationConverter extends ThreadSafeConverter<String, DateDuration>{

	@SuppressWarnings("unused")
	public String2DateDurationConverter() {
		super(String.class, DateDuration.class);
	}

	@Override
	public DateDuration convert(String sourceValue) {
		return DateDuration.of(sourceValue);
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Converts a {@link java.time.ZonedDateTime} to a {@link java.util.Date}.<br/><br/>
 * Created: 28.07.2022 12:35:30
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2DateConverter extends ThreadSafeConverter<ZonedDateTime, Date> {

	public ZonedDateTime2DateConverter() {
		super(ZonedDateTime.class, Date.class);
	}

	@Override
	public Date convert(ZonedDateTime sourceValue) {
		return JavaTimeUtil.toDate(sourceValue);
	}

}

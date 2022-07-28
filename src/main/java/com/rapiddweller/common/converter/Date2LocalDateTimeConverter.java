/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Converts a {@link java.util.Date} to a {@link java.time.LocalDateTime}.<br/><br/>
 * Created: 28.07.2022 13:25:54
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2LocalDateTimeConverter extends ThreadSafeConverter<Date, LocalDateTime> {

	public Date2LocalDateTimeConverter() {
		super(Date.class, LocalDateTime.class);
	}

	@Override
	public LocalDateTime convert(Date sourceValue) {
		return JavaTimeUtil.toLocalDateTime(sourceValue);
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Converts a {@link java.time.LocalDateTime} to a {@link java.util.Date}.<br/><br/>
 * Created: 28.07.2022 13:07:15
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2DateConverter extends ThreadSafeConverter<LocalDateTime, Date> {

	public LocalDateTime2DateConverter() {
		super(LocalDateTime.class, Date.class);
	}

	@Override
	public Date convert(LocalDateTime sourceValue) {
		return JavaTimeUtil.toDate(sourceValue);
	}

}

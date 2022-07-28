/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.time.LocalDate;
import java.util.Date;

/**
 * Converts a {@link java.util.Date} to a {@link java.time.LocalDate}.<br/><br/>
 * Created: 28.07.2022 12:59:31
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DateToLocalDateConverter extends ThreadSafeConverter<Date, LocalDate> {

	public DateToLocalDateConverter() {
		super(Date.class, LocalDate.class);
	}

	@Override
	public LocalDate convert(Date sourceValue) {
		return JavaTimeUtil.toLocalDate(sourceValue);
	}

}

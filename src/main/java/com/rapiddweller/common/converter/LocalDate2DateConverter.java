/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.time.LocalDate;
import java.util.Date;

/**
 * Converts {@link LocalDate} to {@link Date}.<br/><br/>
 * Created: 07.08.2022 21:48:52
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDate2DateConverter extends ThreadSafeConverter<LocalDate, Date> {

	public LocalDate2DateConverter() {
		super(LocalDate.class, Date.class);
	}

	@Override
	public Date convert(LocalDate sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return JavaTimeUtil.toDate(sourceValue);
		}
	}

}

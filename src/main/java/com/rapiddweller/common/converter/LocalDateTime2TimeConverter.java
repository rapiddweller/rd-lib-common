/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Converts {@link java.time.LocalDateTime} to {@link java.sql.Time}.<br/><br/>
 * Created: 07.08.2022 22:04:57
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2TimeConverter extends ThreadSafeConverter<LocalDateTime, Time> {

	public LocalDateTime2TimeConverter() {
		super(LocalDateTime.class, Time.class);
	}

	@Override
	public Time convert(LocalDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalTime localTime = sourceValue.toLocalTime();
			return JavaTimeUtil.toTime(localTime);
		}
	}

}

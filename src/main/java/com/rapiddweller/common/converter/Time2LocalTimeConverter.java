/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Converts a {@link java.sql.Time} to a {@link java.time.LocalTime}.<br/><br/>
 * Created: 28.07.2022 13:04:46
 * @author Volker Bergmann
 * @since 3.0.0
 */
public class Time2LocalTimeConverter extends ThreadSafeConverter<Time, LocalTime> {

	public Time2LocalTimeConverter() {
		super(Time.class, LocalTime.class);
	}

	@Override
	public LocalTime convert(Time sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalTime tmp = sourceValue.toLocalTime(); // this drops millis of second
			long millis = sourceValue.getTime() % 1000;
			return tmp.plusNanos(millis * 1000000); // add millis of second
		}
	}

}

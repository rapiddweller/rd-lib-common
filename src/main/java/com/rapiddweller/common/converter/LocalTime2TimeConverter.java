/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Converts a {@link java.time.LocalTime} to a {@link java.sql.Time}.<br/><br/>
 * Created: 28.07.2022 13:01:36
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalTime2TimeConverter extends ThreadSafeConverter<LocalTime, Time> {

	public LocalTime2TimeConverter() {
		super(LocalTime.class, Time.class);
	}

	@Override
	public Time convert(LocalTime sourceValue) {
		Time tmp = Time.valueOf(sourceValue); // this ignores milliseconds
		return new Time(tmp.getTime() + ((sourceValue.toNanoOfDay() / 1000000) % 1000)); // add milliseconds
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

/**
 * Converts {@link java.sql.Timestamp} to {@link java.sql.Time}.<br/><br/>
 * Created: 07.08.2022 22:00:37
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2TimeConverter extends ThreadSafeConverter<Timestamp, Time> {

	public Timestamp2TimeConverter() {
		super(Timestamp.class, Time.class);
	}

	@Override
	public Time convert(Timestamp sourceValue) throws ConversionException {
		if (sourceValue == null) {
			return null;
		} else {
			LocalTime localTime = sourceValue.toLocalDateTime().toLocalTime();
			Time timeWithSecondResolution = Time.valueOf(localTime);
			return new Time(timeWithSecondResolution.getTime() + localTime.getNano() / 1000000);
		}
	}

}

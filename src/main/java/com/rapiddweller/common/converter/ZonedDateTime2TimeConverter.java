/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts {@link ZonedDateTime} to {@link java.sql.Time}.<br/><br/>
 * Created: 07.08.2022 22:12:58
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2TimeConverter extends AbstractZonedDateTimeConverter<Time> {

	public ZonedDateTime2TimeConverter() {
		this(null);
	}

	public ZonedDateTime2TimeConverter(ZoneId zone) {
		super(Time.class, zone);
	}

	@Override
	public Time convert(ZonedDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return toTime(localDateTimeAtTargetZone(sourceValue));
		}
	}

}

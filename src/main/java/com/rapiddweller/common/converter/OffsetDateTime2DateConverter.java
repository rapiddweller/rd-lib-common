/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Converts {@link java.time.OffsetDateTime} to a {@link java.util.Date} that represents the datetime
 * in the configured time {@link #zone}. If no zone was specified, the datetime values of the argument
 * are taken 'as is' and directly converted to a Date.
 * Since the Date class implies that the system's default time
 * zone is used, the millisecond value from a direct conversion usually does not represent the expected
 * values for hour/minute/second.
 * This implementation assumes that the hour/minute/second values are more relevant than the millisecond values,
 * and accepts to create "wrong" milliseconds-since-epoch-values.
 * There may be up to three different time zones involved in the calculation.<br/><br/>
 * Created: 07.08.2022 21:03:12
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2DateConverter extends AbstractOffsetDateTimeConverter<Date> {

	public OffsetDateTime2DateConverter() {
		this(null);
	}

	public OffsetDateTime2DateConverter(ZoneId zone) {
		super(Date.class, zone);
	}

	@Override
	public Date convert(OffsetDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalDateTime ldtAtTargetZone = localDateTimeAtTargetZone(sourceValue);
			return toDate(ldtAtTargetZone);
		}
	}

}

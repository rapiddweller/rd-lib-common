/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Converts a {@link OffsetDateTime} value to a {@link java.sql.Timestamp} value that represents the datetime
 * in the configured time {@link #zone}. If no zone was specified, the datetime values of the argument
 * are taken 'as is' and directly converted to a Date.
 * Since the Date class implies that the system's default time
 * zone is used, the millisecond value from a direct conversion usually does not represent the expected
 * values for hour/minute/second.
 * This implementation assumes that the hour/minute/second values are more relevant than the millisecond values,
 * and accepts to create "wrong" milliseconds-since-epoch-values.
 * There may be up to three different time zones involved in the calculation.<br/><br/>
 * Created: 07.08.2022 21:07:26
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2TimestampConverter extends AbstractOffsetDateTimeConverter<Timestamp> {

	public OffsetDateTime2TimestampConverter() {
		this(null);
	}

	public OffsetDateTime2TimestampConverter(ZoneId zone) {
		super(Timestamp.class, zone);
	}

	// interface implementation --------------------------------------------------------------------------------------

	@Override
	public Timestamp convert(OffsetDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalDateTime ldtAtTargetZone = localDateTimeAtTargetZone(sourceValue);
			return toTimestamp(ldtAtTargetZone);
		}
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts a {@link java.time.ZonedDateTime} to a {@link java.sql.Timestamp}.<br/><br/>
 * Created: 28.07.2022 13:39:54
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2TimestampConverter extends AbstractZonedDateTimeConverter<Timestamp> {

	public ZonedDateTime2TimestampConverter() {
		this(null);
	}

	public ZonedDateTime2TimestampConverter(ZoneId zone) {
		super(Timestamp.class, zone);
	}

	@Override
	public Timestamp convert(ZonedDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalDateTime ldtAtTargetZone = localDateTimeAtTargetZone(sourceValue);
			return toTimestamp(ldtAtTargetZone);
		}
	}

}

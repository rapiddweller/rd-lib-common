/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Converts {@link java.time.OffsetDateTime} to {@link java.sql.Time}.<br/><br/>
 * Created: 07.08.2022 22:08:57
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2TimeConverter extends AbstractOffsetDateTimeConverter<Time> {

	public OffsetDateTime2TimeConverter() {
		this(null);
	}

	public OffsetDateTime2TimeConverter(ZoneId zone) {
		super(Time.class, zone);
	}

	@Override
	public Time convert(OffsetDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalDateTime ldtAtTargetZone = localDateTimeAtTargetZone(sourceValue);
			return toTime(ldtAtTargetZone);
		}
	}

}

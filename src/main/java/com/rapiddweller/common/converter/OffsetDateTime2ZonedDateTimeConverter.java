/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts an {@link java.time.OffsetDateTime} to a {@link java.time.ZonedDateTime}.<br/><br/>
 * Created: 07.08.2022 19:08:31
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2ZonedDateTimeConverter extends AbstractZonedConverter<OffsetDateTime, ZonedDateTime> {

	public OffsetDateTime2ZonedDateTimeConverter() {
		this(null);
	}

	public OffsetDateTime2ZonedDateTimeConverter(ZoneId zone) {
		super(OffsetDateTime.class, ZonedDateTime.class, zone);
	}

	@Override
	public ZonedDateTime convert(OffsetDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else if (zone == null) {
			return sourceValue.toZonedDateTime();
		} else {
			return sourceValue.atZoneSameInstant(zone);
		}
	}

}

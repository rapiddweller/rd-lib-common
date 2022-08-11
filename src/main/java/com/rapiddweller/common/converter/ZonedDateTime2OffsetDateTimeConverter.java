/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts a {@link java.time.ZonedDateTime} to an {@link java.time.OffsetDateTime}.<br/><br/>
 * Created: 07.08.2022 19:11:57
 * @author Volker Bergmann
 * @since 3.0.0
 */
public class ZonedDateTime2OffsetDateTimeConverter extends AbstractZonedConverter<ZonedDateTime, OffsetDateTime> {

	@SuppressWarnings("unused")
	public ZonedDateTime2OffsetDateTimeConverter() {
		this(null);
	}

	public ZonedDateTime2OffsetDateTimeConverter(ZoneId zone) {
		super(ZonedDateTime.class, OffsetDateTime.class, zone);
	}

	@Override
	public OffsetDateTime convert(ZonedDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else if (zone == null){
			return sourceValue.toOffsetDateTime();
		} else {
			return sourceValue.withZoneSameInstant(zone).toOffsetDateTime();
		}
	}

}

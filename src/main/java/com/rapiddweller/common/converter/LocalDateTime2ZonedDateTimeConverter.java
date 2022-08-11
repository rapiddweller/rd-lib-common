/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts a {@link java.time.LocalDateTime} to a {@link java.time.ZonedDateTime}.<br/><br/>
 * Created: 07.08.2022 19:10:06
 * @author Volker Bergmann
 * @since 3.0.0
 */
public class LocalDateTime2ZonedDateTimeConverter extends AbstractZonedConverter<LocalDateTime, ZonedDateTime> {

	@SuppressWarnings("unused")
	public LocalDateTime2ZonedDateTimeConverter() {
		this(ZoneId.systemDefault());
	}

	public LocalDateTime2ZonedDateTimeConverter(ZoneId zone) {
		super(LocalDateTime.class, ZonedDateTime.class, zone);
	}

	@Override
	public ZonedDateTime convert(LocalDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return sourceValue.atZone(zone);
		}
	}

}

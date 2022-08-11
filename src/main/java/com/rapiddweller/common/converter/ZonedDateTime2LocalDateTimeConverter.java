/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts a {@link java.time.ZonedDateTime} to a {@link java.time.LocalDateTime}.<br/><br/>
 * Created: 07.08.2022 19:11:03
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2LocalDateTimeConverter extends AbstractZonedDateTimeConverter<LocalDateTime> {

	public ZonedDateTime2LocalDateTimeConverter() {
		this(null);
	}

	public ZonedDateTime2LocalDateTimeConverter(ZoneId zone) {
		super(LocalDateTime.class, zone);
	}

	@Override
	public LocalDateTime convert(ZonedDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return localDateTimeAtTargetZone(sourceValue);
		}
	}

}

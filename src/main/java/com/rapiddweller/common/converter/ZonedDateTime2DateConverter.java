/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Converts a {@link java.time.ZonedDateTime} to a {@link java.util.Date}.<br/><br/>
 * Created: 28.07.2022 12:35:30
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2DateConverter extends AbstractZonedDateTimeConverter<Date> {

	public ZonedDateTime2DateConverter() {
		this(null);
	}

	public ZonedDateTime2DateConverter(ZoneId zone) {
		super(Date.class, zone);
	}

	@Override
	public Date convert(ZonedDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			LocalDateTime ldtAtTargetZone = localDateTimeAtTargetZone(sourceValue);
			return toDate(ldtAtTargetZone);
		}
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Converts {@link java.time.OffsetDateTime}s to {@link java.time.LocalDateTime}s.<br/><br/>
 * Created: 07.08.2022 19:19:35
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2LocalDateTimeConverter extends AbstractOffsetDateTimeConverter<LocalDateTime> {

	public OffsetDateTime2LocalDateTimeConverter() {
		this(null);
	}

	public OffsetDateTime2LocalDateTimeConverter(ZoneId zone) {
		super(LocalDateTime.class, zone);
	}

	@Override
	public LocalDateTime convert(OffsetDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else if (zone == null) {
			return sourceValue.toLocalDateTime();
		} else {
			return localDateTimeAtTargetZone(sourceValue);
		}
	}

}

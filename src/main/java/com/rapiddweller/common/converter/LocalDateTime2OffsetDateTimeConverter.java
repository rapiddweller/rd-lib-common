/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.exception.ExceptionFactory;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Converts {@link java.time.LocalDateTime} to {@link java.time.OffsetDateTime}.<br/><br/>
 * Created: 07.08.2022 21:10:05
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2OffsetDateTimeConverter extends AbstractZonedConverter<LocalDateTime, OffsetDateTime> {

	@SuppressWarnings("unused")
	public LocalDateTime2OffsetDateTimeConverter() {
		this(ZoneId.systemDefault());
	}

	public LocalDateTime2OffsetDateTimeConverter(ZoneId zone) {
		super(LocalDateTime.class, OffsetDateTime.class, zone);
	}

	@Override
	public OffsetDateTime convert(LocalDateTime sourceValue) {
		if (sourceValue == null) {
			return null;
		} else if (zone == null) {
			throw ExceptionFactory.getInstance().configurationError("Zone is null for " + getClass().getSimpleName());
		} else {
			return localDateTimeAtTargetZone(sourceValue);
		}
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Converts {@link Date} to {@link java.time.OffsetDateTime}.<br/><br/>
 * Created: 07.08.2022 21:20:21
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2OffsetDateTimeConverter extends AbstractZonedConverter<Date, OffsetDateTime> {

	@SuppressWarnings("unused")
	public Date2OffsetDateTimeConverter() {
		this(ZoneId.systemDefault());
	}

	public Date2OffsetDateTimeConverter(ZoneId zone) {
		super(Date.class, OffsetDateTime.class, zone);
	}

	@Override
	public OffsetDateTime convert(Date sourceValue) {
		if (sourceValue == null) {
			return null;
		} else if (zone != null) {
			LocalDateTime localDateTime = JavaTimeUtil.toLocalDateTime(sourceValue);
			ZoneOffset offset = zone.getRules().getOffset(localDateTime);
			return OffsetDateTime.of(localDateTime, offset);
		} else {
			throw ExceptionFactory.getInstance().configurationError(
				"'zone' property of " + getClass().getSimpleName() + " is not set");
		}
	}

}

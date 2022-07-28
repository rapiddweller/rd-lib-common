/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Converts a {@link java.time.ZonedDateTime} to a {@link java.sql.Timestamp}.<br/><br/>
 * Created: 28.07.2022 13:39:54
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2TimestampConverter extends ThreadSafeConverter<ZonedDateTime, Timestamp> {

	public ZonedDateTime2TimestampConverter() {
		super(ZonedDateTime.class, Timestamp.class);
	}

	@Override
	public Timestamp convert(ZonedDateTime sourceValue) {
		return Timestamp.valueOf(sourceValue.toLocalDateTime());
	}

}

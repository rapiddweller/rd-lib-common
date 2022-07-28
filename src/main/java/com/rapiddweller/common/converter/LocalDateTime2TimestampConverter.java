/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Converts a {@link LocalDateTime} to a {@link java.sql.Timestamp}.<br/><br/>
 * Created: 28.07.2022 13:27:46
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2TimestampConverter extends ThreadSafeConverter<LocalDateTime, Timestamp> {

	public LocalDateTime2TimestampConverter() {
		super(LocalDateTime.class, Timestamp.class);
	}

	@Override
	public Timestamp convert(LocalDateTime sourceValue) {
		return Timestamp.valueOf(sourceValue);
	}

}

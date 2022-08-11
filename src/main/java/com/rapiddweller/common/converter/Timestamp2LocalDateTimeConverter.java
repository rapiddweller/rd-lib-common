/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Converts a {@link java.sql.Timestamp} to a LocalDateTime.<br/><br/>
 * Created: 28.07.2022 13:33:44
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2LocalDateTimeConverter extends ThreadSafeConverter<Timestamp, LocalDateTime> {

	public Timestamp2LocalDateTimeConverter() {
		super(Timestamp.class, LocalDateTime.class);
	}

	@Override
	public LocalDateTime convert(Timestamp sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return JavaTimeUtil.toLocalDateTime(sourceValue.toInstant());
		}
	}

}

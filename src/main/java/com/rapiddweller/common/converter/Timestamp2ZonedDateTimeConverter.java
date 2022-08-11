/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converts {@link Timestamp} to {@link ZonedDateTime}.<br/><br/>
 * Created: 07.08.2022 21:39:19
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2ZonedDateTimeConverter extends AbstractZonedConverter<Timestamp, ZonedDateTime> {

	public Timestamp2ZonedDateTimeConverter() {
		super(Timestamp.class, ZonedDateTime.class, ZoneId.systemDefault());
	}

	@Override
	public ZonedDateTime convert(Timestamp sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return ZonedDateTime.ofInstant(sourceValue.toInstant(), zone);
		}
	}

}

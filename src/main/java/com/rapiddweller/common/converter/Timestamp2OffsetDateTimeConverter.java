/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Converts {@link Timestamp}s to {@link OffsetDateTime}s.<br/><br/>
 * Created: 07.08.2022 21:35:09
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2OffsetDateTimeConverter extends AbstractZonedConverter<Timestamp, OffsetDateTime> {

	public Timestamp2OffsetDateTimeConverter() {
		super(Timestamp.class, OffsetDateTime.class, ZoneId.systemDefault());
	}

	@Override
	public OffsetDateTime convert(Timestamp sourceValue) {
		if (sourceValue == null) {
			return null;
		} else {
			return OffsetDateTime.ofInstant(sourceValue.toInstant(), zone);
		}
	}

}

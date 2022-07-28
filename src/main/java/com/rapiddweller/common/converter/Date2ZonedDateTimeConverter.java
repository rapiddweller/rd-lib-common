/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Converts a {@link Date} to a {@link java.time.ZonedDateTime}.<br/><br/>
 * Created: 28.07.2022 13:35:08
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2ZonedDateTimeConverter extends ThreadSafeConverter<Date, ZonedDateTime> {

	private ZoneId zone;

	public Date2ZonedDateTimeConverter() {
		this(ZoneId.systemDefault());
	}

	public Date2ZonedDateTimeConverter(ZoneId zone) {
		super(Date.class, ZonedDateTime.class);
		this.zone = zone;
	}

	public ZoneId getZone() {
		return zone;
	}

	public void setZone(ZoneId zone) {
		this.zone = zone;
	}

	@Override
	public ZonedDateTime convert(Date sourceValue) {
		return JavaTimeUtil.toZonedDateTime(sourceValue, zone);
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Abstract parent class for {@link com.rapiddweller.common.Converter}
 * implementations which are thread-safe and relate to a {@link java.time.ZoneId}.<br/><br/>
 * Created: 07.08.2022 22:18:51
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractZonedConverter<S, T> extends ThreadSafeConverter<S, T> {

	protected ZoneId zone;

	protected AbstractZonedConverter(Class<S> sourceType, Class<T> targetType, ZoneId zone) {
		super(sourceType, targetType);
		setZone(zone);
	}

	public ZoneId getZone() {
		return zone;
	}

	public void setZone(ZoneId zone) {
		this.zone = zone;
	}

	// internal helper methods ---------------------------------------------------------------------------------------

	protected LocalDateTime localDateTimeAtTargetZone(OffsetDateTime sourceValue) {
		LocalDateTime ldtAtTargetZone;
		if (zone == null) {
			// simply take hour/minutes/seconds, ignoring timezone
			ldtAtTargetZone = sourceValue.toLocalDateTime();
		} else {
			// The ZonedDateTime at this object's zone is calculated
			ZonedDateTime zdtAtTargetZone = sourceValue.atZoneSameInstant(zone);
			// From this, the {@link LocalDateTime} is calculated
			// to get a representation of the hour/minute/second values
			ldtAtTargetZone = zdtAtTargetZone.toLocalDateTime();
		}
		return ldtAtTargetZone;
	}

	protected LocalDateTime localDateTimeAtTargetZone(ZonedDateTime sourceValue) {
		LocalDateTime ldtAtTargetZone;
		if (zone == null) {
			// simply take hour/minutes/seconds, ignoring timezone
			ldtAtTargetZone = sourceValue.toLocalDateTime();
		} else {
			// The ZonedDateTime at this object's zone is calculated
			ZonedDateTime zdtAtTargetZone = sourceValue.withZoneSameInstant(zone);
			// From this, the {@link LocalDateTime} is calculated
			// to get a representation of the hour/minute/second values
			ldtAtTargetZone = zdtAtTargetZone.toLocalDateTime();
		}
		return ldtAtTargetZone;
	}

	protected OffsetDateTime localDateTimeAtTargetZone(LocalDateTime sourceValue) {
		return sourceValue.atZone(zone).toOffsetDateTime();
	}

	protected Date toDate(LocalDateTime localDateTime) {
		// In order to construct a Date (which assumes the system's default time zone)
		// with the expected hour/minute/second values, that LocalDateTime
		// is converted to a ZonedDateTime in the system's default time zone
		ZonedDateTime sameDateTimeInDefaultZone = localDateTime.atZone(ZoneId.systemDefault());
		// The resulting ZonedDateTime is converted to an Instant,
		// which is then used to construct the resulting Date
		Instant instant = sameDateTimeInDefaultZone.toInstant();
		return Date.from(instant);
	}

	protected Timestamp toTimestamp(LocalDateTime localDateTime) {
		// In order to construct a Timestamp (which assumes the system's default time zone)
		// with the expected hour/minute/second values, that LocalDateTime
		// is converted to a ZonedDateTime in the system's default time zone
		ZonedDateTime sameDateTimeInDefaultZone = localDateTime.atZone(ZoneId.systemDefault());
		// The resulting ZonedDateTime is converted to an Instant,
		// which is then used to construct the resulting Timestamp
		Instant instant = sameDateTimeInDefaultZone.toInstant();
		return Timestamp.from(instant);
	}

	protected Time toTime(LocalDateTime localDateTime) {
		// In order to construct a Time object (which assumes the system's default time zone)
		// with the expected hour/minute/second values, that LocalDateTime
		// is converted to a ZonedDateTime in the system's default time zone
		ZonedDateTime sameDateTimeInDefaultZone = localDateTime.atZone(ZoneId.systemDefault());
		// The resulting ZonedDateTime is converted to an Instant,
		// which is then used to construct the resulting Time
		LocalTime localTime = sameDateTimeInDefaultZone.toLocalTime();
		Time timeWithSecondResolution = Time.valueOf(localTime);
		return new Time(timeWithSecondResolution.getTime() + localTime.getNano() / 1000000);
	}

}

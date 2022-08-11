/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.TimeUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Common parent class for date-related {@link com.rapiddweller.common.Converter} tests.<br/><br/>
 * Created: 28.07.2022 13:43:00
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractDateConverterTest extends AbstractConverterTest {

	public static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");
	public static final ZoneId LONDON = ZoneId.of("Europe/London");
	public static final ZoneId CHICAGO = ZoneId.of("America/Chicago");
	public static final ZoneId UTC = ZoneId.of("UTC");

	public static final TimeZone BERLIN_TZ = JavaTimeUtil.toTimeZone(BERLIN);
	public static final TimeZone LONDON_TZ = JavaTimeUtil.toTimeZone(LONDON);
	public static final TimeZone CHICAGO_TZ = JavaTimeUtil.toTimeZone(CHICAGO);
	public static final TimeZone UTC_TZ = JavaTimeUtil.toTimeZone(UTC);

	public static final LocalDateTime LDT_NANOS_BERLIN = LocalDateTime.of(2022, 7, 28, 13, 44, 58, 123123123);
	public static final LocalDateTime LDT_NANOS_LONDON = LocalDateTime.of(2022, 7, 28, 12, 44, 58, 123123123);
	public static final LocalDateTime LDT_NANOS_CHICAGO = LocalDateTime.of(2022, 7, 28, 6, 44, 58, 123123123);

	public static final LocalDateTime LDT_MILLIS_BERLIN = LocalDateTime.of(2022, 7, 28, 13, 44, 58, 123000000);
	public static final LocalDateTime LDT_MILLIS_LONDON = LocalDateTime.of(2022, 7, 28, 12, 44, 58, 123000000);
	public static final LocalDateTime LDT_MILLIS_CHICAGO = LocalDateTime.of(2022, 7, 28, 6, 44, 58, 123000000);

	public static final OffsetDateTime ODT_NANOS_BERLIN = OffsetDateTime.of(2022, 7, 28, 13, 44, 58, 123123123,
		BERLIN.getRules().getOffset(LDT_NANOS_BERLIN));
	public static final OffsetDateTime ODT_NANOS_LONDON = OffsetDateTime.of(2022, 7, 28, 12, 44, 58, 123123123,
		LONDON.getRules().getOffset(LDT_NANOS_LONDON));
	public static final OffsetDateTime ODT_NANOS_CHICAGO = OffsetDateTime.of(2022, 7, 28, 6, 44, 58, 123123123,
		CHICAGO.getRules().getOffset(LDT_NANOS_CHICAGO));

	public static final OffsetDateTime ODT_MILLIS_BERLIN = OffsetDateTime.of(2022, 7, 28, 13, 44, 58, 123000000,
		BERLIN.getRules().getOffset(LDT_NANOS_BERLIN));
	public static final OffsetDateTime ODT_MILLIS_CHICAGO = OffsetDateTime.of(2022, 7, 28, 6, 44, 58, 123000000,
		CHICAGO.getRules().getOffset(LDT_NANOS_CHICAGO));

	public static final ZonedDateTime ZDT_NANOS_BERLIN = ZonedDateTime.of(2022, 7, 28, 13, 44, 58, 123123123,
		BERLIN);
	public static final ZonedDateTime ZDT_NANOS_LONDON = ZonedDateTime.of(2022, 7, 28, 12, 44, 58, 123123123,
		LONDON);
	public static final ZonedDateTime ZDT_NANOS_CHICAGO = ZonedDateTime.of(2022, 7, 28, 6, 44, 58, 123123123,
		CHICAGO);

	public static final ZonedDateTime ZDT_MILLIS_BERLIN = ZonedDateTime.of(2022, 7, 28, 13, 44, 58, 123000000,
		BERLIN);
	public static final ZonedDateTime ZDT_MILLIS_CHICAGO = ZonedDateTime.of(2022, 7, 28, 6, 44, 58, 123000000,
		CHICAGO);


	public static final LocalDate LOCAL_DATE = LocalDate.of(2022, 7, 28);

	/** The reference timepoint in the time of Chicago but with the system's default time zone */
	public static final Date DATE_BERLIN_DTZ = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
	public static final Date DATE_LONDON_DTZ = TimeUtil.date(2022, 6, 28, 12, 44, 58, 123);

	/** The reference timepoint in the time of Chicago but with the system's default time zone */
	public static final Date DATE_CHICAGO_DTZ = TimeUtil.date(2022, 6, 28, 6, 44, 58, 123);

	public static final Date DATE_MIDNIGHT = TimeUtil.date(2022, 6, 28, 0, 0, 0, 0);

	public static final long EPOCH_MILLIS_BERLIN = JavaTimeUtil.millisSinceEpoch(ZDT_NANOS_BERLIN);
	public static final long EPOCH_MILLIS_CHICAGO = JavaTimeUtil.millisSinceEpoch(ZDT_NANOS_CHICAGO);

	public static final Timestamp TIMESTAMP_BERLIN = TimeUtil.timestamp(2022, 6, 28, 13, 44, 58, 123123123);
	public static final Timestamp TIMESTAMP_LONDON = TimeUtil.timestamp(2022, 6, 28, 12, 44, 58, 123123123);
	public static final Timestamp TIMESTAMP_CHICAGO = TimeUtil.timestamp(2022, 6, 28, 6, 44, 58, 123123123);

	public static final Time TIME_BERLIN = TimeUtil.time(13, 44, 58, 123);
	public static final Time TIME_LONDON = TimeUtil.time(12, 44, 58, 123);
	public static final Time TIME_CHICAGO = TimeUtil.time(6, 44, 58, 123);

	public static final Time TIME = TimeUtil.time(13, 44, 58, 123);

	public static final LocalTime LOCAL_TIME_MILLIS = LocalTime.of(13, 44, 58, 123000000);
	public static final LocalTime LOCAL_TIME_NANOS = LocalTime.of(13, 44, 58, 123123123);
	public static final String LOCAL_TIME_STRING = "13:44:58.123123123";

	@SuppressWarnings("rawtypes")
	protected AbstractDateConverterTest(Class<? extends Converter> converterClass) {
		super(converterClass);
	}

	protected void assertEqualDates(Date expected, Date actual) {
		assertEquals(expected, actual);
		assertSame(actual.getClass(), Date.class);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSSSSSSSS");
		assertEquals(formatter.format(expected), formatter.format(actual));
	}

	protected void assertEqualTimestamps(Timestamp expected, Timestamp actual) {
		assertEquals(expected, actual);
		assertSame(actual.getClass(), Timestamp.class);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSSSSSSSS");
		assertEquals(formatter.format(expected), formatter.format(actual));
	}

	protected void assertEqualTimes(Time expected, Time actual) {
		assertEquals(expected, actual);
		assertSame(actual.getClass(), Time.class);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSSSSSSSS");
		assertEquals(formatter.format(expected), formatter.format(actual));
	}

	protected void assertEqualLocalDateTimes(LocalDateTime expected, LocalDateTime actual) {
		assertEquals(expected, actual);
		assertSame(actual.getClass(), LocalDateTime.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSSSSSSSS");
		assertEquals(formatter.format(expected), formatter.format(actual));
	}

	protected void assertEqualZonedDateTimes(ZonedDateTime expected, ZonedDateTime actual) {
		assertEqualZonedDateTimes(expected, actual, true);
	}

	protected void assertEqualZonedDateTimes(ZonedDateTime expected, ZonedDateTime actual, boolean equalZones) {
		if (equalZones) {
			assertEquals(expected, actual);
		}
		assertSame(actual.getClass(), ZonedDateTime.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSSSSSSSS");
		assertEquals(formatter.format(expected), formatter.format(actual));
	}

	protected void assertEqualOffsetDateTimes(OffsetDateTime expected, OffsetDateTime actual) {
		assertEquals(expected, actual);
		assertSame(actual.getClass(), OffsetDateTime.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSSSSSSSS");
		assertEquals(formatter.format(expected), formatter.format(actual));
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.TimeUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Common parent class for date-related {@link com.rapiddweller.common.Converter} tests.<br/><br/>
 * Created: 28.07.2022 13:43:00
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractDateConverterTest extends AbstractConverterTest {

	public static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

	public static final LocalDateTime LDT_NANOS  = LocalDateTime.of(2022, 7, 28, 13, 44, 58, 123123123);
	public static final LocalDateTime LDT_MILLIS = LocalDateTime.of(2022, 7, 28, 13, 44, 58, 123000000);
	public static final LocalDate LD = LocalDate.of(2022, 7, 28);
	public static final Date DATE = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
	public static final Timestamp TIMESTAMP = TimeUtil.timestamp(2022, 6, 28, 13, 44, 58, 123123123);
	public static final ZonedDateTime ZDT_MILLIS  = ZonedDateTime.of(2022, 7, 28, 13, 44, 58, 123000000, BERLIN);
	public static final ZonedDateTime ZDT_NANOS  = ZonedDateTime.of(2022, 7, 28, 13, 44, 58, 123123123, BERLIN);

	public static final Time TIME = TimeUtil.time(13, 44, 58, 123);
	public static final LocalTime LOCAL_TIME_MILLIS = LocalTime.of(13, 44, 58, 123000000);
	public static final LocalTime LOCAL_TIME_NANOS = LocalTime.of(13, 44, 58, 123123123);
	public static final String LOCAL_TIME_STRING = "13:44:58.123123123";

	@SuppressWarnings("rawtypes")
	protected AbstractDateConverterTest(Class<? extends Converter> converterClass) {
		super(converterClass);
	}
}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link OffsetDateTime2DateConverter}.<br/><br/>
 * Created: 07.08.2022 21:01:29
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2DateConverterTest extends AbstractDateConverterTest {

	private final OffsetDateTime2DateConverter berlinConverter = new OffsetDateTime2DateConverter(BERLIN);
	private final OffsetDateTime2DateConverter londonConverter = new OffsetDateTime2DateConverter(LONDON);
	private final OffsetDateTime2DateConverter chicagoConverter = new OffsetDateTime2DateConverter(CHICAGO);

	public OffsetDateTime2DateConverterTest() {
		super(OffsetDateTime2DateConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new OffsetDateTime2DateConverter().convert(null));
	}

	@Test
	public void testDefaultWithBerlinTime() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Date dateBerlin = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
			assertEqualDates(dateBerlin, new OffsetDateTime2DateConverter().convert(ODT_NANOS_BERLIN));
		});
	}

	@Test
	public void testDefaultWithChicagoTime() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Date chicagoDateInZoneBerlin = TimeUtil.date(2022, 6, 28, 6, 44, 58, 123);
			assertEqualDates(chicagoDateInZoneBerlin, new OffsetDateTime2DateConverter().convert(ODT_NANOS_CHICAGO));
		});
	}

	@Test
	public void testZoneNullBerlin() {
		OffsetDateTime2DateConverter nullConverter = new OffsetDateTime2DateConverter(null);
		assertEqualDates(DATE_BERLIN_DTZ, nullConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		OffsetDateTime2DateConverter nullConverter = new OffsetDateTime2DateConverter(null);
		assertEqualDates(DATE_LONDON_DTZ, nullConverter.convert(ODT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		OffsetDateTime2DateConverter nullConverter = new OffsetDateTime2DateConverter(null);
		assertEqualDates(DATE_CHICAGO_DTZ, nullConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualDates(DATE_LONDON_DTZ, londonConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Date dateBerlin = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
			assertEqualDates(dateBerlin, berlinConverter.convert(ODT_NANOS_BERLIN));
		});
	}

	@Test
	public void testZoneChicago() {
		JavaTimeUtil.runInZone(CHICAGO, () -> {
			Date dateChicago = TimeUtil.date(2022, 6, 28, 6, 44, 58, 123);
			assertEqualDates(dateChicago, chicagoConverter.convert(ODT_NANOS_CHICAGO));
		});
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		JavaTimeUtil.runInZone(CHICAGO, () -> {
			Date dateChicago = TimeUtil.date(2022, 6, 28, 6, 44, 58, 123);
			assertEqualDates(dateChicago, chicagoConverter.convert(ODT_NANOS_BERLIN));
		});
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Date dateBerlin = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
			assertEqualDates(dateBerlin, berlinConverter.convert(ODT_NANOS_CHICAGO));
		});
	}

}

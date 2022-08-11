/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;
import org.junit.Ignore;
import org.junit.Test;

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
		JavaTimeUtil.runInZone(BERLIN,
			() -> assertEqualDates(DATE_BERLIN_DTZ, new OffsetDateTime2DateConverter().convert(ODT_NANOS_BERLIN)));
	}

	@Test
	public void testDefaultWithChicagoTime() {
		JavaTimeUtil.runInZone(BERLIN,
			() -> assertEqualDates(DATE_CHICAGO_DTZ, new OffsetDateTime2DateConverter().convert(ODT_NANOS_CHICAGO)));
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
		assertEqualDates(DATE_BERLIN_DTZ, berlinConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualDates(DATE_CHICAGO_DTZ, chicagoConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualDates(DATE_CHICAGO_DTZ, chicagoConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualDates(DATE_BERLIN_DTZ, berlinConverter.convert(ODT_NANOS_CHICAGO));
	}

}

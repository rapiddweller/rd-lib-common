/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link ZonedDateTime2TimestampConverter}.<br/><br/>
 * Created: 28.07.2022 15:22:09
 * @author Volker Bergmann
 * @since 3.0.0
 */
public class ZonedDateTime2TimestampConverterTest extends AbstractDateConverterTest {

	private final ZonedDateTime2TimestampConverter defaultConverter = new ZonedDateTime2TimestampConverter();
	private final ZonedDateTime2TimestampConverter nullConverter = new ZonedDateTime2TimestampConverter(null);
	private final ZonedDateTime2TimestampConverter berlinConverter = new ZonedDateTime2TimestampConverter(BERLIN);
	private final ZonedDateTime2TimestampConverter londonConverter = new ZonedDateTime2TimestampConverter(LONDON);
	private final ZonedDateTime2TimestampConverter chicagoConverter = new ZonedDateTime2TimestampConverter(CHICAGO);

	public ZonedDateTime2TimestampConverterTest() {
		super(ZonedDateTime2TimestampConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new ZonedDateTime2TimestampConverter().convert(null));
	}

	@Test
	public void testDefaultConstructionBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultConstructionChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, defaultConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualTimestamps(TIMESTAMP_LONDON, nullConverter.convert(ZDT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, nullConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualTimestamps(TIMESTAMP_LONDON, londonConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, berlinConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, chicagoConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, chicagoConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, berlinConverter.convert(ZDT_NANOS_CHICAGO));
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link OffsetDateTime2TimeConverter}.<br/><br/>
 * Created: 07.08.2022 22:10:28
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2TimeConverterTest extends AbstractDateConverterTest {

	private final OffsetDateTime2TimeConverter defaultConverter = new OffsetDateTime2TimeConverter();
	private final OffsetDateTime2TimeConverter nullConverter = new OffsetDateTime2TimeConverter(null);
	private final OffsetDateTime2TimeConverter berlinConverter = new OffsetDateTime2TimeConverter(BERLIN);
	private final OffsetDateTime2TimeConverter londonConverter = new OffsetDateTime2TimeConverter(LONDON);
	private final OffsetDateTime2TimeConverter chicagoConverter = new OffsetDateTime2TimeConverter(CHICAGO);

	public OffsetDateTime2TimeConverterTest() {
		super(OffsetDateTime2TimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new OffsetDateTime2TimeConverter().convert(null));
	}

	@Test
	public void testDefaultConstructionBerlin() {
		assertEqualTimes(TIME_BERLIN, defaultConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultConstructionChicago() {
		assertEqualTimes(TIME_CHICAGO, defaultConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualTimes(TIME_BERLIN, defaultConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualTimes(TIME_LONDON, nullConverter.convert(ODT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualTimes(TIME_CHICAGO, nullConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualTimes(TIME_LONDON, londonConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualTimes(TIME_BERLIN, berlinConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualTimes(TIME_CHICAGO, chicagoConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualTimes(TIME_CHICAGO, chicagoConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualTimes(TIME_BERLIN, berlinConverter.convert(ODT_NANOS_CHICAGO));
	}

}

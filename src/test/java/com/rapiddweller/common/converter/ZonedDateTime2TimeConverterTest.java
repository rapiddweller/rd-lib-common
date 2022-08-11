/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link ZonedDateTime2TimeConverter}.<br/><br/>
 * Created: 07.08.2022 22:14:38
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2TimeConverterTest extends AbstractDateConverterTest {

	private final ZonedDateTime2TimeConverter defaultConverter = new ZonedDateTime2TimeConverter();
	private final ZonedDateTime2TimeConverter nullConverter = new ZonedDateTime2TimeConverter(null);
	private final ZonedDateTime2TimeConverter berlinConverter = new ZonedDateTime2TimeConverter(BERLIN);
	private final ZonedDateTime2TimeConverter londonConverter = new ZonedDateTime2TimeConverter(LONDON);
	private final ZonedDateTime2TimeConverter chicagoConverter = new ZonedDateTime2TimeConverter(CHICAGO);

	public ZonedDateTime2TimeConverterTest() {
		super(ZonedDateTime2TimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new ZonedDateTime2TimeConverter().convert(null));
	}

	@Test
	public void testDefaultConstructionBerlin() {
		assertEqualTimes(TIME_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultConstructionChicago() {
		assertEqualTimes(TIME_CHICAGO, defaultConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualTimes(TIME_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualTimes(TIME_LONDON, nullConverter.convert(ZDT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualTimes(TIME_CHICAGO, nullConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualTimes(TIME_LONDON, londonConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualTimes(TIME_BERLIN, berlinConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualTimes(TIME_CHICAGO, chicagoConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualTimes(TIME_CHICAGO, chicagoConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualTimes(TIME_BERLIN, berlinConverter.convert(ZDT_NANOS_CHICAGO));
	}

}

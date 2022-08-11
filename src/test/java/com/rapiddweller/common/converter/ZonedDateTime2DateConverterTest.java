/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link ZonedDateTime2DateConverter}.<br/><br/>
 * Created: 28.07.2022 15:24:46
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2DateConverterTest extends AbstractDateConverterTest {

	private final ZonedDateTime2DateConverter defaultConverter = new ZonedDateTime2DateConverter();
	private final ZonedDateTime2DateConverter nullConverter = new ZonedDateTime2DateConverter(null);
	private final ZonedDateTime2DateConverter berlinConverter = new ZonedDateTime2DateConverter(BERLIN);
	private final ZonedDateTime2DateConverter londonConverter = new ZonedDateTime2DateConverter(LONDON);
	private final ZonedDateTime2DateConverter chicagoConverter = new ZonedDateTime2DateConverter(CHICAGO);

	public ZonedDateTime2DateConverterTest() {
		super(ZonedDateTime2DateConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new ZonedDateTime2DateConverter().convert(null));
	}

	@Test
	public void testDefaultWithBerlinTime() {
		assertEqualDates(DATE_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultWithChicagoTime() {
		assertEqualDates(DATE_CHICAGO, defaultConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualDates(DATE_BERLIN, nullConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualDates(DATE_LONDON, nullConverter.convert(ZDT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualDates(DATE_CHICAGO, nullConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualDates(DATE_LONDON, londonConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualDates(DATE_BERLIN, berlinConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualDates(DATE_CHICAGO, chicagoConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualDates(DATE_CHICAGO, chicagoConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualDates(DATE_BERLIN, berlinConverter.convert(ZDT_NANOS_CHICAGO));
	}

}

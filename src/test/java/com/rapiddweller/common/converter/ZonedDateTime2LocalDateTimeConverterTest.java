/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link ZonedDateTime2LocalDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 19:43:28
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2LocalDateTimeConverterTest extends AbstractDateConverterTest {

	private final ZonedDateTime2LocalDateTimeConverter defaultConverter = new ZonedDateTime2LocalDateTimeConverter();
	private final ZonedDateTime2LocalDateTimeConverter nullConverter = new ZonedDateTime2LocalDateTimeConverter(null);
	private final ZonedDateTime2LocalDateTimeConverter berlinConverter = new ZonedDateTime2LocalDateTimeConverter(BERLIN);
	private final ZonedDateTime2LocalDateTimeConverter londonConverter = new ZonedDateTime2LocalDateTimeConverter(LONDON);
	private final ZonedDateTime2LocalDateTimeConverter chicagoConverter = new ZonedDateTime2LocalDateTimeConverter(CHICAGO);

	public ZonedDateTime2LocalDateTimeConverterTest() {
		super(ZonedDateTime2LocalDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new ZonedDateTime2LocalDateTimeConverter().convert(null));
	}

	@Test
	public void testDefaultWithBerlinTime() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultWithChicagoTime() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, defaultConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, nullConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualLocalDateTimes(LDT_NANOS_LONDON, nullConverter.convert(ZDT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, nullConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualLocalDateTimes(LDT_NANOS_LONDON, londonConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, berlinConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, chicagoConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, chicagoConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, berlinConverter.convert(ZDT_NANOS_CHICAGO));
	}

}

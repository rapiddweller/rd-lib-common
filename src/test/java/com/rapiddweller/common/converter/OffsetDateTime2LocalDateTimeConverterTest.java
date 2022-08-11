/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link OffsetDateTime2LocalDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 19:57:15
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2LocalDateTimeConverterTest  extends AbstractDateConverterTest {

	private final OffsetDateTime2LocalDateTimeConverter defaultConverter = new OffsetDateTime2LocalDateTimeConverter();
	private final OffsetDateTime2LocalDateTimeConverter nullConverter = new OffsetDateTime2LocalDateTimeConverter(null);
	private final OffsetDateTime2LocalDateTimeConverter berlinConverter = new OffsetDateTime2LocalDateTimeConverter(BERLIN);
	private final OffsetDateTime2LocalDateTimeConverter londonConverter = new OffsetDateTime2LocalDateTimeConverter(LONDON);
	private final OffsetDateTime2LocalDateTimeConverter chicagoConverter = new OffsetDateTime2LocalDateTimeConverter(CHICAGO);

	public OffsetDateTime2LocalDateTimeConverterTest() {
		super(OffsetDateTime2LocalDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new OffsetDateTime2LocalDateTimeConverter().convert(null));
	}

	@Test
	public void testDefaultWithBerlinTime() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, defaultConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultWithChicagoTime() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, defaultConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, nullConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualLocalDateTimes(LDT_NANOS_LONDON, nullConverter.convert(ODT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, nullConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualLocalDateTimes(LDT_NANOS_LONDON, londonConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, berlinConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, chicagoConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualLocalDateTimes(LDT_NANOS_CHICAGO, chicagoConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualLocalDateTimes(LDT_NANOS_BERLIN, berlinConverter.convert(ODT_NANOS_CHICAGO));
	}

}

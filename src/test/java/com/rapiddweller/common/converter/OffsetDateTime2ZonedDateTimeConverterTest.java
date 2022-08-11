/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link OffsetDateTime2ZonedDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 19:22:36
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2ZonedDateTimeConverterTest extends AbstractDateConverterTest {

	private final OffsetDateTime2ZonedDateTimeConverter berlinConverter = new OffsetDateTime2ZonedDateTimeConverter(BERLIN);
	private final OffsetDateTime2ZonedDateTimeConverter londonConverter = new OffsetDateTime2ZonedDateTimeConverter(LONDON);
	private final OffsetDateTime2ZonedDateTimeConverter chicagoConverter = new OffsetDateTime2ZonedDateTimeConverter(CHICAGO);

	public OffsetDateTime2ZonedDateTimeConverterTest() {
		super(OffsetDateTime2ZonedDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		OffsetDateTime2ZonedDateTimeConverter defaultConverter = new OffsetDateTime2ZonedDateTimeConverter();
		assertNull(defaultConverter.convert(null));
	}

	@Test
	public void testDefaultWithBerlinTime() {
		OffsetDateTime2ZonedDateTimeConverter defaultConverter = new OffsetDateTime2ZonedDateTimeConverter();
		assertEqualZonedDateTimes(ZDT_NANOS_BERLIN, defaultConverter.convert(ODT_NANOS_BERLIN), false);
	}

	@Test
	public void testDefaultWithChicagoTime() {
		OffsetDateTime2ZonedDateTimeConverter defaultConverter = new OffsetDateTime2ZonedDateTimeConverter();
		assertEqualZonedDateTimes(ZDT_NANOS_CHICAGO, defaultConverter.convert(ODT_NANOS_CHICAGO), false);
	}

	@Test
	public void testZoneNull() {
		OffsetDateTime2ZonedDateTimeConverter nullConverter = new OffsetDateTime2ZonedDateTimeConverter(null);
		assertEqualZonedDateTimes(ZDT_NANOS_CHICAGO, nullConverter.convert(ODT_NANOS_CHICAGO), false);
	}

	@Test
	public void testZoneLondon() {
		assertEqualZonedDateTimes(ZDT_NANOS_LONDON, londonConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualZonedDateTimes(ZDT_NANOS_BERLIN, berlinConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualZonedDateTimes(ZDT_NANOS_CHICAGO, chicagoConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualZonedDateTimes(ZDT_NANOS_CHICAGO, chicagoConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualZonedDateTimes(ZDT_NANOS_BERLIN, berlinConverter.convert(ODT_NANOS_CHICAGO));
	}

}

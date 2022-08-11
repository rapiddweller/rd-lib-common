/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link ZonedDateTime2OffsetDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 19:40:13
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2OffsetDateTimeConverterTest extends AbstractDateConverterTest {

	private final ZonedDateTime2OffsetDateTimeConverter defaultConverter = new ZonedDateTime2OffsetDateTimeConverter();
	private final ZonedDateTime2OffsetDateTimeConverter berlinConverter = new ZonedDateTime2OffsetDateTimeConverter(BERLIN);
	private final ZonedDateTime2OffsetDateTimeConverter chicagoConverter = new ZonedDateTime2OffsetDateTimeConverter(CHICAGO);

	public ZonedDateTime2OffsetDateTimeConverterTest() {
		super(ZonedDateTime2OffsetDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new ZonedDateTime2OffsetDateTimeConverter().convert(null));
	}

	@Test
	public void testBerlin() {
		assertEqualOffsetDateTimes(ODT_NANOS_BERLIN, defaultConverter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testLondon() {
		assertEqualOffsetDateTimes(ODT_NANOS_LONDON, defaultConverter.convert(ZDT_NANOS_LONDON));
	}

	@Test
	public void testChicago() {
		assertEqualOffsetDateTimes(ODT_NANOS_CHICAGO, defaultConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testChicagoTimeInBerlinZone() {
		assertEqualOffsetDateTimes(ODT_NANOS_BERLIN, berlinConverter.convert(ZDT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeInChicagoZone() {
		assertEqualOffsetDateTimes(ODT_NANOS_CHICAGO, chicagoConverter.convert(ZDT_NANOS_BERLIN));
	}

}

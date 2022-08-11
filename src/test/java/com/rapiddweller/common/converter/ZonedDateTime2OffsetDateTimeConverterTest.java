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

	private final ZonedDateTime2OffsetDateTimeConverter converter = new ZonedDateTime2OffsetDateTimeConverter();

	public ZonedDateTime2OffsetDateTimeConverterTest() {
		super(ZonedDateTime2OffsetDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new ZonedDateTime2OffsetDateTimeConverter().convert(null));
	}

	@Test
	public void testBerlin() {
		assertEqualOffsetDateTimes(ODT_NANOS_BERLIN, converter.convert(ZDT_NANOS_BERLIN));
	}

	@Test
	public void testLondon() {
		assertEqualOffsetDateTimes(ODT_NANOS_LONDON, converter.convert(ZDT_NANOS_LONDON));
	}

	@Test
	public void testChicago() {
		assertEqualOffsetDateTimes(ODT_NANOS_CHICAGO, converter.convert(ZDT_NANOS_CHICAGO));
	}

	// TODO v3.0.0 Add tests for conversion between time zones
}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalDateTime2OffsetDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 21:12:27
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2OffsetDateTimeConverterTest extends AbstractDateConverterTest {

	public LocalDateTime2OffsetDateTimeConverterTest() {
		super(LocalDateTime2OffsetDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalDateTime2OffsetDateTimeConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(ODT_NANOS_BERLIN, new LocalDateTime2OffsetDateTimeConverter().convert(LDT_NANOS_BERLIN));
	}

}

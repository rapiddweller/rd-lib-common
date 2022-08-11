/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalDateTime2ZonedDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 19:47:04
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2ZonedDateTimeConverterTest extends AbstractDateConverterTest {

	public LocalDateTime2ZonedDateTimeConverterTest() {
		super(LocalDateTime2ZonedDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalDateTime2ZonedDateTimeConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(ZDT_NANOS_BERLIN, new LocalDateTime2ZonedDateTimeConverter(BERLIN).convert(LDT_NANOS_BERLIN));
	}

}

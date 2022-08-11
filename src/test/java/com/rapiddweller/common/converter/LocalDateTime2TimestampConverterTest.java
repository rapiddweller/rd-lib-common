/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalDateTime2TimestampConverter}.<br/><br/>
 * Created: 28.07.2022 14:51:34
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2TimestampConverterTest extends AbstractDateConverterTest {

	public LocalDateTime2TimestampConverterTest() {
		super(LocalDateTime2TimestampConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalDateTime2TimestampConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(TIMESTAMP_BERLIN, new LocalDateTime2TimestampConverter().convert(LDT_NANOS_BERLIN));
	}

}

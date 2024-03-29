/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Timestamp2LocalDateTimeConverter}.<br/><br/>
 * Created: 28.07.2022 15:12:45
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2LocalDateTimeConverterTest extends AbstractDateConverterTest {

	public Timestamp2LocalDateTimeConverterTest() {
		super(Timestamp2LocalDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Timestamp2LocalDateTimeConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(LDT_NANOS_BERLIN, new Timestamp2LocalDateTimeConverter().convert(TIMESTAMP_BERLIN));
	}

}

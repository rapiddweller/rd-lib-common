/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Timestamp2OffsetDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 21:37:40
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2OffsetDateTimeConverterTest extends AbstractDateConverterTest {

	public Timestamp2OffsetDateTimeConverterTest() {
		super(Timestamp2OffsetDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Timestamp2OffsetDateTimeConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(ODT_NANOS_BERLIN, new Timestamp2OffsetDateTimeConverter().convert(TIMESTAMP_BERLIN));
	}

}

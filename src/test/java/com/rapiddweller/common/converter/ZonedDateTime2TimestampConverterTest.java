/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ZonedDateTime2TimestampConverter}.<br/><br/>
 * Created: 28.07.2022 15:22:09
 * @author Volker Bergmann
 * @since 3.0.0
 */
public class ZonedDateTime2TimestampConverterTest extends AbstractDateConverterTest {

	public ZonedDateTime2TimestampConverterTest() {
		super(ZonedDateTime2TimestampConverter.class);
	}

	@Test
	public void test() {
		assertEquals(TIMESTAMP, new ZonedDateTime2TimestampConverter().convert(ZDT_NANOS));
	}

}

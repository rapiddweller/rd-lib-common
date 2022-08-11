/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalDateTime2TimeConverter}.<br/><br/>
 * Created: 07.08.2022 22:07:19
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2TimeConverterTest extends AbstractDateConverterTest {

	public LocalDateTime2TimeConverterTest() {
		super(LocalDateTime2TimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalDateTime2TimeConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(TIME_BERLIN, new LocalDateTime2TimeConverter().convert(LDT_MILLIS_BERLIN));
	}

}

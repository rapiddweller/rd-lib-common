/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalDateTime2DateConverter}.<br/><br/>
 * Created: 28.07.2022 14:08:24
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2DateConverterTest extends AbstractDateConverterTest {

	public LocalDateTime2DateConverterTest() {
		super(LocalDateTime2DateConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalDateTime2DateConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(DATE_BERLIN_DTZ, new LocalDateTime2DateConverter().convert(LDT_MILLIS_BERLIN));
	}

}

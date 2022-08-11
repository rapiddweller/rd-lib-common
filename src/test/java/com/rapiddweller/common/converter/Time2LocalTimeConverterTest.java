/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Time2LocalTimeConverter}.<br/><br/>
 * Created: 28.07.2022 15:08:47
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Time2LocalTimeConverterTest extends AbstractDateConverterTest {

	public Time2LocalTimeConverterTest() {
		super(Time2LocalTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Time2LocalTimeConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(LOCAL_TIME_MILLIS, new Time2LocalTimeConverter().convert(TIME));
	}

}

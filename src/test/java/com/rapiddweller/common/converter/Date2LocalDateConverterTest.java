/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Date2LocalDateConverter}.<br/><br/>
 * Created: 28.07.2022 13:55:11
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2LocalDateConverterTest extends AbstractDateConverterTest {

	public Date2LocalDateConverterTest() {
		super(Date2LocalDateConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Date2LocalDateConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(LOCAL_DATE, new Date2LocalDateConverter().convert(DATE_BERLIN_DTZ));
		assertEquals(LOCAL_DATE, new Date2LocalDateConverter().convert(DATE_LONDON_DTZ));
		assertEquals(LOCAL_DATE, new Date2LocalDateConverter().convert(DATE_CHICAGO_DTZ));
	}
}

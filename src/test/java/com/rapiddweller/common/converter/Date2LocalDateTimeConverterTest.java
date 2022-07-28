/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link Date2LocalDateTimeConverter}.<br/><br/>
 * Created: 28.07.2022 13:42:20
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2LocalDateTimeConverterTest extends AbstractDateConverterTest {

	public Date2LocalDateTimeConverterTest() {
		super(Date2LocalDateTimeConverter.class);
	}

	@Test
	public void test() {
		assertEquals(LDT_MILLIS, new Date2LocalDateTimeConverter().convert(DATE));
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link String2LocalTimeConverter}.<br/><br/>
 * Created: 28.07.2022 14:59:51
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class String2LocalTimeConverterTest extends AbstractDateConverterTest {

	public String2LocalTimeConverterTest() {
		super(String2LocalTimeConverter.class);
	}

	@Test
	public void test() {
		assertEquals(LOCAL_TIME_NANOS, new String2LocalTimeConverter().convert(LOCAL_TIME_STRING));
	}

}

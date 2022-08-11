/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalDate2DateConverter}.<br/><br/>
 * Created: 07.08.2022 21:51:49
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDate2DateConverterTest extends AbstractDateConverterTest {

	public LocalDate2DateConverterTest() {
		super(LocalDate2DateConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalDate2DateConverter().convert(null));
	}

	@Test
	public void test() {
		assertEquals(DATE_MIDNIGHT, new LocalDate2DateConverter().convert(LOCAL_DATE));
	}

}

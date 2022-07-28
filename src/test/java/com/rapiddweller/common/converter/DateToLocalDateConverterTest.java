/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link DateToLocalDateConverter}.<br/><br/>
 * Created: 28.07.2022 13:55:11
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DateToLocalDateConverterTest extends AbstractDateConverterTest {

	public DateToLocalDateConverterTest() {
		super(DateToLocalDateConverter.class);
	}

	@Test
	public void test() {
		assertEquals(LD, new DateToLocalDateConverter().convert(DATE));
	}
}

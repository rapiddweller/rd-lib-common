/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link Long2StringConverter}.<br/><br/>
 * Created: 03.02.2022 17:54:11
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Long2StringConverterTest {

	@Test
	public void test() {
		Long2StringConverter c = new Long2StringConverter();
		assertEquals(null, c.convert(null));
		assertEquals("20211101154530", c.convert(20211101154530L));
		assertEquals("-1", c.convert(-1L));
		assertEquals("0", c.convert(0L));
		assertEquals("1", c.convert(1L));
	}

}

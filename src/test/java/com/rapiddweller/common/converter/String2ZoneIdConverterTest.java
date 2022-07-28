/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link String2ZoneIdConverter}.<br/><br/>
 * Created: 28.07.2022 14:57:25
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class String2ZoneIdConverterTest {

	@Test
	public void test() {
		assertEquals(ZoneId.of("Europe/Berlin"), new String2ZoneIdConverter().convert("Europe/Berlin"));
	}

}

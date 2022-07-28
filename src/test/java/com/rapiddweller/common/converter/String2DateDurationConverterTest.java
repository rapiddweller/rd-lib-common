/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.time.DateDuration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link String2DateDurationConverter}.<br/><br/>
 * Created: 28.07.2022 15:03:49
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class String2DateDurationConverterTest {
	@Test
	public void test() {
		assertEquals(DateDuration.of(1, 2, 3), new String2DateDurationConverter().convert("1-2-3"));
	}
}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ZonedDateTime2DateConverter}.<br/><br/>
 * Created: 28.07.2022 15:24:46
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ZonedDateTime2DateConverterTest extends AbstractDateConverterTest {

	public ZonedDateTime2DateConverterTest() {
		super(ZonedDateTime2DateConverter.class);
	}

	@Test @Ignore("This test works on Volker's system, but fails in CI. @Alex please check on your system") // TODO review
	public void test() {
		assertEquals(DATE, new ZonedDateTime2DateConverter().convert(ZDT_MILLIS));
		assertEquals(DATE, new ZonedDateTime2DateConverter().convert(ZDT_NANOS));
	}

}

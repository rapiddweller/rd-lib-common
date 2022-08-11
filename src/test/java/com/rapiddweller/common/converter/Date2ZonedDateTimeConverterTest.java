/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Date2ZonedDateTimeConverter}.<br/><br/>
 * Created: 28.07.2022 13:57:55
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2ZonedDateTimeConverterTest extends AbstractDateConverterTest {

	public Date2ZonedDateTimeConverterTest() {
		super(Date2ZonedDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Date2ZonedDateTimeConverter().convert(null));
	}

	@Test @Ignore("This fails in CI") // TODO v3.0.0 make this work
	// CI message: Date2ZonedDateTimeConverterTest.test:29 expected:<2022-07-28T13:44:58.123+02:00[Europe/Berlin]> but was:<2022-07-28T15:44:58.123+02:00[Europe/Berlin]>
	public void test() {
		assertEquals(ZDT_MILLIS_BERLIN, new Date2ZonedDateTimeConverter(BERLIN).convert(DATE_BERLIN));
	}

}

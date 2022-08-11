/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.TimeUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the Date2OffsetDateTimeConverter.<br/><br/>
 * Created: 07.08.2022 21:20:06
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Date2OffsetDateTimeConverterTest extends AbstractDateConverterTest {

	public Date2OffsetDateTimeConverterTest() {
		super(Date2OffsetDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Date2OffsetDateTimeConverter().convert(null));
	}

	@Test(expected = ConfigurationError.class)
	public void testNullZone() {
		new Date2OffsetDateTimeConverter(null).convert(DATE_BERLIN_DTZ);
	}

	@Test
	public void testDefaultConstructor_berlin_date() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
				Date dateBerlin = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
				assertEqualOffsetDateTimes(ODT_MILLIS_BERLIN, new Date2OffsetDateTimeConverter().convert(dateBerlin));
			}
		);
	}

	@Test
	public void testZone_berlin() {
		assertEquals(ODT_MILLIS_BERLIN, new Date2OffsetDateTimeConverter(BERLIN).convert(DATE_BERLIN_DTZ));
	}

	@Test
	public void testZone_chicago() {
		assertEquals(ODT_MILLIS_CHICAGO, new Date2OffsetDateTimeConverter(CHICAGO).convert(DATE_CHICAGO_DTZ));
	}

}

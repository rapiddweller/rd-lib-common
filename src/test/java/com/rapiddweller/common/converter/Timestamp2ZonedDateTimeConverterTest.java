/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.TimeUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Timestamp2ZonedDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 21:40:35
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2ZonedDateTimeConverterTest extends AbstractDateConverterTest {

	public Timestamp2ZonedDateTimeConverterTest() {
		super(Timestamp2ZonedDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Timestamp2ZonedDateTimeConverter().convert(null));
	}

	@Test
	public void test() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Timestamp timestampBerlin = TimeUtil.timestamp(2022, 6, 28, 13, 44, 58, 123123123);
			assertEquals(ZDT_NANOS_BERLIN, new Timestamp2ZonedDateTimeConverter().convert(timestampBerlin));
		});
	}

}

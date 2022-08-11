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
 * Tests the {@link Timestamp2OffsetDateTimeConverter}.<br/><br/>
 * Created: 07.08.2022 21:37:40
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2OffsetDateTimeConverterTest extends AbstractDateConverterTest {

	public Timestamp2OffsetDateTimeConverterTest() {
		super(Timestamp2OffsetDateTimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Timestamp2OffsetDateTimeConverter().convert(null));
	}

	@Test
	public void testBerlin() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Timestamp timestampBerlin = TimeUtil.timestamp(2022, 6, 28, 13, 44, 58, 123123123);
			assertEquals(ODT_NANOS_BERLIN, new Timestamp2OffsetDateTimeConverter().convert(timestampBerlin));
		});
	}

	@Test
	public void testChicago() {
		JavaTimeUtil.runInZone(CHICAGO, () -> {
			Timestamp timestampChicago = TimeUtil.timestamp(2022, 6, 28, 6, 44, 58, 123123123);
			assertEquals(ODT_NANOS_CHICAGO, new Timestamp2OffsetDateTimeConverter().convert(timestampChicago));
		});
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

import java.util.Date;

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

	@Test
	public void testBerlinDate() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Date dateBerlin = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
			assertEquals(ZDT_MILLIS_BERLIN, new Date2ZonedDateTimeConverter(BERLIN).convert(dateBerlin));
		});
	}

	@Test
	public void testChicagoDate() {
		JavaTimeUtil.runInZone(BERLIN, () -> {
			Date dateChicagoInDTZ = TimeUtil.date(2022, 6, 28, 13, 44, 58, 123);
			assertEquals(ZDT_MILLIS_CHICAGO, new Date2ZonedDateTimeConverter(CHICAGO).convert(dateChicagoInDTZ));
		});
	}

}

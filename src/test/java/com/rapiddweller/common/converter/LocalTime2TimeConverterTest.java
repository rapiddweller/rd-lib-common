/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.Converter;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link LocalTime2TimeConverter}.<br/><br/>
 * Created: 28.07.2022 14:11:33
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalTime2TimeConverterTest extends AbstractDateConverterTest {

	public LocalTime2TimeConverterTest() {
		super(LocalTime2TimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new LocalTime2TimeConverter().convert(null));
	}

	@Test
	public void test() {
		LocalTime2TimeConverter converter = new LocalTime2TimeConverter();
		System.out.println(LOCAL_TIME_MILLIS + " " + LOCAL_TIME_MILLIS.toNanoOfDay() / 1000000);
		System.out.println(TIME + " " + TIME.getTime());
		Time tmp = converter.convert(LOCAL_TIME_MILLIS);
		System.out.println(tmp + " " + tmp.getTime());
		assertEquals(TIME, tmp);
		assertEquals(TIME, converter.convert(LOCAL_TIME_NANOS));
	}

}

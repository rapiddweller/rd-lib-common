/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.Converter;
import org.junit.Test;

import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

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
	public void test() {
		assertEquals(ZDT_MILLIS, new Date2ZonedDateTimeConverter(ZoneId.of("Europe/Berlin")).convert(DATE));
	}

}

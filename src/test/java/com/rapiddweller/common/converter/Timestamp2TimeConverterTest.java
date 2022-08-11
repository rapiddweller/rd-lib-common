/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link Timestamp2TimeConverter}.<br/><br/>
 * Created: 07.08.2022 22:02:27
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Timestamp2TimeConverterTest extends AbstractDateConverterTest {

	public Timestamp2TimeConverterTest() {
		super(Timestamp2TimeConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new Timestamp2TimeConverter().convert(null));
	}

	@Test
	public void testBerlin() {
		assertEqualTimes(TIME_BERLIN, new Timestamp2TimeConverter().convert(TIMESTAMP_BERLIN));
	}

	@Test
	public void testChicago() {
		assertEqualTimes(TIME_CHICAGO, new Timestamp2TimeConverter().convert(TIMESTAMP_CHICAGO));
	}

}

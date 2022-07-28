/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link LocalDateTime2TimestampConverter}.<br/><br/>
 * Created: 28.07.2022 14:51:34
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateTime2TimestampConverterTest extends AbstractDateConverterTest {

	public LocalDateTime2TimestampConverterTest() {
		super(LocalDateTime2TimestampConverter.class);
	}

	@Test
	public void test() {
		assertEquals(TIMESTAMP, new LocalDateTime2TimestampConverter().convert(LDT_NANOS));
	}

}

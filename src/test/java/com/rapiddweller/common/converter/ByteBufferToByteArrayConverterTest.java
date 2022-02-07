/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests the {@link ByteBufferToByteArrayConverter}.<br/><br/>
 * Created: 07.02.2022 14:05:33
 * @author Volker Bergmann
 * @since 0.0.3
 */
public class ByteBufferToByteArrayConverterTest {

	@Test
	public void test() {
		byte[] array = {5, 6};
		ByteBufferToByteArrayConverter converter = new ByteBufferToByteArrayConverter();
		assertArrayEquals(array, converter.convert(ByteBuffer.wrap(array)));
	}

}

/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

import java.nio.ByteBuffer;

/**
 * Extracts the data of a {@link ByteBuffer} as array of bytes.<br/><br/>
 * Created: 07.02.2022 14:03:37
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ByteBufferToByteArrayConverter extends ThreadSafeConverter<ByteBuffer, byte[]> {

	public ByteBufferToByteArrayConverter() {
		super(ByteBuffer.class, byte[].class);
	}

	@Override
	public byte[] convert(ByteBuffer target) throws ConversionException {
		return target.array();
	}

}

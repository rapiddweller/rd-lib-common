package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class ByteArrayToBase64ConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals("QUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFB", (new ByteArrayToBase64Converter()).convert(
                new byte[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65}));
    }
}


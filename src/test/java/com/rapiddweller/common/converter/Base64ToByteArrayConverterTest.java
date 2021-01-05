package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class Base64ToByteArrayConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals(0, (new Base64ToByteArrayConverter()).convert("").length);
    }
}


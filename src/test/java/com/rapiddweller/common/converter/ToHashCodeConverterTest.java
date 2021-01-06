package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class ToHashCodeConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals(-93515242, (new ToHashCodeConverter()).convert("sourceValue").intValue());
        assertEquals(0, (new ToHashCodeConverter()).convert(null).intValue());
    }
}


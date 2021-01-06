package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class CharArray2StringConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals("AAAAAAAA",
                (new CharArray2StringConverter()).convert(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A'}));
    }
}


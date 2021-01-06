package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class Class2StringConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        Class<?> clazz = Object.class;
        assertEquals("java.lang.Object", (new Class2StringConverter()).convert(clazz));
    }
}


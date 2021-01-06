package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import org.junit.Test;

public class Boolean2NumberConverterTest {
    @Test
    public void testConstructor() {
        Boolean2NumberConverter<Integer> actualBoolean2NumberConverter = new Boolean2NumberConverter<>(Integer.class);
        assertTrue(actualBoolean2NumberConverter.isParallelizable());
        Converter<Integer, Integer> converter = actualBoolean2NumberConverter.realConverter;
        Converter<Integer, Integer> converter1 = ((NumberToNumberConverter<Integer, Integer>) converter).realConverter;
        assertTrue(converter.isThreadSafe());
        Class<Integer> actualTargetType = converter1.getTargetType();
        assertSame(actualBoolean2NumberConverter.getTargetType(), actualTargetType);
        Class<Integer> expectedSourceType = converter.getSourceType();
        assertSame(expectedSourceType, converter1.getSourceType());
    }

    @Test
    public void testConvert() throws ConversionException {
        assertEquals(1, (new Boolean2NumberConverter<>(Integer.class)).convert(true).intValue());
        assertEquals(0, (new Boolean2NumberConverter<>(Integer.class)).convert(false).intValue());
    }

    @Test
    public void testGetTargetType() {
        Class<Integer> actualTargetType = (new Boolean2NumberConverter<>(Integer.class)).getTargetType();
        assertSame(Integer.class, actualTargetType);
    }
}


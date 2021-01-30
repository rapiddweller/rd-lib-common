package com.rapiddweller.common.converter;

import static org.junit.Assert.assertNotNull;

import com.rapiddweller.common.ConversionException;

import java.sql.Time;

import org.junit.Test;

public class Time2StringConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        Time target = new Time(10L);
        assertNotNull((new Time2StringConverter()).convert(target));
    }

    @Test
    public void testConvert2() throws ConversionException {
        Time target = new Time(0L);
        assertNotNull((new Time2StringConverter()).convert(target));
    }
}


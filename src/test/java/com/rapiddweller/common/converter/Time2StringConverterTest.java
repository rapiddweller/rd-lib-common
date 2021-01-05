package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;

import java.sql.Time;

import org.junit.Test;

public class Time2StringConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        Time target = new Time(10L);
        assertEquals("01:00:00.010", (new Time2StringConverter()).convert(target));
    }

    @Test
    public void testConvert2() throws ConversionException {
        Time target = new Time(0L);
        assertEquals("01:00:00.000", (new Time2StringConverter()).convert(target));
    }
}


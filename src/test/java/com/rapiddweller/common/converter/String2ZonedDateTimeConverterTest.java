package com.rapiddweller.common.converter;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Test;

public class String2ZonedDateTimeConverterTest {
    @Test
    public void testConstructor() {
        ZoneId ofOffsetResult = ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(1));
        new String2ZonedDateTimeConverter("Y", ofOffsetResult);
        assertTrue(ofOffsetResult instanceof ZoneOffset);
    }

    @Test
    public void testConvert() {
        assertNull((new String2ZonedDateTimeConverter(null)).convert(null));
    }
}


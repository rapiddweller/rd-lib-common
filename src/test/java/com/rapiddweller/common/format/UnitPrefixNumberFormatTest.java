package com.rapiddweller.common.format;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitPrefixNumberFormatTest {
    @Test
    public void testFormat() {
        assertEquals("10", (new UnitPrefixNumberFormat()).format(10.0));
        assertEquals("1M", (new UnitPrefixNumberFormat()).format(1000000.0));
        assertEquals("1K", (new UnitPrefixNumberFormat()).format(1000.0));
    }
}


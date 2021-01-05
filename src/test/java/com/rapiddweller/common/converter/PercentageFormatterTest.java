package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class PercentageFormatterTest {
    @Test
    public void testFormatChange() {
        assertEquals("+1,000.0%", PercentageFormatter.formatChange(10.0));
        assertEquals("0.0%", PercentageFormatter.formatChange(0.0));
    }

    @Test
    public void testFormat() {
        assertEquals("+1,000.0%", PercentageFormatter.format(10.0, 1, true));
        assertEquals("0.0%", PercentageFormatter.format(0.0, 1, true));
        assertEquals("1,000.0%", PercentageFormatter.format(10.0, 1, false));
        assertEquals("+1,000.0%", PercentageFormatter.format(10.0, 1, true, new Locale("en")));
        assertEquals("0.0%", PercentageFormatter.format(0.0, 1, true, new Locale("en")));
        assertEquals("1,000.0%", PercentageFormatter.format(10.0, 1, false, new Locale("en")));
        assertEquals("0%", PercentageFormatter.format(0.0, 0, true, new Locale("en")));
    }
}


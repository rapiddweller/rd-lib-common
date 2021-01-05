package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

public class HFTest {
    @Test
    public void testFormatPctChange100() {
        assertEquals("+10.0%", HF.formatPctChange100(10.0));
        assertEquals("0.0%", HF.formatPctChange100(0.0));
        assertEquals("NaN", HF.formatPctChange100(Double.NaN));
    }

    @Test
    public void testFormatPct100() {
        assertEquals("10.0%", HF.formatPct100(10.0));
        assertEquals("NaN", HF.formatPct100(Double.NaN));
        assertEquals("Pattern10%", HF.formatPct100(10.0, "Pattern"));
        assertEquals("NaN", HF.formatPct100(Double.NaN, "Pattern"));
    }

    @Test
    public void testFormat() {
        assertEquals("10", HF.format(10.0));
        assertEquals("NaN", HF.format(Double.NaN));
        assertEquals("01:01:00", HF.format(LocalTime.of(1, 1)));
    }
}


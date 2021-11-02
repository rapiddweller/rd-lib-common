package com.rapiddweller.common;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

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

  @Test
  public void testFormatDuration() {
    assertEquals("10 hours", HF.formatDurationSec(36000));
    assertEquals("5 hours 10 minutes", HF.formatDurationSec(18659));
    assertEquals("1 hour 10 minutes", HF.formatDurationSec(4200));
    assertEquals("1 hour", HF.formatDurationSec(3600));
    assertEquals("50 minutes", HF.formatDurationSec(3000));
    assertEquals("5 minutes 30 seconds", HF.formatDurationSec(330));
    assertEquals("1 minute 30 seconds", HF.formatDurationSec(90));
    assertEquals("1 minute", HF.formatDurationSec(60));
    assertEquals("59 seconds", HF.formatDurationSec(59));
    assertEquals("2 seconds", HF.formatDurationSec(2));
    assertEquals("1 second", HF.formatDurationSec(1));
    assertEquals("Less than a second", HF.formatDurationSec(0));
  }

}


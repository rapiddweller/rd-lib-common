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
  public void testFormatDouble() {
    assertEquals("10", HF.format(10.0));
    assertEquals("NaN", HF.format(Double.NaN));
    assertEquals("01:01:00", HF.format(LocalTime.of(1, 1)));
  }

  @Test
  public void testFormatLong() {
    assertEquals("10", HF.format(10L));
    assertEquals("1,234", HF.format(1234L));
    assertEquals("1,234,567", HF.format(1234567L));
  }

  @Test
  public void testFormatLocalTime() {
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

  @Test
  public void testPluralize() {
    assertEquals("1 degree", HF.pluralize(1, "degree"));
    assertEquals("-5 degrees", HF.pluralize(-5, "degree"));
    assertEquals("-10 DEGREES", HF.pluralize(-10, "DEGREE"));

    assertEquals("0 countries", HF.pluralize(0, "country"));
    assertEquals("1 country", HF.pluralize(1, "country"));
    assertEquals("2 countries", HF.pluralize(2, "country"));

    assertEquals("2 kisses", HF.pluralize(2, "kiss"));
    assertEquals("2 KISSES", HF.pluralize(2, "KISS"));
  }

  @Test
  public void testFormatByteSize() {
    assertEquals("12 bytes", HF.formatByteSize(12L));
    assertEquals("123,456 bytes", HF.formatByteSize(123456L));
    assertEquals("123,456 KB", HF.formatByteSize(123456L * 1024));
    assertEquals("123,456 MB", HF.formatByteSize(123456L * 1024 * 1024));
  }

}


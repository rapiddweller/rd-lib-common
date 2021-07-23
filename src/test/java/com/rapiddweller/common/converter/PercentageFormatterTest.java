package com.rapiddweller.common.converter;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertNotNull;

public class PercentageFormatterTest {
  @Test
  public void testFormatChange() {
    assertNotNull(PercentageFormatter.formatChange(10.0));
    assertNotNull(PercentageFormatter.formatChange(0.0));
  }

  @Test
  public void testFormat() {
    assertNotNull(PercentageFormatter.format(10.0, 1, true));
    assertNotNull(PercentageFormatter.format(0.0, 1, true));
    assertNotNull(PercentageFormatter.format(10.0, 1, false));
    assertNotNull(PercentageFormatter.format(10.0, 1, true, new Locale("en")));
    assertNotNull(PercentageFormatter.format(0.0, 1, true, new Locale("en")));
    assertNotNull(PercentageFormatter.format(10.0, 1, false, new Locale("en")));
    assertNotNull(PercentageFormatter.format(0.0, 0, true, new Locale("en")));
  }
}


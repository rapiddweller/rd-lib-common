package com.rapiddweller.common.format;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class ConcurrentDateFormatTest {
  @Test
  public void testConstructor() {
    ConcurrentDateFormat actualConcurrentDateFormat = new ConcurrentDateFormat("Pattern");
    assertNull(actualConcurrentDateFormat.getCalendar());
    assertNull(actualConcurrentDateFormat.getNumberFormat());
  }
}


package com.rapiddweller.common.format;

import org.junit.Test;

import java.text.ParsePosition;

import static org.junit.Assert.assertNull;

public class ConcurrentDecimalFormatTest {
  @Test
  public void testParseObject() {
    ConcurrentDecimalFormat concurrentDecimalFormat = new ConcurrentDecimalFormat("Pattern");
    assertNull(concurrentDecimalFormat.parseObject("Source", new ParsePosition(1)));
  }

  @Test
  public void testParseObject2() {
    ConcurrentDecimalFormat concurrentDecimalFormat = new ConcurrentDecimalFormat("Pattern");
    ParsePosition parsePosition = new ParsePosition(1);
    parsePosition.setIndex(1);
    assertNull(concurrentDecimalFormat.parseObject("Source", parsePosition));
  }
}


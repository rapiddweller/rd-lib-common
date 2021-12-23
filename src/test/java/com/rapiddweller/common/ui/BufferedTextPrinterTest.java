package com.rapiddweller.common.ui;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BufferedTextPrinterTest {
  @Test
  public void testConstructor() {
    assertEquals("", (new BufferedTextPrinter()).toString());
  }

  @Test
  public void testPrintLines() {
    BufferedTextPrinter bufferedInfoPrinter = new BufferedTextPrinter();
    bufferedInfoPrinter.printStd("owner", "foo", "foo", "foo");
    assertEquals("owner\nfoo\nfoo\nfoo\n", bufferedInfoPrinter.toString());
  }

  @Test
  public void testClear() {
    BufferedTextPrinter bufferedInfoPrinter = new BufferedTextPrinter();
    bufferedInfoPrinter.clear();
    assertEquals("", bufferedInfoPrinter.toString());
  }

  @Test
  public void testToString() {
    assertEquals("", (new BufferedTextPrinter()).toString());
  }
}


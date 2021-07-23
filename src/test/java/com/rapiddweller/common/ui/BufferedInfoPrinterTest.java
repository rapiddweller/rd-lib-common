package com.rapiddweller.common.ui;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BufferedInfoPrinterTest {
  @Test
  public void testConstructor() {
    assertEquals("", (new BufferedInfoPrinter()).toString());
  }

  @Test
  public void testPrintLines() {
    BufferedInfoPrinter bufferedInfoPrinter = new BufferedInfoPrinter();
    bufferedInfoPrinter.printLines("owner", "foo", "foo", "foo");
    assertEquals("owner\nfoo\nfoo\nfoo\n", bufferedInfoPrinter.toString());
  }

  @Test
  public void testClear() {
    BufferedInfoPrinter bufferedInfoPrinter = new BufferedInfoPrinter();
    bufferedInfoPrinter.clear();
    assertEquals("", bufferedInfoPrinter.toString());
  }

  @Test
  public void testToString() {
    assertEquals("", (new BufferedInfoPrinter()).toString());
  }
}


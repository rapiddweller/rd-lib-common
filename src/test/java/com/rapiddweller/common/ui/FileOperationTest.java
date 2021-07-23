package com.rapiddweller.common.ui;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileOperationTest {
  @Test
  public void testValueOf() {
    assertEquals(FileOperation.CUSTOM, FileOperation.valueOf("CUSTOM"));
  }

  @Test
  public void testValues() {
    assertEquals(3, FileOperation.values().length);
  }
}


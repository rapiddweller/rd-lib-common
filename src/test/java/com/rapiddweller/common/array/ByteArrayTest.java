package com.rapiddweller.common.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteArrayTest {
  @Test
  public void testConstructor() {
    assertEquals(10, (new ByteArray()).getBytes().length);
    assertEquals(1, (new ByteArray(1)).getBytes().length);
  }
}


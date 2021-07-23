package com.rapiddweller.common.accessor;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class FallbackAccessorTest {
  @Test
  public void testGetValue() {
    assertNull((new FallbackAccessor<>()).getValue("target"));
  }
}


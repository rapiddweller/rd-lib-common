package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LocalSequenceProviderTest {
  @Test
  public void testSetCached() {
    LocalSequenceProvider instance = LocalSequenceProvider.getInstance("foo.txt");
    instance.setCached(true);
    assertTrue(instance.isCached());
  }
}


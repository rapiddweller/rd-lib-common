package com.rapiddweller.common.accessor;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccessorMapAccessorTest {
  @Test
  public void testGetDependencies() {
    assertTrue((new AccessorMapAccessor(new HashMap<>(), "key")).getDependencies().isEmpty());
  }

  @Test
  public void testGetAccessor() {
    assertNull((new AccessorMapAccessor(new HashMap<>(), "key")).getAccessor());
  }
}


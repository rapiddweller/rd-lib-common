package com.rapiddweller.common.collection;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class CaseSensitiveOrderedNameMapTest {
  @Test
  public void testConstructor() {
    assertTrue((new CaseSensitiveOrderedNameMap<>()).isEmpty());
    assertTrue((new CaseSensitiveOrderedNameMap<>(new HashMap<>())).isEmpty());
  }
}


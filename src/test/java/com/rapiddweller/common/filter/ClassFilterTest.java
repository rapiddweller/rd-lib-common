package com.rapiddweller.common.filter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassFilterTest {
  @Test
  public void testAccept() {
    assertTrue((new ClassFilter<>(Object.class, true)).accept("candidate"));
    assertFalse((new ClassFilter<>(Object.class, false)).accept("candidate"));
    assertFalse((new ClassFilter<>(Object.class, true)).accept(null));
  }
}


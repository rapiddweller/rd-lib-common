package com.rapiddweller.common.filter;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AcceptAllFilterTest {
  @Test
  public void testAccept() {
    assertTrue((new AcceptAllFilter<>()).accept("candidate"));
  }
}


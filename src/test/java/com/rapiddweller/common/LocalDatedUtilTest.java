package com.rapiddweller.common;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;

public class LocalDatedUtilTest {
  @Test
  public void testSoonestFutureElement() {
    assertNull(LocalDatedUtil.soonestFutureElement(new ArrayList<>()));
  }
}


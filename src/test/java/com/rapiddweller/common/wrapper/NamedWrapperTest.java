package com.rapiddweller.common.wrapper;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NamedWrapperTest {
  @Test
  public void testSetWrapped() {
    NamedWrapper<Object> namedWrapper = new NamedWrapper<>("Name", "wrapped");
    namedWrapper.setWrapped("wrapped");
    assertTrue(namedWrapper.getWrapped() instanceof String);
  }
}


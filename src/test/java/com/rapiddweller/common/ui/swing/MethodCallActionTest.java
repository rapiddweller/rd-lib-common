package com.rapiddweller.common.ui.swing;

import org.junit.Test;

import javax.swing.plaf.metal.MetalIconFactory;

import static org.junit.Assert.assertEquals;

public class MethodCallActionTest {
  @Test
  public void testConstructor() {
    Object[] objectArray = new Object[] {"foo", "foo", "foo"};
    new MethodCallAction("Label", MetalIconFactory.getHorizontalSliderThumbIcon(), "target", "Method Name",
        objectArray);
    assertEquals(3, objectArray.length);
  }
}


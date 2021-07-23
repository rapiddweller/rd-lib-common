package com.rapiddweller.common.ui.swing;

import org.junit.Test;

import javax.swing.plaf.metal.MetalIconFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConsumerActionTest {
  @Test
  public void testConstructor() {
    ConsumerAction<Object> actualConsumerAction = new ConsumerAction<>("Label",
        MetalIconFactory.getHorizontalSliderThumbIcon(), "object", null);
    assertEquals(2, actualConsumerAction.getKeys().length);
    assertTrue(actualConsumerAction.isEnabled());
    assertEquals(0, actualConsumerAction.getPropertyChangeListeners().length);
  }
}


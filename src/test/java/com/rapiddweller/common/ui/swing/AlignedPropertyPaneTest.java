package com.rapiddweller.common.ui.swing;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class AlignedPropertyPaneTest {
  @Test
  public void testConstructor() {
    assertNull((new AlignedPropertyPane<Object>(1, 1, "bean", null)).i18n);
  }
}


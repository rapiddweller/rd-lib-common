package com.rapiddweller.common.ui.swing;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class AlignedPaneTest {

  @Test
  public void testConstructor4() {
    assertThrows(IllegalArgumentException.class, () -> new AlignedPane(18, 1));
  }
}


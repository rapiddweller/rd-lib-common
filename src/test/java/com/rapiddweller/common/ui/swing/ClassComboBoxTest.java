package com.rapiddweller.common.ui.swing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassComboBoxTest {
  @Test
  public void testConstructor() {
    Class<?> forNameResult = Object.class;
    Class<?> forNameResult1 = Object.class;
    Class<?>[] classArray = new Class[] {forNameResult, forNameResult1, Object.class};
    new ClassComboBox<>(classArray);
    assertEquals(3, classArray.length);
  }
}


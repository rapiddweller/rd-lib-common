package com.rapiddweller.common.ui.swing;

import org.junit.Test;

import javax.swing.Icon;
import java.awt.Color;
import java.awt.Font;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TextIconTest {

  @Test
  public void testConstructor3() {
    Color foreground = Color.getHSBColor(10.0f, 10.0f, 10.0f);
    Color background = Color.getHSBColor(10.0f, 10.0f, 10.0f);
    TextIcon actualTextIcon = new TextIcon("Text", foreground, background, true, Font.decode("Str"));
    assertEquals(26, actualTextIcon.getIconWidth());
    assertEquals(26, actualTextIcon.getIconHeight());
  }

  @Test
  public void testConstructor4() {
    Color foreground = Color.getHSBColor(10.0f, 10.0f, 10.0f);
    Color background = Color.getHSBColor(10.0f, 10.0f, 10.0f);
    TextIcon actualTextIcon = new TextIcon("Text", foreground, background, false, Font.decode("Str"));
    assertEquals(26, actualTextIcon.getIconWidth());
    assertEquals(14, actualTextIcon.getIconHeight());
  }

  @Test
  public void testSetIconHeight() {
    TextIcon textIcon = new TextIcon("Text");
    textIcon.setIconHeight(1);
    assertEquals(1, textIcon.getIconHeight());
  }

  @Test
  public void testSetIconWidth() {
    TextIcon textIcon = new TextIcon("Text");
    textIcon.setIconWidth(1);
    assertEquals(1, textIcon.getIconWidth());
  }

  @Test
  public void testWithSize() {
    TextIcon textIcon = new TextIcon("Text");
    Icon actualWithSizeResult = textIcon.withSize(3);
    assertSame(textIcon, actualWithSizeResult);
    assertEquals(3, actualWithSizeResult.getIconWidth());
    assertEquals(3, actualWithSizeResult.getIconHeight());
  }
}


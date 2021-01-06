package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.awt.Color;
import java.awt.Font;
import javax.swing.plaf.metal.MetalIconFactory;

import org.junit.Test;

public class TextOverlayIconTest {
    @Test
    public void testConstructor() {
        TextOverlayIcon actualTextOverlayIcon = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(),
                "Text");
        assertEquals(26, actualTextOverlayIcon.getIconWidth());
        assertEquals(16, actualTextOverlayIcon.getIconHeight());
    }

    @Test
    public void testConstructor2() {
        TextOverlayIcon actualTextOverlayIcon = new TextOverlayIcon(
                new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text"), "Text");
        assertEquals(26, actualTextOverlayIcon.getIconWidth());
        assertEquals(16, actualTextOverlayIcon.getIconHeight());
    }

    @Test
    public void testConstructor3() {
        Color color = Color.getHSBColor(10.0f, 10.0f, 10.0f);
        TextOverlayIcon actualTextOverlayIcon = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text",
                color);
        assertEquals(26, actualTextOverlayIcon.getIconWidth());
        assertEquals(16, actualTextOverlayIcon.getIconHeight());
    }

    @Test
    public void testConstructor4() {
        TextOverlayIcon background = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text");
        TextOverlayIcon actualTextOverlayIcon = new TextOverlayIcon(background, "Text",
                Color.getHSBColor(10.0f, 10.0f, 10.0f));
        assertEquals(26, actualTextOverlayIcon.getIconWidth());
        assertEquals(16, actualTextOverlayIcon.getIconHeight());
    }

    @Test
    public void testConstructor5() {
        Color color = Color.getHSBColor(10.0f, 10.0f, 10.0f);
        Font font = Font.decode("Str");
        TextOverlayIcon actualTextOverlayIcon = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text",
                color, font);
        assertEquals(26, actualTextOverlayIcon.getIconWidth());
        assertEquals(16, actualTextOverlayIcon.getIconHeight());
    }

    @Test
    public void testWithSize() {
        TextOverlayIcon textOverlayIcon = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text");
        TextOverlayIcon actualWithSizeResult = textOverlayIcon.withSize(3);
        assertSame(textOverlayIcon, actualWithSizeResult);
        assertEquals(3, actualWithSizeResult.getIconWidth());
        assertEquals(3, actualWithSizeResult.getIconHeight());
    }

    @Test
    public void testWithSize2() {
        TextOverlayIcon textOverlayIcon = new TextOverlayIcon(
                new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text"), "Text");
        TextOverlayIcon actualWithSizeResult = textOverlayIcon.withSize(3);
        assertSame(textOverlayIcon, actualWithSizeResult);
        assertEquals(3, actualWithSizeResult.getIconWidth());
        assertEquals(3, actualWithSizeResult.getIconHeight());
    }

    @Test
    public void testSetIconWidth() {
        TextOverlayIcon textOverlayIcon = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text");
        textOverlayIcon.setIconWidth(1);
        assertEquals(1, textOverlayIcon.getIconWidth());
    }

    @Test
    public void testSetIconWidth2() {
        TextOverlayIcon textOverlayIcon = new TextOverlayIcon(
                new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text"), "Text");
        textOverlayIcon.setIconWidth(1);
        assertEquals(1, textOverlayIcon.getIconWidth());
    }

    @Test
    public void testSetIconHeight() {
        TextOverlayIcon textOverlayIcon = new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text");
        textOverlayIcon.setIconHeight(1);
        assertEquals(1, textOverlayIcon.getIconHeight());
    }

    @Test
    public void testSetIconHeight2() {
        TextOverlayIcon textOverlayIcon = new TextOverlayIcon(
                new TextOverlayIcon(MetalIconFactory.getHorizontalSliderThumbIcon(), "Text"), "Text");
        textOverlayIcon.setIconHeight(1);
        assertEquals(1, textOverlayIcon.getIconHeight());
    }
}


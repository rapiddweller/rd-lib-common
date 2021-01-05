package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AlignedPropertyPaneTest {
    @Test
    public void testConstructor() {
        assertNull((new AlignedPropertyPane<Object>(1, 1, "bean", null)).i18n);
    }
}


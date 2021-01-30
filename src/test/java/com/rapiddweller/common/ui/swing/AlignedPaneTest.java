package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class AlignedPaneTest {

    @Test
    public void testConstructor4() {
        assertThrows(IllegalArgumentException.class, () -> new AlignedPane(18, 1));
    }
}


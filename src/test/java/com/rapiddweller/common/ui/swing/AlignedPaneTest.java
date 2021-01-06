package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.awt.HeadlessException;

import org.junit.Test;

public class AlignedPaneTest {

    @Test
    public void testConstructor4() {
        assertThrows(IllegalArgumentException.class, () -> new AlignedPane(18, 1));
    }
}


package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class SwingUtilTest {
    @Ignore
    @Test
    public void testIsLookAndFeelNative() {
        assertTrue(SwingUtil.isLookAndFeelNative());
    }
    @Ignore
    @Test
    public void testGetHardDriveIcon() {
        assertNull(SwingUtil.getHardDriveIcon());
    }
}


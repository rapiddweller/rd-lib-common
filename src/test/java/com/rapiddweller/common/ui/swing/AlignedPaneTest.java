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
    public void testConstructor() throws HeadlessException {
        AlignedPane actualAlignedPane = new AlignedPane();
        assertNull(actualAlignedPane.getName());
        assertTrue(actualAlignedPane.getFocusTraversalKeysEnabled());
        assertTrue(actualAlignedPane.getForeground() instanceof javax.swing.plaf.ColorUIResource);
        assertFalse(actualAlignedPane.getIgnoreRepaint());
        assertTrue(actualAlignedPane.getLayout() instanceof java.awt.GridBagLayout);
        assertTrue(actualAlignedPane.isOpaque());
        assertTrue(actualAlignedPane.getFont() instanceof javax.swing.plaf.FontUIResource);
        assertEquals(0, actualAlignedPane.getWidth());
        assertEquals(0, actualAlignedPane.getMouseMotionListeners().length);
        assertFalse(actualAlignedPane.isFocusTraversalPolicyProvider());
        assertEquals(0, actualAlignedPane.getVetoableChangeListeners().length);
        assertTrue(actualAlignedPane.getBackground() instanceof javax.swing.plaf.ColorUIResource);
        assertTrue(actualAlignedPane.getColorModel() instanceof java.awt.image.DirectColorModel);
        assertNull(actualAlignedPane.getMousePosition());
        assertEquals(0, actualAlignedPane.getMouseListeners().length);
        assertEquals(0, actualAlignedPane.getKeyListeners().length);
        assertNull(actualAlignedPane.getTopLevelAncestor());
        assertNull(actualAlignedPane.getGraphicsConfiguration());
        assertEquals(0, actualAlignedPane.getPropertyChangeListeners().length);
        assertFalse(actualAlignedPane.isPreferredSizeSet());
        assertFalse(actualAlignedPane.getAutoscrolls());
        assertFalse(actualAlignedPane.isFocusTraversalPolicySet());
        assertEquals(0.5f, actualAlignedPane.getAlignmentY(), 0.0f);
        assertNull(actualAlignedPane.getToolTipText());
        assertNull(actualAlignedPane.getInputVerifier());
        assertNull(actualAlignedPane.getFocusCycleRootAncestor());
        assertNull(actualAlignedPane.getRootPane());
        assertTrue(actualAlignedPane.getVerifyInputWhenFocusTarget());
        assertEquals(0, actualAlignedPane.getHierarchyListeners().length);
        assertFalse(actualAlignedPane.isLightweight());
        assertNull(actualAlignedPane.getDropTarget());
        assertFalse(actualAlignedPane.isFocusOwner());
        assertEquals(0, actualAlignedPane.getY());
        assertNull(actualAlignedPane.getInputMethodRequests());
        assertEquals(0, actualAlignedPane.getHeight());
        assertFalse(actualAlignedPane.isPaintingForPrint());
        assertFalse(actualAlignedPane.isFocusCycleRoot());
        assertFalse(actualAlignedPane.isDisplayable());
        assertTrue(actualAlignedPane.getUI() instanceof javax.swing.plaf.basic.BasicPanelUI);
        assertFalse(actualAlignedPane.isValidateRoot());
    }

    @Test
    public void testConstructor4() {
        assertThrows(IllegalArgumentException.class, () -> new AlignedPane(18, 1));
    }
}


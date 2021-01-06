package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ui.FileOperation;
import com.rapiddweller.common.ui.FileTypeSupport;
import org.junit.Test;

public class SwingFileChooserTest {
    @Test
    public void testConstructor() {
        SwingFileChooser actualSwingFileChooser = new SwingFileChooser(FileTypeSupport.filesOnly, FileOperation.OPEN);
        assertNull(actualSwingFileChooser.getName());
        assertEquals(0, actualSwingFileChooser.getMouseListeners().length);
        assertNull(actualSwingFileChooser.getGraphicsConfiguration());
        assertEquals(4, actualSwingFileChooser.getPropertyChangeListeners().length);
        assertFalse(actualSwingFileChooser.isPreferredSizeSet());
        assertEquals(0, actualSwingFileChooser.getWidth());
        assertEquals(0, actualSwingFileChooser.getMouseMotionListeners().length);
        assertEquals(0, actualSwingFileChooser.getVetoableChangeListeners().length);
        assertNull(actualSwingFileChooser.getDialogTitle());
        assertNull(actualSwingFileChooser.getInputVerifier());
        assertFalse(actualSwingFileChooser.getAutoscrolls());
        assertNull(actualSwingFileChooser.getToolTipText());
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicySet());
        assertFalse(actualSwingFileChooser.isDirectorySelectionEnabled());
        assertEquals(0, actualSwingFileChooser.getDialogType());
        assertNull(actualSwingFileChooser.getTopLevelAncestor());
        assertEquals(0, actualSwingFileChooser.getKeyListeners().length);
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicyProvider());
        assertEquals(0, actualSwingFileChooser.getSelectedFiles().length);
        assertTrue(actualSwingFileChooser.isFileSelectionEnabled());
        assertNull(actualSwingFileChooser.getAccessory());
        assertEquals(0.5f, actualSwingFileChooser.getAlignmentY(), 0.0f);
        assertNull(actualSwingFileChooser.getFocusCycleRootAncestor());
        assertEquals(1, actualSwingFileChooser.getChoosableFileFilters().length);
        assertNull(actualSwingFileChooser.getRootPane());
        assertNull(actualSwingFileChooser.getInputMethodRequests());
        assertTrue(actualSwingFileChooser.getVerifyInputWhenFocusTarget());
        assertTrue(actualSwingFileChooser.isAcceptAllFileFilterUsed());
        assertEquals(1, actualSwingFileChooser.getHierarchyListeners().length);
        assertFalse(actualSwingFileChooser.isLightweight());
        assertEquals(0, actualSwingFileChooser.getY());
        assertFalse(actualSwingFileChooser.isFocusOwner());
        assertEquals(0, actualSwingFileChooser.getHeight());
        assertFalse(actualSwingFileChooser.isPaintingForPrint());
        assertFalse(actualSwingFileChooser.isDisplayable());
        assertFalse(actualSwingFileChooser.isFocusCycleRoot());
        assertFalse(actualSwingFileChooser.isValidateRoot());
    }

    @Test
    public void testConstructor2() {
        SwingFileChooser actualSwingFileChooser = new SwingFileChooser(FileTypeSupport.directoriesOnly, FileOperation.OPEN);
        assertNull(actualSwingFileChooser.getName());
        assertEquals(0, actualSwingFileChooser.getMouseListeners().length);
        assertNull(actualSwingFileChooser.getGraphicsConfiguration());
        assertEquals(4, actualSwingFileChooser.getPropertyChangeListeners().length);
        assertFalse(actualSwingFileChooser.isPreferredSizeSet());
        assertEquals(0, actualSwingFileChooser.getWidth());
        assertEquals(0, actualSwingFileChooser.getMouseMotionListeners().length);
        assertEquals(0, actualSwingFileChooser.getVetoableChangeListeners().length);
        assertNull(actualSwingFileChooser.getDialogTitle());
        assertNull(actualSwingFileChooser.getInputVerifier());
        assertFalse(actualSwingFileChooser.getAutoscrolls());
        assertNull(actualSwingFileChooser.getToolTipText());
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicySet());
        assertTrue(actualSwingFileChooser.isDirectorySelectionEnabled());
        assertEquals(0, actualSwingFileChooser.getDialogType());
        assertNull(actualSwingFileChooser.getTopLevelAncestor());
        assertEquals(0, actualSwingFileChooser.getKeyListeners().length);
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicyProvider());
        assertEquals(0, actualSwingFileChooser.getSelectedFiles().length);
        assertFalse(actualSwingFileChooser.isFileSelectionEnabled());
        assertNull(actualSwingFileChooser.getAccessory());
        assertEquals(0.5f, actualSwingFileChooser.getAlignmentY(), 0.0f);
        assertNull(actualSwingFileChooser.getFocusCycleRootAncestor());
        assertEquals(1, actualSwingFileChooser.getChoosableFileFilters().length);
        assertNull(actualSwingFileChooser.getRootPane());
        assertNull(actualSwingFileChooser.getInputMethodRequests());
        assertTrue(actualSwingFileChooser.getVerifyInputWhenFocusTarget());
        assertTrue(actualSwingFileChooser.isAcceptAllFileFilterUsed());
        assertEquals(1, actualSwingFileChooser.getHierarchyListeners().length);
        assertFalse(actualSwingFileChooser.isLightweight());
        assertEquals(0, actualSwingFileChooser.getY());
        assertFalse(actualSwingFileChooser.isFocusOwner());
        assertEquals(0, actualSwingFileChooser.getHeight());
        assertFalse(actualSwingFileChooser.isPaintingForPrint());
        assertFalse(actualSwingFileChooser.isDisplayable());
        assertFalse(actualSwingFileChooser.isFocusCycleRoot());
        assertFalse(actualSwingFileChooser.isValidateRoot());
    }

    @Test
    public void testConstructor3() {
        SwingFileChooser actualSwingFileChooser = new SwingFileChooser(FileTypeSupport.filesAndDirectories,
                FileOperation.OPEN);
        assertNull(actualSwingFileChooser.getName());
        assertEquals(0, actualSwingFileChooser.getMouseListeners().length);
        assertNull(actualSwingFileChooser.getGraphicsConfiguration());
        assertEquals(4, actualSwingFileChooser.getPropertyChangeListeners().length);
        assertFalse(actualSwingFileChooser.isPreferredSizeSet());
        assertEquals(0, actualSwingFileChooser.getWidth());
        assertEquals(0, actualSwingFileChooser.getMouseMotionListeners().length);
        assertEquals(0, actualSwingFileChooser.getVetoableChangeListeners().length);
        assertNull(actualSwingFileChooser.getDialogTitle());
        assertNull(actualSwingFileChooser.getInputVerifier());
        assertFalse(actualSwingFileChooser.getAutoscrolls());
        assertNull(actualSwingFileChooser.getToolTipText());
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicySet());
        assertTrue(actualSwingFileChooser.isDirectorySelectionEnabled());
        assertEquals(0, actualSwingFileChooser.getDialogType());
        assertNull(actualSwingFileChooser.getTopLevelAncestor());
        assertEquals(0, actualSwingFileChooser.getKeyListeners().length);
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicyProvider());
        assertEquals(0, actualSwingFileChooser.getSelectedFiles().length);
        assertTrue(actualSwingFileChooser.isFileSelectionEnabled());
        assertNull(actualSwingFileChooser.getAccessory());
        assertEquals(0.5f, actualSwingFileChooser.getAlignmentY(), 0.0f);
        assertNull(actualSwingFileChooser.getFocusCycleRootAncestor());
        assertEquals(1, actualSwingFileChooser.getChoosableFileFilters().length);
        assertNull(actualSwingFileChooser.getRootPane());
        assertNull(actualSwingFileChooser.getInputMethodRequests());
        assertTrue(actualSwingFileChooser.getVerifyInputWhenFocusTarget());
        assertTrue(actualSwingFileChooser.isAcceptAllFileFilterUsed());
        assertEquals(1, actualSwingFileChooser.getHierarchyListeners().length);
        assertFalse(actualSwingFileChooser.isLightweight());
        assertEquals(0, actualSwingFileChooser.getY());
        assertFalse(actualSwingFileChooser.isFocusOwner());
        assertEquals(0, actualSwingFileChooser.getHeight());
        assertFalse(actualSwingFileChooser.isPaintingForPrint());
        assertFalse(actualSwingFileChooser.isDisplayable());
        assertFalse(actualSwingFileChooser.isFocusCycleRoot());
        assertFalse(actualSwingFileChooser.isValidateRoot());
    }

    @Test
    public void testConstructor4() {
        SwingFileChooser actualSwingFileChooser = new SwingFileChooser(FileTypeSupport.filesOnly, FileOperation.SAVE);
        assertNull(actualSwingFileChooser.getName());
        assertEquals(0, actualSwingFileChooser.getMouseListeners().length);
        assertNull(actualSwingFileChooser.getGraphicsConfiguration());
        assertEquals(4, actualSwingFileChooser.getPropertyChangeListeners().length);
        assertFalse(actualSwingFileChooser.isPreferredSizeSet());
        assertEquals(0, actualSwingFileChooser.getWidth());
        assertEquals(0, actualSwingFileChooser.getMouseMotionListeners().length);
        assertEquals(0, actualSwingFileChooser.getVetoableChangeListeners().length);
        assertNull(actualSwingFileChooser.getDialogTitle());
        assertNull(actualSwingFileChooser.getInputVerifier());
        assertFalse(actualSwingFileChooser.getAutoscrolls());
        assertNull(actualSwingFileChooser.getToolTipText());
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicySet());
        assertFalse(actualSwingFileChooser.isDirectorySelectionEnabled());
        assertEquals(1, actualSwingFileChooser.getDialogType());
        assertNull(actualSwingFileChooser.getTopLevelAncestor());
        assertEquals(0, actualSwingFileChooser.getKeyListeners().length);
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicyProvider());
        assertEquals(0, actualSwingFileChooser.getSelectedFiles().length);
        assertTrue(actualSwingFileChooser.isFileSelectionEnabled());
        assertNull(actualSwingFileChooser.getAccessory());
        assertEquals(0.5f, actualSwingFileChooser.getAlignmentY(), 0.0f);
        assertNull(actualSwingFileChooser.getFocusCycleRootAncestor());
        assertEquals(1, actualSwingFileChooser.getChoosableFileFilters().length);
        assertNull(actualSwingFileChooser.getRootPane());
        assertNull(actualSwingFileChooser.getInputMethodRequests());
        assertTrue(actualSwingFileChooser.getVerifyInputWhenFocusTarget());
        assertTrue(actualSwingFileChooser.isAcceptAllFileFilterUsed());
        assertEquals(1, actualSwingFileChooser.getHierarchyListeners().length);
        assertFalse(actualSwingFileChooser.isLightweight());
        assertEquals(0, actualSwingFileChooser.getY());
        assertFalse(actualSwingFileChooser.isFocusOwner());
        assertEquals(0, actualSwingFileChooser.getHeight());
        assertFalse(actualSwingFileChooser.isPaintingForPrint());
        assertFalse(actualSwingFileChooser.isDisplayable());
        assertFalse(actualSwingFileChooser.isFocusCycleRoot());
        assertFalse(actualSwingFileChooser.isValidateRoot());
    }

    @Test
    public void testConstructor5() {
        SwingFileChooser actualSwingFileChooser = new SwingFileChooser(FileTypeSupport.filesOnly, FileOperation.CUSTOM);
        assertNull(actualSwingFileChooser.getName());
        assertEquals(0, actualSwingFileChooser.getMouseListeners().length);
        assertNull(actualSwingFileChooser.getGraphicsConfiguration());
        assertEquals(4, actualSwingFileChooser.getPropertyChangeListeners().length);
        assertFalse(actualSwingFileChooser.isPreferredSizeSet());
        assertEquals(0, actualSwingFileChooser.getWidth());
        assertEquals(0, actualSwingFileChooser.getMouseMotionListeners().length);
        assertEquals(0, actualSwingFileChooser.getVetoableChangeListeners().length);
        assertNull(actualSwingFileChooser.getDialogTitle());
        assertNull(actualSwingFileChooser.getInputVerifier());
        assertFalse(actualSwingFileChooser.getAutoscrolls());
        assertNull(actualSwingFileChooser.getToolTipText());
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicySet());
        assertFalse(actualSwingFileChooser.isDirectorySelectionEnabled());
        assertEquals(2, actualSwingFileChooser.getDialogType());
        assertNull(actualSwingFileChooser.getTopLevelAncestor());
        assertEquals(0, actualSwingFileChooser.getKeyListeners().length);
        assertFalse(actualSwingFileChooser.isFocusTraversalPolicyProvider());
        assertEquals(0, actualSwingFileChooser.getSelectedFiles().length);
        assertTrue(actualSwingFileChooser.isFileSelectionEnabled());
        assertNull(actualSwingFileChooser.getAccessory());
        assertEquals(0.5f, actualSwingFileChooser.getAlignmentY(), 0.0f);
        assertNull(actualSwingFileChooser.getFocusCycleRootAncestor());
        assertEquals(1, actualSwingFileChooser.getChoosableFileFilters().length);
        assertNull(actualSwingFileChooser.getRootPane());
        assertNull(actualSwingFileChooser.getInputMethodRequests());
        assertTrue(actualSwingFileChooser.getVerifyInputWhenFocusTarget());
        assertTrue(actualSwingFileChooser.isAcceptAllFileFilterUsed());
        assertEquals(1, actualSwingFileChooser.getHierarchyListeners().length);
        assertFalse(actualSwingFileChooser.isLightweight());
        assertEquals(0, actualSwingFileChooser.getY());
        assertFalse(actualSwingFileChooser.isFocusOwner());
        assertEquals(0, actualSwingFileChooser.getHeight());
        assertFalse(actualSwingFileChooser.isPaintingForPrint());
        assertFalse(actualSwingFileChooser.isDisplayable());
        assertFalse(actualSwingFileChooser.isFocusCycleRoot());
        assertFalse(actualSwingFileChooser.isValidateRoot());
    }
}


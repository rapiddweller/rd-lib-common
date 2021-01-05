package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.tree.ChildTreeModel;
import org.junit.Test;

public class SwingTreeModelAdapterTest {
    @Test
    public void testConstructor() {
        assertTrue((new SwingTreeModelAdapter<Object>(new ChildTreeModel())).delegate instanceof ChildTreeModel);
    }
}


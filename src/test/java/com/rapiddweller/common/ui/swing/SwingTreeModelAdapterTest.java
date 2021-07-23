package com.rapiddweller.common.ui.swing;

import com.rapiddweller.common.tree.ChildTreeModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SwingTreeModelAdapterTest {
  @Test
  public void testConstructor() {
    assertTrue((new SwingTreeModelAdapter<Object>(new ChildTreeModel())).delegate instanceof ChildTreeModel);
  }
}


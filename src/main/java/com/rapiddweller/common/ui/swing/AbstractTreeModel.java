/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common.ui.swing;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides handling for {@link TreeModelListener}s.
 * Created: 22.08.2012 17:13:08
 *
 * @author Volker Bergmann
 * @since 0.5.18
 */
public abstract class AbstractTreeModel implements TreeModel {

  private final List<TreeModelListener> listeners;

  /**
   * Instantiates a new Abstract tree model.
   */
  public AbstractTreeModel() {
    this.listeners = new ArrayList<>();
  }

  @Override
  public void addTreeModelListener(TreeModelListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeTreeModelListener(TreeModelListener listener) {
    listeners.remove(listener);
  }

  /**
   * Fire tree structure changed.
   *
   * @param source the source
   * @param path   the path
   */
  protected void fireTreeStructureChanged(Object source, Object[] path) {
    TreeModelEvent event = new TreeModelEvent(source, path);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      listeners.get(i).treeStructureChanged(event);
    }
  }

  /**
   * Fire tree nodes changed.
   *
   * @param source       the source
   * @param path         the path
   * @param childIndices the child indices
   * @param children     the children
   */
  protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
    TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      listeners.get(i).treeNodesChanged(event);
    }
  }

  /**
   * Fire tree nodes inserted.
   *
   * @param source       the source
   * @param path         the path
   * @param childIndices the child indices
   * @param children     the children
   */
  protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
    TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      listeners.get(i).treeNodesInserted(e);
    }
  }

  /**
   * Fire tree nodes removed.
   *
   * @param source       the source
   * @param path         the path
   * @param childIndices the child indices
   * @param children     the children
   */
  protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
    TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      listeners.get(i).treeNodesRemoved(e);
    }
  }

}

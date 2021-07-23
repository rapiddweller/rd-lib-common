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

import com.rapiddweller.common.TreeModel;

import javax.swing.tree.TreePath;

/**
 * Adaptor that maps Databene {@link TreeModel}s to Swing {@link javax.swing.tree.TreeModel}.
 * Created: 02.12.2010 06:46:55
 *
 * @param <E> the type of the tree nodes
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class SwingTreeModelAdapter<E> extends AbstractTreeModel {

  /**
   * The Delegate.
   */
  com.rapiddweller.common.TreeModel<E> delegate;

  /**
   * Instantiates a new Swing tree model adapter.
   *
   * @param delegate the delegate
   */
  public SwingTreeModelAdapter(com.rapiddweller.common.TreeModel<E> delegate) {
    this.delegate = delegate;
  }

  @Override
  public Object getRoot() {
    return delegate.getRoot();
  }

  /**
   * Gets parent.
   *
   * @param child the child
   * @return the parent
   */
  public E getParent(E child) {
    return delegate.getParent(child);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object getChild(Object parent, int index) {
    return delegate.getChild((E) parent, index);
  }

  @Override
  @SuppressWarnings("unchecked")
  public int getChildCount(Object parent) {
    return delegate.getChildCount((E) parent);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean isLeaf(Object node) {
    return delegate.isLeaf((E) node);
  }

  @Override
  @SuppressWarnings("unchecked")
  public int getIndexOfChild(Object parent, Object child) {
    return delegate.getIndexOfChild((E) parent, (E) child);
  }

  @Override
  public void valueForPathChanged(TreePath path, Object newValue) {
    // nothing to do: com.rapiddweller.common.TreeModel does not support changing of path values
  }

}

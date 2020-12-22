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
package com.rapiddweller.commons.ui.swing;

import javax.swing.tree.TreePath;

import com.rapiddweller.commons.TreeModel;

/**
 * Adaptor that maps Databene {@link TreeModel}s to Swing {@link javax.swing.tree.TreeModel}.
 * Created: 02.12.2010 06:46:55
 * @param <E> the type of the tree nodes
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class SwingTreeModelAdapter<E> extends AbstractTreeModel {

	com.rapiddweller.commons.TreeModel<E> delegate;

	public SwingTreeModelAdapter(com.rapiddweller.commons.TreeModel<E> delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object getRoot() {
		return delegate.getRoot();
	}

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
		// nothing to do: com.rapiddweller.commons.TreeModel does not support changing of path values
	}

}

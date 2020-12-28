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
package com.rapiddweller.commons.tree;

import com.rapiddweller.commons.TreeModel;

/**
 * Default implementation of a {@link TreeModel}. It uses DefaultTreeNodes.
 * Created: 08.05.2007 19:05:14
 * @param <E> the type of the wrapped objects
 * @author Volker Bergmann
 */
public class DefaultTreeModel<E> implements TreeModel<DefaultTreeNode<E>> {

    private final DefaultTreeNode<E> root;

    public DefaultTreeModel(DefaultTreeNode<E> root) {
        this.root = root;
    }

    @Override
	public DefaultTreeNode<E> getRoot() {
        return root;
    }

    @Override
	public DefaultTreeNode<E> getParent(DefaultTreeNode<E> child) {
        return child.getParent();
    }

    @Override
	public DefaultTreeNode<E> getChild(DefaultTreeNode<E> parent, int index) {
        return parent.getChild(index);
    }

    @Override
	public int getChildCount(DefaultTreeNode<E> parent) {
        return parent.getChildCount();
    }

    @Override
	public boolean isLeaf(DefaultTreeNode<E> node) {
        return node.isLeaf();
    }

    @Override
	public int getIndexOfChild(DefaultTreeNode<E> parent, DefaultTreeNode<E> child) {
        return parent.getIndexOfChild(child);
    }
    
}

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

import java.util.Map;
import java.util.HashMap;

/**
 * Adapts a ChildModel to a TreeModel.
 * Created: 31.07.2007 06:32:41
 * @param <I> the id type
 * @param <V> the element type
 * @author Volker Bergmann
 */
public class ChildTreeModel<I, V> implements TreeModel<V> {

    private final ChildModel<I, V> childModel;
    private DefaultTreeNode<V> root;
    private final Map<I, DefaultTreeNode<V>> elements;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ChildTreeModel() {
        this(new DefaultChildModel());
    }

    public ChildTreeModel(ChildModel<I, V> childModel) {
        this.childModel = childModel;
        this.elements = new HashMap<>();
    }

    public void add(V element) {
        DefaultTreeNode<V> elementNode;
        I id = childModel.getId(element);
        I parentId = childModel.getParentId(element);
        if (id == null) {
            elementNode = new DefaultTreeNode<>(element);
            this.root = elementNode;
        } else {
            DefaultTreeNode<V> parentNode = elements.get(parentId);
            if (parentNode == null)
                if (root == null) {
                    parentNode = new DefaultTreeNode<>(null);
                    elements.put(parentId, parentNode);
                    this.root = parentNode;
                } else
                    throw new IllegalArgumentException("Multiple roots.");
            elementNode = new DefaultTreeNode<>(parentNode, element);
            parentNode.addChild(elementNode);
        }
        elements.put(id, elementNode);
    }

    @Override
	public V getRoot() {
        return root.getObject();
    }

    @Override
	public V getParent(V child) {
        return elements.get(childModel.getParentId(child)).getObject();
    }

    @Override
	public V getChild(V parent, int index) {
        return elements.get(childModel.getId(parent)).getChild(index).getObject();
    }

    @Override
	public int getChildCount(V parent) {
        return elements.get(childModel.getId(parent)).getChildCount();
    }

    @Override
	public boolean isLeaf(V node) {
        return elements.get(childModel.getId(node)).isLeaf();
    }

    @Override
	public int getIndexOfChild(V parent, V child) {
        return elements.get(childModel.getId(parent)).getIndexOfChild(new DefaultTreeNode<>(child));
    }
    
}

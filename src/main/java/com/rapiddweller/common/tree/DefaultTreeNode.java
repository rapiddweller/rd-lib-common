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
package com.rapiddweller.common.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of a tree node.
 * Created: 08.05.2007 19:06:49
 * @param <E> the type of the wrapped object
 * @author Volker Bergmann
 */
public class DefaultTreeNode<E> {

    private final DefaultTreeNode<E> parent;
    private final boolean leaf;
    private final List<DefaultTreeNode<E>> children;
    protected E object;

    public static <T> DefaultTreeNode<T> createLeaf(DefaultTreeNode<T> parent, T object) {
        return new DefaultTreeNode<>(parent, object, true);
    }

    public static <T> DefaultTreeNode<T> createFolder(DefaultTreeNode<T> parent, T object) {
        return new DefaultTreeNode<>(parent, object, false);
    }

    public DefaultTreeNode(E object) {
        this(null, object);
    }

    public DefaultTreeNode(DefaultTreeNode<E> parent, E object) {
        this(parent, object, false);
    }

    public DefaultTreeNode(DefaultTreeNode<E> parent, E object, boolean leaf) {
        this.parent = parent;
        this.leaf = leaf;
        this.children = new ArrayList<>();
        this.object = object;
    }

    public void addChild(DefaultTreeNode<E> child) {
        if (leaf)
            throw new IllegalStateException("Can't add a child to a leaf");
        children.add(child);
    }

    public DefaultTreeNode<E> getParent() {
        return parent;
    }

    public DefaultTreeNode<E> getChild(int index) {
        return children.get(index);
    }

    public int getChildCount() {
        return children.size();
    }

    public boolean isLeaf() {
        return leaf;
    }

    public int getIndexOfChild(DefaultTreeNode<E> child) {
        return children.indexOf(child);
    }

    public E getObject() {
        return object;
    }

    @Override
    public String toString() {
        return String.valueOf(object);
    }
}

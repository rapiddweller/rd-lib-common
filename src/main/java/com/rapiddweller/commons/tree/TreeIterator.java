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

import com.rapiddweller.commons.NullSafeComparator;
import com.rapiddweller.commons.TreeModel;
import com.rapiddweller.commons.iterator.BidirectionalIterator;

/**
 * Iterates a tree forward and backward.
 * Created: 08.05.2007 18:05:24
 * @param <E> the type of object to iterate
 * @author Volker Bergmann
 */
public class TreeIterator<E> implements BidirectionalIterator<E> {

    private final TreeModel<E> treeModel;
    private E cursor;

    private Boolean hasNext;
    private E next;
    private Boolean hasPrevious;
    private E previous;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public TreeIterator(TreeModel<E> treeModel) {
        this.treeModel = treeModel;
        this.cursor = treeModel.getRoot();
        this.hasNext = true;
        this.next = cursor;
        this.hasPrevious = null;
        this.previous = null;
    }
    
    // BidirectionalIterator interface implementation ------------------------------------------------------------------

    @Override
	public E first() {
        this.cursor = treeModel.getRoot();
        this.hasNext = null;
        this.next = null;
        this.hasPrevious = null;
        this.previous = null;
        return this.cursor;
    }

    @Override
	public boolean hasPrevious() {
        if (hasPrevious == null) {
            previous = nodeBefore(cursor, treeModel);
            hasPrevious = (previous != null);
        }
        return hasPrevious;
    }

    @Override
	public E previous() {
        if (!hasPrevious())
            throw new IllegalStateException("No object available for previous()");
        hasNext = true;
        next = cursor;
        cursor = previous;
        hasPrevious = null;
        previous = null;
        return cursor;
    }

    @Override
	public E last() {
        hasNext = false;
        next = null;
        hasPrevious = null;
        previous = null;
        E tmp = lastChild(treeModel.getRoot(), treeModel);
        while (treeModel.getChildCount(tmp) > 0) {
            E candidate = lastChild(tmp, treeModel);
            if (candidate == null) {// empty directory
                cursor = tmp;
                return cursor;
            }
            tmp = candidate;
        }
        cursor = tmp;
        return cursor;
    }

    @Override
	public boolean hasNext() {
        if (hasNext == null) {
            next = nodeAfter(cursor, treeModel);
            hasNext = (next != null);
        }
        return hasNext;
    }

    @Override
	public E next() {
        if (!hasNext())
            throw new IllegalStateException("No object available for next()");
        hasPrevious = true;
        previous = cursor;
        cursor = next;
        hasNext = null;
        next = null;
        return cursor;
    }

    @Override
	public void remove() {
        throw new UnsupportedOperationException("remove() is not supported on " + getClass());
    }
    
    // private helpers -------------------------------------------------------------------------------------------------
    
    private static <T> T nodeBefore(T cursor, TreeModel<T> treeModel) {
        // root is the very first node
        if (cursor == treeModel.getRoot())
            return null;
        // check previous siblings
        T parent = treeModel.getParent(cursor);
        for (int i = treeModel.getIndexOfChild(parent, cursor); i > 0; i--) {
            T tmp = treeModel.getChild(parent, i - 1);
            if (treeModel.getChildCount(tmp) == 0)
                return tmp;
            else {
                T candidate = lastSubNode(tmp, treeModel);
                if (candidate != null)
                    return candidate;
            }
        }
        /*
        // check sibling of parent
        if (!treeModel.getRoot().equals(parent))
            return nodeBefore(parent, treeModel);
        else // return root
        	return treeModel.getRoot();
        */
        return parent;
    }

    private static <T> T lastChild(T node, TreeModel<T> treeModel) {
        int childCount = treeModel.getChildCount(node);
        if (childCount > 0)
            return treeModel.getChild(node, childCount - 1);
        else
            return null;
    }

    private static <T> T lastSubNode(T node, TreeModel<T> treeModel) {
        T candidate = lastChild(node, treeModel);
        while (candidate != null) {
            if (treeModel.getChildCount(candidate) == 0)
                return candidate;
            else
                candidate = lastChild(candidate, treeModel);
        }
        return null;
    }

    private static <T> T nodeAfter(T cursor, TreeModel<T> treeModel) {
        // find next
        T tmp = null;
        if (treeModel.getChildCount(cursor) > 0)
                tmp = treeModel.getChild(cursor, 0);
        T parent = treeModel.getParent(cursor);
        if (tmp == null && parent != null) {
            int cursorIndex = treeModel.getIndexOfChild(parent, cursor);
            if (cursorIndex < treeModel.getChildCount(parent) - 1)
                tmp = treeModel.getChild(parent, cursorIndex + 1);
        }
        while (tmp == null && parent != null && !NullSafeComparator.equals(parent, treeModel.getRoot())) {
            T parentsParent = treeModel.getParent(parent);
            int parentsIndex = treeModel.getIndexOfChild(parentsParent, parent);
            int parentLevelCount = treeModel.getChildCount(parentsParent);
            if (parentsIndex < parentLevelCount - 1)
                tmp = treeModel.getChild(parentsParent, parentsIndex + 1);
            parent = parentsParent;
        }
        return tmp;
    }

}

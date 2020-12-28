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
import com.rapiddweller.commons.iterator.BidirectionalIteratorTest;
import org.junit.Test;

/**
 * Tests the {@link TreeIterator}.
 * Created at 04.05.2008 09:17:07
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class TreeIteratorTest extends BidirectionalIteratorTest {
	
	@Test
	@SuppressWarnings("unchecked")
    public void test() {
		DefaultTreeNode<Integer> root = new DefaultTreeNode<>(0);
		DefaultTreeNode<Integer> c1 = new DefaultTreeNode<>(root, 1);
		root.addChild(c1);
		DefaultTreeNode<Integer> c11 = new DefaultTreeNode<>(c1, 11);
		c1.addChild(c11);
		DefaultTreeNode<Integer> c2 = new DefaultTreeNode<>(root, 2);
		root.addChild(c2);
		DefaultTreeNode<Integer> c3 = new DefaultTreeNode<>(root, 3);
		root.addChild(c3);
		TreeModel<DefaultTreeNode<Integer>> model = new DefaultTreeModel<>(root);
		TreeIterator<DefaultTreeNode<Integer>> iterator = new TreeIterator<>(model);
		expectNextElements(iterator, root, c1, c11, c2, c3).withNoNext();
		expectPreviousElements(iterator, c2, c11, c1, root).withNoPrevious();
	}
	
}

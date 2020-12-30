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

import com.rapiddweller.common.TreeModel;
import com.rapiddweller.common.tree.DefaultTreeModel;
import com.rapiddweller.common.tree.DefaultTreeNode;

/**
 * Creates trees for testing.
 * Created: 08.05.2007 19:47:45
 * @author Volker Bergmann
 */
public class TreeCreator {

    public static TreeModel<DefaultTreeNode<String>> createTreeModel() {
        DefaultTreeNode<String> root = new DefaultTreeNode<>(null, "root", false);
        TreeModel<DefaultTreeNode<String>> model = new DefaultTreeModel<>(root);

        // create 1st level sub nodes
        root.addChild(DefaultTreeNode.createLeaf(root, "a1l"));
        DefaultTreeNode<String> a2f = DefaultTreeNode.createFolder(root, "a2f");
        root.addChild(a2f);
        root.addChild(DefaultTreeNode.createLeaf(root, "a3l"));

        // create 2nd level sub nodes
        DefaultTreeNode<String> b1f = DefaultTreeNode.createFolder(a2f, "b1f");
        a2f.addChild(b1f);

        // create 3nd level sub nodes
        DefaultTreeNode<String> c1l = DefaultTreeNode.createLeaf(b1f, "c1l");
        b1f.addChild(c1l);

        return model;
    }

}

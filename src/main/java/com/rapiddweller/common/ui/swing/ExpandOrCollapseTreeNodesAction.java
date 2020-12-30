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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.rapiddweller.common.ArrayUtil;

/**
 * {@link Action} class which expands or collapses nodes of a {@link JTree}.
 * Created: 23.08.2012 07:47:04
 * @since 0.5.18
 * @author Volker Bergmann
 */
@SuppressWarnings("serial")
public class ExpandOrCollapseTreeNodesAction extends AbstractAction {
	
	private final JTree tree;
	private final boolean expand;
	private final boolean recursive;

	public ExpandOrCollapseTreeNodesAction(JTree tree, boolean expand, boolean recursive) {
		super(expand ? "expand" : "collapse");
		putValue(SHORT_DESCRIPTION, (expand ? "Expand tree nodes" : "Collapse tree nodes"));
		this.tree = tree;
		this.expand = expand;
		this.recursive = recursive;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		tree.cancelEditing();
		TreePath[] selectionPaths = tree.getSelectionPaths();
		if (!ArrayUtil.isEmpty(selectionPaths)) {
			for (TreePath path : selectionPaths)
				handlePath(path);
		} else {
			handlePath(new TreePath(tree.getModel().getRoot()));
		}
	}

	private void handlePath(TreePath path) {
		Object node = path.getLastPathComponent();
		TreeModel model = tree.getModel();
		if (!model.isLeaf(node)) {
			if (recursive) {
				for (int i = model.getChildCount(node) - 1; i >= 0; i--)
					handlePath(path.pathByAddingChild(model.getChild(node, i)));
			}
			if (expand)
				tree.expandPath(path);
			else
				tree.collapsePath(path);
		}
	}

}

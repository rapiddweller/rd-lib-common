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

import com.rapiddweller.common.Filter;
import com.rapiddweller.common.TreeModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logs the structure represented by a {@link com.rapiddweller.common.TreeModel} implementor.
 * Created: 10.11.2010 10:21:59
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class TreeLogger {
	
	private static final Logger LOGGER = LogManager.getLogger(TreeLogger.class);

	String indent = "";
	
	// interface ---------------------------------------------------------------
	
	public <T> void log(TreeModel<T> model) {
	    log(model.getRoot(), false, model, null);
    }
	
	public <T> void log(TreeModel<T> model, Filter<T> filter) {
	    log(model.getRoot(), false, model, filter);
    }
	
	// private helper methods --------------------------------------------------

	private <T> void log(T node, boolean hasSiblings, TreeModel<T> model, Filter<T> filter) {
		if (filter != null && !filter.accept(node))
			return;
	    LOGGER.info(indent + node);
	    if (!model.isLeaf(node)) {
			increaseIndent(hasSiblings);
			int n = model.getChildCount(node);
			for (int i = 0; i < n; i++)
		    	log(model.getChild(node, i), i < n - 1, model, filter);
		    reduceIndent();
	    }
    }

	private void increaseIndent(boolean hasSuccessors) {
	    if (indent.length() == 0)
	    	indent = "+-";
	    else if (hasSuccessors)
	    	indent = indent.substring(0, indent.length() - 2) + "| " + indent.substring(indent.length() - 2);
	    else
	    	indent = indent.substring(0, indent.length() - 2) + "  " + indent.substring(indent.length() - 2);
    }

	private void reduceIndent() {
	    if (indent.length() >= 4)
	    	indent = indent.substring(0, indent.length() - 4) + indent.substring(indent.length() - 2);
	    else
	    	indent = "";
    }

}

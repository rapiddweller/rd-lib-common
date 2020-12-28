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
package com.rapiddweller.commons.iterator;

import com.rapiddweller.commons.ConfigurationError;
import com.rapiddweller.commons.StringUtil;

/**
 * Iterates through another {@link TabularIterator}, 
 * picking and possibly reordering a sub set of its columns.
 * Created: 26.01.2012 18:33:10
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class SelectiveTabularIterator extends IteratorProxy<Object[]> implements TabularIterator {
	
	private final String[] columnNames;
	private final int[] sourceIndexes;
	
	public SelectiveTabularIterator(TabularIterator source, String[] columnNames) {
		super(source);
		String[] sourceColumnNames = source.getColumnNames();
		if (columnNames != null) {
			this.columnNames = columnNames;
			this.sourceIndexes = new int[columnNames.length];
			for (int i = 0; i < columnNames.length; i++) {
				String columnName = columnNames[i];
				int sourceIndex = StringUtil.indexOfIgnoreCase(columnName, sourceColumnNames);
				if (sourceIndex < 0)
					throw new ConfigurationError("Column '" + columnName + "' not defined in source: " + source);
				this.sourceIndexes[i] = sourceIndex;
			}
		} else {
			this.columnNames = sourceColumnNames;
			this.sourceIndexes = null;
		}
	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}

	@Override
	public Object[] next() {
		Object[] sourceArray = super.next();
		if (sourceIndexes == null)
			return sourceArray;
		Object[] result = new Object[sourceIndexes.length];
		for (int i = 0; i < sourceIndexes.length; i++)
			result[i] = (i < sourceArray.length ? sourceArray[sourceIndexes[i]] : null);
		return result;
	}

}

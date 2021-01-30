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
package com.rapiddweller.common.iterator;

import com.rapiddweller.common.converter.ArrayTypeConverter;

import java.util.Iterator;

/**
 * Forwards the output of another array {@link Iterator}, 
 * interpreting its first provided array as column names.
 * Created: 26.01.2012 17:24:53
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class HeadedTabularIterator extends IteratorProxy<Object[]> implements TabularIterator {
	
	private String[] columnNames;

	public HeadedTabularIterator(Iterator<Object[]> source) {
		super(source);
		if (source.hasNext())
			this.columnNames = ArrayTypeConverter.convert(source.next(), String.class);
	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}
	
}

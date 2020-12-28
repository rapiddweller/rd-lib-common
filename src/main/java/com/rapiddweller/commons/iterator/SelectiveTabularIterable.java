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

/**
 * Special sub type of {@link Iterable} which creates {@link SelectiveTabularIterator}s.
 * Created: 26.01.2012 18:31:04
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class SelectiveTabularIterable implements TabularIterable {
	
	private final TabularIterable source;
	private final String[] columnNames;
	
	public SelectiveTabularIterable(TabularIterable source, String... columnNames) {
		this.source = source;
		this.columnNames = columnNames;
	}

	@Override
	public TabularIterator iterator() {
		return new SelectiveTabularIterator(source.iterator(), columnNames);
	}
	
}

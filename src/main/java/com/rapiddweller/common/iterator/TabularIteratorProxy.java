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

/**
 * Proxy implementation for the {@link TabularIterator} interface.
 * Created: 26.10.2011 21:58:20
 * @since 0.5.11
 * @author Volker Bergmann
 */
public class TabularIteratorProxy extends HeavyweightIteratorProxy<Object[]> implements TabularIterator {

	public TabularIteratorProxy(TabularIterator source) {
        super(source);
    }
	
	@Override
	public Object[] next() {
		return super.next();
	}

	@Override
	public String[] getColumnNames() {
        return ((TabularIterator) source).getColumnNames();
    }
	
}

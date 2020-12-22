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
package com.rapiddweller.commons.filter;

import com.rapiddweller.commons.Filter;

/**
 * Inverts the result of another {@link Filter}.
 * Created at 04.05.2008 10:10:09
 * @param <E> the type of objects to filter
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class InverseFilter<E> implements Filter<E> {

	private Filter<E> realFilter;

	public InverseFilter(Filter<E> realFilter) {
		this.realFilter = realFilter;
	}

	@Override
	public boolean accept(E candidate) {
		return !realFilter.accept(candidate);
	}
	
}

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
 * Yields the same result for any argument.
 * Created: 08.06.2012 20:56:15
 * @param <E> the type of objects to filter
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class ConstantFilter<E> implements Filter<E> {
	
	private final boolean accept;

	public ConstantFilter(boolean accept) {
		this.accept = accept;
	}

	@Override
	public boolean accept(E candidate) {
		return accept;
	}
	
}

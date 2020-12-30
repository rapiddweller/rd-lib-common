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
package com.rapiddweller.common.filter;

import com.rapiddweller.common.Filter;

/**
 * Combines {@link Filter} components in an OR manner: A candidate value is accepted if any of the 
 * components accepts it.
 * Created: 08.06.2012 20:28:54
 * @param <E> the type of objects to be filtered
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class OrFilter<E> extends CompositeFilter<E> {

	@SafeVarargs
    public OrFilter(Filter<E>... components) {
		super(components);
	}

	public boolean accept(E candidate) {
		for (Filter<E> component : components)
			if (component.accept(candidate))
				return true;
		return false;
	}

}

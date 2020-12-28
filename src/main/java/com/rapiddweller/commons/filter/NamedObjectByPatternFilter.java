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
import com.rapiddweller.commons.Named;

/**
 * {@link Filter} implementation which filters implementors of the {@link Named} interface by their name.
 * Created: 11.06.2011 15:44:44
 * @param <E> the type of objects to filter
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class NamedObjectByPatternFilter<E extends Named> implements Filter<E> {
	
	private final RegexBasedFilter nameFilter;
	
	public NamedObjectByPatternFilter(String inclusionPattern, String exclusionPattern) {
		this.nameFilter = new RegexBasedFilter(inclusionPattern, exclusionPattern);
	}

	@Override
	public boolean accept(Named candidate) {
	    return nameFilter.accept(candidate.getName());
	}

}

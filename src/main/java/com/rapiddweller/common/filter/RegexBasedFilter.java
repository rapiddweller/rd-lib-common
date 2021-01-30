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

import java.util.regex.Pattern;

/**
 * {@link Filter} implementation which filters strings by regular expressions for inclusion and exclusion.
 * Created: 11.06.2011 15:48:30
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class RegexBasedFilter implements Filter<String> {
	
	private final Pattern exclusionPattern;
	private final Pattern inclusionPattern;

	public RegexBasedFilter(String inclusionPattern, String exclusionPattern) {
		this.inclusionPattern = (inclusionPattern != null ? Pattern.compile(inclusionPattern) : null);
		this.exclusionPattern = (exclusionPattern != null ? Pattern.compile(exclusionPattern) : null);
	}
	
	@Override
	public boolean accept(String name) {
		if (exclusionPattern != null && exclusionPattern.matcher(name).matches())
			return false;
	    return (inclusionPattern == null || inclusionPattern.matcher(name).matches());
	}

}

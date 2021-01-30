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
package com.rapiddweller.common.tag;

import com.rapiddweller.common.Tagged;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides tag management, to be used as delegate object.
 * Created: 14.11.2013 07:00:00
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class TagSupport implements Tagged {
	
	private final Set<String> tags;
	
	public TagSupport() {
		this.tags = new HashSet<>();
	}
	
	@Override
	public Set<String> getTags() {
		return tags;
	}

	@Override
	public boolean hasTag(String tag) {
		return tags.contains(tag);
	}

	@Override
	public void addTag(String tag) {
		this.tags.add(tag);
		
	}

	@Override
	public void removeTag(String tag) {
		this.tags.remove(tag);
	}

}

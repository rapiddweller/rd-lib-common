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
package com.rapiddweller.commons.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.rapiddweller.commons.CollectionUtil;
import com.rapiddweller.commons.Context;

/**
 * {@link Context} implementation which is case insensitive regarding key Strings.
 * Created at 19.11.2008 08:55:05
 * @since 0.4.6
 * @author Volker Bergmann
 */

public class CaseInsensitiveContext implements Context {
	
	private final boolean capsPreserved;
	private final Map<String, Object> map;

	public CaseInsensitiveContext(boolean capsPreserved) {
		this.capsPreserved = capsPreserved;
		map = new HashMap<>();
	}
	
	// Context interface implementation --------------------------------------------------------------------------------

	@Override
	public synchronized void set(String key, Object value) {
		map.put(transformKey(key), value);
	}
	
    @Override
	public synchronized Object get(String key) {
        return CollectionUtil.getCaseInsensitive(transformKey(key), map);
    }

    @Override
	public boolean contains(String key) {
        return CollectionUtil.containsCaseInsensitive(transformKey(key), map);
    }

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public void remove(String key) {
		map.remove(transformKey(key));
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private String transformKey(String key) {
		if (!capsPreserved)
			key = key.toUpperCase();
		return key;
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + map;
	}

}

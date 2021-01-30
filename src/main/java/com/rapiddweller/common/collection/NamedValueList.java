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
package com.rapiddweller.common.collection;

import com.rapiddweller.common.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a list of named objects supporting duplicate names and missing names.
 * Created at 09.05.2008 20:25:59
 * @param <E> the type of the collection's elements
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NamedValueList<E> {
	
	public static final int SENSITIVE = 0;
	public static final int INSENSITIVE = 1;
	public static final int IGNORANT = 2;

	private final int caseHandling;
	private final List<String> names;
	private final List<E> values;
	private final Map<String, Integer> indices;

	public static <T> NamedValueList<T> createCaseSensitiveList() {
		return new NamedValueList<>(SENSITIVE);
	}
	
	public static <T> NamedValueList<T> createCaseInsensitiveList() {
		return new NamedValueList<>(INSENSITIVE);
	}
	
	public static <T> NamedValueList<T> createCaseIgnorantList() {
		return new NamedValueList<>(IGNORANT);
	}

	public NamedValueList() {
		this(SENSITIVE);
	}
		
	public NamedValueList(int caseHandling) {
		this.names   = new ArrayList<>();
		this.values  = new ArrayList<>();
		this.indices = new HashMap<>();
		this.caseHandling = caseHandling;
	}
	
	public int size() {
		return values.size();
	}
	
	public String getName(int index) {
		return names.get(index);
	}
	
	public boolean containsName(String name) {
		if (caseHandling == IGNORANT && name != null)
			name = name.toLowerCase();
		boolean contained = indices.containsKey(name);
		if (contained || caseHandling == IGNORANT || caseHandling == SENSITIVE || name == null)
			return contained;
		for (String nameCandidate : names)
			if (StringUtil.equalsIgnoreCase(nameCandidate, name))
				return true;
		return false;
	}
	
	public E getValue(int index) {
		return values.get(index);
	}

	public void set(String name, E value) {
		if (StringUtil.isEmpty(name))
			add(name, value);
		if (caseHandling == IGNORANT)
			name = name.toLowerCase();
		int index = someIndexOfName(name);
		if (index < 0)
			add(name, value);
		else
			setValue(index, value);
	}
	
	public void add(String name, E value) {
		if (caseHandling == IGNORANT && name != null)
			name = name.toLowerCase();
		names.add(name);
		values.add(value);
		indices.put(name, values.size() - 1);
	}
	
	public void setValue(int index, E value) {
		values.set(index, value);
	}
	
	public E someValueOfName(String name) {
		int index = someIndexOfName(name);
		return (index >= 0 ? getValue(index) : null);
	}
	
	public int someIndexOfName(String name) {
		Integer index;
		if (caseHandling == IGNORANT && name != null)
			name = name.toLowerCase();
		index = indices.get(name);
		if (index != null)
			return index;
		if (caseHandling == IGNORANT || caseHandling == SENSITIVE)
			return -1;
		for (Map.Entry<String, Integer> entry : indices.entrySet())
			if (StringUtil.equalsIgnoreCase(entry.getKey(), name))
				return entry.getValue();
		return -1;
	}
	
	public void clear() {
		names.clear();
		values.clear();
		indices.clear();
	}

	public List<String> names() {
		return Collections.unmodifiableList(names);
	}

	public List<E> values() {
		return Collections.unmodifiableList(values);
	}
	
	@Override
	public String toString() {
		return values.toString();
	}
}

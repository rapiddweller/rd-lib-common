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
package com.rapiddweller.commons.version;

import java.io.Serializable;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.List;

import com.rapiddweller.commons.CollectionUtil;

/**
 * Represents a software version number.
 * Attention: equals() may return false for instances which are equivalent concerning compareTo(), e.g. 1.0 and 1.0.0.
 * Created at 22.12.2008 16:46:24
 * @since 0.5.7
 * @author Volker Bergmann
 */

public class VersionNumber implements Comparable<VersionNumber>, Serializable {

	private static final long serialVersionUID = 8411677292851262669L;
	
	private static final NumberVersionNumberComponent ZERO_COMPONENT = new NumberVersionNumberComponent("0");
	private static final VersionNumberParser PARSER = new VersionNumberParser();
	
	private VersionNumberComponent[] components;
	private String[] delimiters;
	
	public static VersionNumber valueOf(String text) {
		return PARSER.parseObject(text, new ParsePosition(0));
	}
	
	/*
	public VersionNumber(String number) {
		if (StringUtil.isEmpty(number)) {
			components = new VersionNumberComponent[] { new NumberVersionNumberComponent("1") };
			delimiters = new String[0];
		} else {
			ParsePosition pos = new ParsePosition(0);
			String delimiter;
			ArrayBuilder<VersionNumberComponent> componentBuilder 
				= new ArrayBuilder<VersionNumberComponent>(VersionNumberComponent.class);
			ArrayBuilder<String> delimBuilder = new ArrayBuilder<String>(String.class);
			do {
				componentBuilder.add(parseComponent(number, pos));
				delimiter = parseDelimiter(number, pos);
				if (delimiter != null)
					delimBuilder.add(delimiter);
			} while (delimiter != null);
			this.components = componentBuilder.toArray();
			this.delimiters = delimBuilder.toArray();
		}
	}
	*/
	
	public VersionNumber(List<Object> components) {
		this.components = new VersionNumberComponent[components.size() / 2 + 1];
		this.delimiters = new String[components.size() / 2];
		for (int i = 0; i < components.size(); i += 2) {
			Object component = components.get(i);
			if (component instanceof Number)
				this.components[i / 2] = new NumberVersionNumberComponent(((Number) component).intValue());
			else
				this.components[i / 2] = new StringVersionNumberComponent((String) component);
			if (i / 2 < delimiters.length)
				this.delimiters[i / 2] = (String) components.get(i + 1);
		}
	}

	public VersionNumber(List<VersionNumberComponent> components, List<String> delimiters) {
		this.components = CollectionUtil.toArray(components, VersionNumberComponent.class);
		this.delimiters = CollectionUtil.toArray(delimiters, String.class);
	}

	@Override
	public int compareTo(VersionNumber that) {
		int n = Math.min(this.components.length, that.components.length);
		for (int i = 0; i < n; i++) {
			int componentComparation = this.components[i].compareTo(that.components[i]);
			if (componentComparation != 0)
				return componentComparation;
		}
		if (this.components.length == that.components.length)
			return 0;
		else if (this.components.length < that.components.length)
			return - checkAdditionalComponents(that.components, this.components.length);
		else
			return checkAdditionalComponents(this.components, that.components.length);
	}
	
	private static int checkAdditionalComponents(VersionNumberComponent[] components, int from) {
		for (int i = from; i < components.length; i++) {
			int comparation = components[i].compareTo(ZERO_COMPONENT);
			if (comparation != 0)
				return comparation;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < components.length; i++) {
			builder.append(components[i]);
			if (i < delimiters.length)
				builder.append(delimiters[i]);
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return 31 * Arrays.hashCode(components) + Arrays.hashCode(delimiters);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		VersionNumber other = (VersionNumber) obj;
		return (Arrays.equals(components, other.components) && Arrays.equals(delimiters, other.delimiters));
	}
	
}

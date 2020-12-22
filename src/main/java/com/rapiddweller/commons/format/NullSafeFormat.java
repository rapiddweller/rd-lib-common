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
package com.rapiddweller.commons.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Wraps another {@link Format} and overrides the mapping from a null value to a string and vice versa.
 * Created: 28.02.2013 16:20:55
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class NullSafeFormat extends Format {
	
	private static final long serialVersionUID = 2203854824964382584L;
	
	private Format realFormat;
	private String nullString;

	public NullSafeFormat(Format realFormat, String nullString) {
		this.realFormat = realFormat;
		this.nullString = nullString;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj == null)
			return toAppendTo.append(nullString);
		return realFormat.format(obj, toAppendTo, pos);
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		if (source == null || nullString.equals(source.substring(pos.getIndex())))
			return null;
		return realFormat.parseObject(source, pos);
	}
	
}

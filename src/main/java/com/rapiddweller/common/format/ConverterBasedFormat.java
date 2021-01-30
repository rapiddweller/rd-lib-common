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
package com.rapiddweller.common.format;

import com.rapiddweller.common.Converter;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Format implementation that uses a to-String-Converter for formatting objects.
 * Created: 25.04.2015 06:58:54
 * @since 1.0.6
 * @author Volker Bergmann
 */
public class ConverterBasedFormat extends Format {

	private static final long serialVersionUID = 1L;
	
	private final Converter<Object, String> converter;

	@SuppressWarnings("unchecked")
	public ConverterBasedFormat(Converter<?, String> converter) {
		this.converter = (Converter<Object, String>) converter;
	}

	@Override
	public StringBuffer format(Object sourceValue, StringBuffer toAppendTo, FieldPosition pos) {
		toAppendTo.append((converter).convert(sourceValue));
		return toAppendTo;
	}

	@Override
	public Object parseObject(String text, ParsePosition pos) {
		throw new UnsupportedOperationException();
	}

}

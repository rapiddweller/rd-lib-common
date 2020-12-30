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
package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

/**
 * Converts {@link Number} objects to {@link Integer}s.
 * Created at 14.04.2008 10:48:53
 * @since 0.4.2
 * @author Volker Bergmann
 *
 */
public class Number2IntegerConverter extends ThreadSafeConverter<Number, Integer> {

	public Number2IntegerConverter() {
		super(Number.class, Integer.class);
	}

	@Override
	public Integer convert(Number sourceValue) throws ConversionException {
		return (sourceValue != null ? sourceValue.intValue() : null); 
	}

}

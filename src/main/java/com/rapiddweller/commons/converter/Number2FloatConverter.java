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
package com.rapiddweller.commons.converter;

import com.rapiddweller.commons.ConversionException;

/**
 * Converts arbitrary {@link Number}s to {@link Float}s.
 * Created: 15.11.2012 08:26:50
 * @since 0.5.20
 * @author Volker Bergmann
 */
public class Number2FloatConverter extends ThreadSafeConverter<Number, Float> {

	protected Number2FloatConverter() {
		super(Number.class, Float.class);
	}

	@Override
	public Float convert(Number sourceValue) throws ConversionException {
		return (sourceValue != null ? sourceValue.floatValue() : null);
	}

}

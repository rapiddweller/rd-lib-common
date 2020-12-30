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
 * Converts {@link Number}s to characters like a cast does, e.g. (char) 65.
 * Created: 19.01.2011 15:33:22
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class Number2CharConverter extends ThreadSafeConverter<Number, Character>{

	public Number2CharConverter() {
		super(Number.class, Character.class);
	}

	@Override
	public Character convert(Number sourceValue) throws ConversionException {
		return (sourceValue != null ? (char) sourceValue.intValue() : null);
	}

}

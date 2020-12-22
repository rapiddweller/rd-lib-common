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
 * Converts an arbitrary object to a hash code using the method <code>hashCode()</code>. 
 * Null values are converted to zero.
 * Created at 06.11.2008 07:37:17
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class ToHashCodeConverter extends ThreadSafeConverter<Object, Integer>{

	public ToHashCodeConverter() {
		super(Object.class, Integer.class);
	}

	@Override
	public Integer convert(Object sourceValue) throws ConversionException {
		return (sourceValue != null ? sourceValue.hashCode() : 0);
	}

}

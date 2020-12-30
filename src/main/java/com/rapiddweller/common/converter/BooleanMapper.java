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
 * Maps true, false and null to configured aliases, e.g. '1', '0' and '?'.
 * By default booleans are converted to the strings 'true', 'false' and null. 
 * Created at 11.03.2009 19:40:33
 * @param <T> the object type to convert to
 * @since 0.5.8
 * @author Volker Bergmann
 */

public class BooleanMapper<T> extends ThreadSafeConverter<Boolean, T> {

    private final T trueValue;
	private final T falseValue;
	private final T nullValue;

	@SuppressWarnings("unchecked")
    public BooleanMapper() {
	    this((T) "true", (T) "false", null);
    }

	@SuppressWarnings("unchecked")
    public BooleanMapper(T trueValue, T falseValue, T nullValue) {
	    super(Boolean.class, (Class<T>) trueValue.getClass());
	    this.trueValue = trueValue;
	    this.falseValue = falseValue;
	    this.nullValue = nullValue;
    }

    @Override
	public T convert(Boolean sourceValue) throws ConversionException {
	    return (sourceValue != null ? (sourceValue ? trueValue : falseValue) : nullValue);
    }

}

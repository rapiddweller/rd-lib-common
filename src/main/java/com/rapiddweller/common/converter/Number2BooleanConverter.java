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
 * Converts {@link Number}s to {@link Boolean}s, 
 * interpreting zero as false, any other value as true.
 * Created: 27.02.2010 09:55:30
 * @param <S> the number type to convert from
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Number2BooleanConverter<S extends Number> extends ThreadSafeConverter<S, Boolean>{

	protected Number2BooleanConverter(Class<S> sourceType) {
	    super(sourceType, Boolean.class);
    }

	@Override
	public Boolean convert(S sourceValue) throws ConversionException {
	    return (sourceValue.doubleValue() != 0);
    }

}

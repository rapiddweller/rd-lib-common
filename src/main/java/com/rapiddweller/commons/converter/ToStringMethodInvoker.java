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
import com.rapiddweller.commons.Converter;

/**
 * {@link Converter} implementation which invokes the toString() method on the source object.
 * Created: 27.02.2010 09:44:10
 * @param <E> the object type to convert
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class ToStringMethodInvoker<E> extends ThreadSafeConverter<E, String> {

	protected ToStringMethodInvoker(Class<E> sourceType) {
	    super(sourceType, String.class);
    }

	@Override
	public String convert(E sourceValue) throws ConversionException {
	    return (sourceValue != null ? sourceValue.toString() : null);
    }

}

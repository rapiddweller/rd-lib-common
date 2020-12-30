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
import com.rapiddweller.common.Converter;

/**
 * Proxy class for (unsafe) {@link Converter}s 
 * that synchronizes calls to the wrapped converter instance.
 * Created: 26.02.2010 18:22:36
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class SynchronizedConverterProxy<S, T> extends ConverterProxy<S, T> {

	protected SynchronizedConverterProxy(Converter<S, T> realConverter) {
	    super(realConverter);
    }

	@Override
	public T convert(S sourceValue) throws ConversionException {
		synchronized(realConverter) {
			return realConverter.convert(sourceValue);
		}
    }

}

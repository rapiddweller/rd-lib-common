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
 * Parent class for {@link Converter}s that act as a proxy to another converter instance.
 * Created: 26.02.2010 17:30:25
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public abstract class ConverterProxy<S, T> extends ConverterWrapper<S, T> implements Converter<S, T> {
	
	protected ConverterProxy(Converter<S, T> realConverter) {
	    super(realConverter);
    }

	@Override
	public Class<S> getSourceType() {
	    return realConverter.getSourceType();
    }

	@Override
	public Class<T> getTargetType() {
	    return realConverter.getTargetType();
    }
	
	@Override
	public T convert(S sourceValue) throws ConversionException {
		return realConverter.convert(sourceValue);
	}
	
}

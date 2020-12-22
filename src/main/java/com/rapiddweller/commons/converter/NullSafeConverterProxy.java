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
 * Wraps another Converter and adds the feature of converting null values to a predefined value.
 * Created: 26.07.2007 06:59:35
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 */
public class NullSafeConverterProxy<S, T> extends ConverterProxy<S, T> {

    protected T nullResult;

    public NullSafeConverterProxy(Converter<S, T> realConverter, T nullResult) {
        super(realConverter);
        this.nullResult = nullResult;
    }

    @Override
	public Class<T> getTargetType() {
        return realConverter.getTargetType();
    }

	@Override
	public T convert(S sourceValue) throws ConversionException {
        return (sourceValue != null ? realConverter.convert(sourceValue) : nullResult);
    }

}

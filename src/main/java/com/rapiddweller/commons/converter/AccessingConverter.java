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

import com.rapiddweller.commons.Accessor;
import com.rapiddweller.commons.ConversionException;

/**
 * Wraps an Accessor into a Converter interface.
 * The object to be converted is used as the provider for the Accessor
 * Created: 26.08.2007 07:25:26
 * @author Volker Bergmann
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 */
public class AccessingConverter<C, V> extends ThreadSafeConverter<C, V> {

    private Accessor<C, V> accessor;

    public AccessingConverter(Class<C> sourceType, Class<V> targetType, Accessor<C, V> accessor) {
        super(sourceType, targetType);
        this.accessor = accessor;
    }

    @Override
	public V convert(C sourceValue) throws ConversionException {
        return accessor.getValue(sourceValue);
    }
    
}

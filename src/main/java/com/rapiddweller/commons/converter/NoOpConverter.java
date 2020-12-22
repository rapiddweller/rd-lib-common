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
 * Returns the argument to be converted.
 * Created: 27.09.2006 23:26:25
 * @param <E> the object type to convert from and to
 * @since 0.1
 * @author Volker Bergmann
 */
public class NoOpConverter<E> extends ThreadSafeConverter<E,E> {

    private static final NoOpConverter<?> instance = new NoOpConverter<Object>();

    @SuppressWarnings("rawtypes")
	public static NoOpConverter getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public NoOpConverter() {
        this((Class<E>) Object.class);
    }

    public NoOpConverter(Class<E> type) {
        super(type, type);
    }

    @Override
	public E convert(E source) throws ConversionException {
        return source;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
}

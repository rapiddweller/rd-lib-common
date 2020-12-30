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
package com.rapiddweller.common.iterator;

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.HeavyweightIterator;
import com.rapiddweller.common.IOUtil;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Iterator proxy that converts iterated objects before providing them to the caller.
 * Created: 16.08.2007 06:34:59
 * @param <S> the type to iterate
 * @param <T> the type to provide
 * @author Volker Bergmann
 */
public class ConvertingIterator<S, T> implements HeavyweightIterator<T> {

    protected Iterator<S> source;
    protected Converter<S, T> converter;

    public ConvertingIterator(Iterator<S> source, Converter<S, T> converter) {
        this.source = source;
        this.converter = converter;
    }

    @Override
	public void close() {
        if (source instanceof Closeable)
            IOUtil.close((Closeable) source);
    }

    @Override
	public boolean hasNext() {
        return source.hasNext();
    }

    @Override
	public T next() {
        S sourceValue = source.next();
        return converter.convert(sourceValue);
    }

    @Override
	public void remove() {
        source.remove();
    }
    
}

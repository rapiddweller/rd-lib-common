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

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import com.rapiddweller.common.HeavyweightIterator;

/**
 * Proxy for an {@link Iterator} with additional support for 
 * iterators that implement the {@link Closeable} interface.
 * Created: 13.10.2010 13:22:46
 * @param <E> the type to iterate
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class IteratorProxy<E> implements HeavyweightIterator<E> {
	
	protected Iterator<E> source;

	public IteratorProxy(Iterator<E> source) {
	    this.source = source;
    }

	@Override
	public boolean hasNext() {
	    return source.hasNext();
    }

	@Override
	public E next() {
	    return source.next();
    }

	@Override
	public void remove() {
	    source.remove();
    }

	@Override
	public void close() throws IOException {
		if (source instanceof Closeable)
			((Closeable) source).close();
    }
	
	@Override
	public String toString() {
		return source.toString();
	}
	
}

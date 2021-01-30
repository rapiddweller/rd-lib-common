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

import com.rapiddweller.common.HeavyweightIterator;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Wraps an {@link Iterator} with a {@link HeavyweightIterator}. 
 * If the wrapped iterator implements {@link Closeable}, calls to 
 * <code>close()</code> are forwarded, otherwise ignored.
 * Created: 14.10.2009 11:50:43
 * @param <E> the type to iterate
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class HeavyweightIteratorProxy<E> extends HeavyweightIteratorAdapter<E, E> {
	
	public HeavyweightIteratorProxy(Iterator<E> source) {
		super(source);
	}

	@Override
	public E next() {
		return source.next();
	}

}

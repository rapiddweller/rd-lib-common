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

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.TypedIterable;

import java.util.Iterator;

/**
 * Implementation of the {@link Iterable} interface which creates {@link Iterator} that iterate over an array.
 * Created at 30.06.2009 09:30:02
 * @param <E> the type to iterate
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class ArrayIterable<E> implements TypedIterable<E> {
	
	protected Class<E> type;
	private final E[] source;

    public ArrayIterable(E[] source, Class<E> type) {
	    this.source = source;
	    this.type = type;
    }

	@Override
	public Class<E> getType() {
	    return type;
    }

    @Override
	public Iterator<E> iterator() {
	    return new ArrayIterator<>(source);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + ArrayFormat.format(source) + ']';
    }
    
}

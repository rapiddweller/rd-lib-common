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

package com.rapiddweller.commons.collection;

import com.rapiddweller.commons.ArrayUtil;

/**
 * Implements a generic ring buffer.<br><br>
 * Created: 25.11.2017 23:34:53
 * @since 1.0.12
 * @author Volker Bergmann
 * @param <E> the type of object to be buffered
 */

public class RingBuffer<E> {
	
	protected final E[] buffer;
	private int cursor;
	private int size;

	public RingBuffer(Class<E> componentClass, int capacity) {
		this.buffer = ArrayUtil.newInstance(componentClass, capacity);
		this.cursor = 0;
		this.size = 0;
	}
	
	public int getCapacity() {
		return buffer.length;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isFilled() {
		return (size == buffer.length);
	}
	
	public boolean contains(E object) {
		for (Object o : buffer)
			if (o != null && o.equals(object))
				return true;
		return false;
	}

	public E add(E object) {
		E oldComponent = buffer[cursor];
		buffer[cursor++] = object;
		if (cursor == buffer.length)
			cursor = 0;
		if (size < buffer.length)
			size++;
		return oldComponent;
	}
	
	public E get(int index) {
		if (index > size - 1)
			return null;
		int offset = cursor - index - 1;
		if (offset < 0)
			offset += buffer.length;
		return buffer[offset];
	}
	
}

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
package com.rapiddweller.common.array;

import com.rapiddweller.common.converter.ToStringConverter;

/**
 * Helper class for constructing int arrays.
 * Created: 27.12.2010 07:45:22
 * @since 1.0.6
 * @author Volker Bergmann
 */
public class IntArrayBuilder {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    
	// constructors ------------------------------------------------------------
	
    protected int[] buffer;
    protected int itemCount;
    
    // constructors ------------------------------------------------------------

    public IntArrayBuilder() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    
    public IntArrayBuilder(int initialCapacity) {
        this.buffer = createBuffer(initialCapacity);
    }
    
    // interface ---------------------------------------------------------------

    public int length() {
    	return itemCount;
    }
    
    public int get(int index) {
    	return this.buffer[index];
    }
    
    public void set(int index, int value) {
    	if (index < this.buffer.length) {
    		this.buffer[index] = value;
    	} else {
    		for (int i = buffer.length; i < index; i++)
    			add(0);
    		add(value);
    	}
    }
    
    public IntArrayBuilder add(int item) {
        if (this.buffer == null)
            throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
        if (itemCount >= buffer.length - 1) {
            int[] newBuffer = createBuffer(buffer.length * 2);
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            this.buffer = newBuffer;
        }
        buffer[itemCount++] = item;
        return this;
    }
    
    public void addAll(int[] elements) {
	    addAll(elements, 0, elements.length);
    }
    
    public void addAll(int[] elements, int fromIndex, int toIndex) {
	    for (int i = fromIndex; i < toIndex; i++)
	    	add(elements[i]);
    }
    
    // private helper method -------------------------------------------------------------------------------------------

    private static int[] createBuffer(int capacity) {
        return new int[capacity];
    }

    public int[] getAndDeleteBuffer() {
        if (this.buffer == null)
            throw new UnsupportedOperationException("buffer already deleted");
        return this.buffer;
    }
    
    public int[] toArray() {
        if (this.buffer == null)
            throw new UnsupportedOperationException("buffer was deleted");
        int[] result = new int[this.itemCount];
        System.arraycopy(buffer, 0, result, 0, this.itemCount);
        this.itemCount = 0;
        buffer = null;
        return result;
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < itemCount; i++) {
    		if (i > 0)
    			builder.append(", ");
    		builder.append(ToStringConverter.convert(buffer[i], ""));
    	}
    	return builder.toString();
    }
    
}

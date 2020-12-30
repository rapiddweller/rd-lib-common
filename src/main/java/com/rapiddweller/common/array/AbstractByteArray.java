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
 * Parent class for classes that manage a dynamically increasing byte array.
 * Created: 02.04.2015 11:04:03
 * @since 1.0.5
 * @author Volker Bergmann
 */

public abstract class AbstractByteArray {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    
    protected byte[] buffer;
    protected int itemCount;
    
    // constructors ------------------------------------------------------------

    public AbstractByteArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    
    public AbstractByteArray(int initialCapacity) {
        this.buffer = createBuffer(initialCapacity);
    }
    
    // interface ---------------------------------------------------------------

    public int length() {
    	return itemCount;
    }
    
    public byte get(int index) {
    	return this.buffer[index];
    }
    
    public void set(int index, byte value) {
    	if (index < this.buffer.length) {
    		this.buffer[index] = value;
    	} else {
    		for (int i = buffer.length; i < index; i++)
    			add((byte) 0);
    		add(value);
    	}
    }
    
    public AbstractByteArray add(byte item) {
        if (itemCount >= buffer.length - 1) {
            byte[] newBuffer = createBuffer(buffer.length * 2);
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[itemCount++] = item;
        return this;
    }
    
    public void addAll(byte[] elements) {
	    addAll(elements, 0, elements.length);
    }
    
    public void addAll(byte[] elements, int fromIndex, int toIndex) {
	    for (int i = fromIndex; i < toIndex; i++)
	    	add(elements[i]);
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < itemCount; i++) {
    		if (i > 0)
    			builder.append(", ");
    		builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    	}
    	return builder.toString();
    }
    
    // private helper method -------------------------------------------------------------------------------------------

    private static byte[] createBuffer(int capacity) {
        return new byte[capacity];
    }

}

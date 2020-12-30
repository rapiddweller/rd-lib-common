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
package com.rapiddweller.common.math;

/**
 * Simple non-thread-save implementation of a mutable integer object.
 * Created at 12.07.2009 00:34:24
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class MutableInteger {

	public int value;

	public MutableInteger(int value) {
		this.value = value;
    }
	
	public void increment() {
		this.value++;
	}
	
	@Override
    public int hashCode() {
	    return value;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    MutableInteger that = (MutableInteger) obj;
	    return (this.value == that.value);
    }

	@Override
	public String toString() {
	    return String.valueOf(value);
	}
	
}

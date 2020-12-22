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
package com.rapiddweller.commons.bean;

import java.util.Arrays;

import com.rapiddweller.commons.ArrayFormat;

/**
 * Wrapper for an array which implements {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * based on the array's element values.
 * Created: 03.02.2012 16:34:14
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class ArrayWithIdentity {

	private Object[] elements;

	public ArrayWithIdentity(Object[] elements) {
	    this.elements = elements;
    }

	public int getElementCount() {
		return elements.length;
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(elements);
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    ArrayWithIdentity that = (ArrayWithIdentity) obj;
	    return Arrays.equals(this.elements, that.elements);
    }
	
	@Override
	public String toString() {
		return ArrayFormat.format(elements);
    }

}

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
 * Represents a range of int values from a <code>min</code> to a <code>max</code> value 
 * (including min and max).
 * Created: 05.10.2010 19:33:23
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class IntRange {
	
	protected int min;
	protected int max;
	
	public IntRange(int min, int max) {
	    this.min = min;
	    this.max = max;
    }

	public int getMin() {
    	return min;
    }

	public void setMin(int min) {
    	this.min = min;
    }

	public int getMax() {
    	return max;
    }

	public void setMax(int max) {
    	this.max = max;
    }
	
	public boolean contains(int i) {
		return (min <= i && i <= max);
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + max;
	    result = prime * result + min;
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    IntRange that = (IntRange) obj;
	    return (max == that.max && min == that.min);
    }
	
	@Override
	public String toString() {
	    return (min != max ? min + "..." + max : String.valueOf(min));
	}
	
}

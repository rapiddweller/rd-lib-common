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
package com.rapiddweller.commons.math;

/**
 * Simple non-thread-save implementation of a mutable double object.
 * Created at 17.07.2009 06:13:53
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class MutableDouble {

	public double value;

	public MutableDouble(double value) {
	    super();
	    this.value = value;
    }
	
	@Override
    public int hashCode() {
	    long temp = Double.doubleToLongBits(value);
	    return (int) (temp ^ (temp >>> 32));
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    MutableDouble that = (MutableDouble) obj;
	    return (Double.doubleToLongBits(value) != Double.doubleToLongBits(that.value));
    }


	@Override
	public String toString() {
	    return String.valueOf(value);
	}
}

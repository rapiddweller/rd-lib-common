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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of intervals and provides a {@link #contains(Object)} 
 * method for checking if one of them contains a certain value.
 * Created: 10.03.2011 17:28:50
 * @param <E> the type of the bounds that define the interval
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class Intervals<E> implements Serializable {

	private static final long serialVersionUID = 8528001196553630862L;
	
	private final List<Interval<E>> intervals;
	
	public Intervals() {
		this.intervals = new ArrayList<>();
	}
	
	public void add(Interval<E> interval) {
		intervals.add(interval);
    }
	
	public boolean contains(E x) {
		for (Interval<E> interval : intervals)
			if (interval.contains(x))
				return true;
		return false;
	}
	
	public int intervalCount() {
		return intervals.size();
	}
	
	public Interval<E> getInterval(int i) {
		return intervals.get(i);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (intervals.size() > 0)
			builder.append(intervals.get(0));
		for (int i = 1; i < intervals.size(); i++)
			builder.append(", ").append(intervals.get(i));
		return builder.toString();
	}
	
}

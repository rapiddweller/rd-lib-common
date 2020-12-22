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

import java.util.Comparator;

/**
 * Compares Intervals by its min value.<br><br>
 * Created: 26.04.2019 23:27:52
 * @since 1.0.12
 * @author Volker Bergmann
 * @param <E> 
 */

public class IntervalMinComparator<E extends Comparable<E>> implements Comparator<Interval<E>> {
	@Override
	public int compare(Interval<E> i1, Interval<E> i2) {
		return i1.getMin().compareTo(i2.getMin());
	}
}

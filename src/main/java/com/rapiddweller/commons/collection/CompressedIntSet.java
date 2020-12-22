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

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.rapiddweller.commons.math.IntRange;

/**
 * Collects int values in a compressed way.
 * Created: 05.10.2010 19:17:30
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class CompressedIntSet {
	
	protected TreeMap<Integer, IntRange> numbers;
	protected long size;
	
	public CompressedIntSet() {
	    this.numbers = new TreeMap<Integer, IntRange>();
	    this.size = 0;
    }

	public void clear() {
		numbers.clear();
		this.size = 0;
    }
	
	public void addAll(int... numbers) {
		for (int number : numbers)
			add(number);
	}

	public void add(int i) {
		if (numbers.isEmpty()) {
			// if the set is empty, insert the number
			insertNumber(i);
			size = 1;
		} else {
			// search the highest entry which is less or equals to i
			Entry<Integer, IntRange> floorEntry = numbers.floorEntry(i);
			IntRange rangeBelow;
			if (floorEntry == null)
				extendRangeAboveOrInsertNumber(i); // no range below found, check above
			else {
				// check found range
				rangeBelow = floorEntry.getValue();
				if (rangeBelow.contains(i))
					return;
				if (rangeBelow.getMax() + 1 == i) {
					// extend found range if applicable
					rangeBelow.setMax(i);
					size++;
					// check if two adjacent ranges can be merged
				    IntRange upperNeighbor = numbers.get(i + 1);
				    if (upperNeighbor != null) {
				    	numbers.remove(i + 1);
				    	rangeBelow.setMax(upperNeighbor.getMax());
				    }
				} else
					extendRangeAboveOrInsertNumber(i);
			}
		}
    }

	private void extendRangeAboveOrInsertNumber(int i) {
	    IntRange rangeAbove = numbers.get(i + 1);
	    if (rangeAbove != null) {
	    	numbers.remove(i + 1);
	    	rangeAbove.setMin(i);
	    	numbers.put(i, rangeAbove);
	    } else
	        insertNumber(i);
	    size++;
    }

	private void insertNumber(int i) {
	    numbers.put(i, new IntRange(i, i));
    }

	public boolean contains(int i) {
		Entry<Integer, IntRange> floorEntry = numbers.floorEntry(i);
		return (floorEntry != null && floorEntry.getValue().contains(i));
    }

	public boolean remove(int i) {
		Entry<Integer, IntRange> floorEntry = numbers.floorEntry(i);
		if (floorEntry == null || !floorEntry.getValue().contains(i)) 
			return false;
		IntRange range = floorEntry.getValue();
		if (i == range.getMax() && range.getMax() > range.getMin()) {
			range.setMax(i - 1);
		} else if (i == range.getMin()) {
			numbers.remove(i);
			if (range.getMax() > i) {
				range.setMin(i + 1);
				numbers.put(i + 1, range);
			}
		} else {
			int max = range.getMax();
			range.setMax(i - 1);
			IntRange range2 = new IntRange(i + 1, max);
			numbers.put(i + 1, range2);
		}
		return true;
    }

	public boolean isEmpty() {
	    return numbers.isEmpty();
    }
	
	public long size() {
		return size;
	}
	
	public Iterator<Integer> iterator() {
		return new CompressedSetIterator();
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    CompressedIntSet that = (CompressedIntSet) obj;
	    return this.equals(that.numbers);
    }
	
    @Override
	public int hashCode() {
		return numbers.hashCode();
	}
    
	@Override
	public String toString() {
	    return numbers.values().toString();
	}
	
	
	
	// Iterator class --------------------------------------------------------------------------------------------------
	
	public class CompressedSetIterator implements Iterator<Integer> {
    	
    	protected Iterator<IntRange> intRangeIterator;
    	protected IntRange currentIntRange;
    	protected Integer lastInt;
    	
    	protected CompressedSetIterator() {
    		intRangeIterator = numbers.values().iterator();
    		currentIntRange = null;
    		lastInt = null;
    	}

	    @Override
		public boolean hasNext() {
	    	if (currentIntRange == null) {
	    		if (intRangeIterator != null && intRangeIterator.hasNext()) {
	    			currentIntRange = intRangeIterator.next();
	    			lastInt = null;
	    			return true;
	    		} else {
	    			intRangeIterator = null;
	    			return false;
	    		}
	    	}
	    	return (lastInt == null || lastInt < currentIntRange.getMax());
	    }

	    @Override
		public Integer next() {
	    	if (intRangeIterator != null && currentIntRange == null) {
	    		if (intRangeIterator.hasNext()) {
	    			currentIntRange = intRangeIterator.next();
	    		} else {
	    			intRangeIterator = null;
	    			currentIntRange = null;
	    		}
	    	}
	    	if (intRangeIterator == null || currentIntRange == null)
    			throw new IllegalStateException("No 'next' value available. Check hasNext() before calling next().");
	    	lastInt = (lastInt != null ? ++lastInt : currentIntRange.getMin());
	    	if (lastInt == currentIntRange.getMax())
	    		currentIntRange = null;
	    	return lastInt;
	    }

	    @Override
		public void remove() {
	    	CompressedIntSet.this.remove(lastInt);
	    }
    }
	
}

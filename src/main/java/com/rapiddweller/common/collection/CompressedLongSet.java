/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

package com.rapiddweller.common.collection;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Set of {@link Long} values which stores subsequent values in a compressed format.
 * Created: 18.10.2010 08:32:15
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class CompressedLongSet {

  protected TreeMap<Long, LongRange> numbers;
  protected long size;

  public CompressedLongSet() {
    this.numbers = new TreeMap<>();
    this.size = 0;
  }

  public void clear() {
    numbers.clear();
    this.size = 0;
  }

  public void addAll(int... numbers) {
    for (int number : numbers) {
      add(number);
    }
  }

  public void add(long i) {
    if (numbers.isEmpty()) {
      // if the set is empty, insert the number
      insertNumber(i);
      size = 1;
    } else {
      // search the highest entry which is less or equals to i
      Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
      LongRange rangeBelow;
      if (floorEntry == null) {
        extendRangeAboveOrInsertNumber(i); // no range below found, check above
      } else {
        // check found range
        rangeBelow = floorEntry.getValue();
        if (rangeBelow.contains(i)) {
          return;
        }
        if (rangeBelow.getMax() + 1 == i) {
          extendRangeBelow(rangeBelow, i);
        } else {
          extendRangeAboveOrInsertNumber(i);
        }
      }
    }
  }

  public boolean contains(long i) {
    Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
    return (floorEntry != null && floorEntry.getValue().contains(i));
  }


  public boolean remove(long i) {
    Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
    if (floorEntry == null || !floorEntry.getValue().contains(i)) {
      return false;
    }
    LongRange range = floorEntry.getValue();
    if (i == range.getMax() && range.getMax() > range.getMin()) {
      range.setMax(i - 1);
    } else if (i == range.getMin()) {
      numbers.remove(i);
      if (range.getMax() > i) {
        range.setMin(i + 1);
        numbers.put(i + 1, range);
      }
    } else {
      long max = range.getMax();
      range.setMax(i - 1);
      LongRange range2 = new LongRange(i + 1, max);
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

  public Iterator<Long> iterator() {
    return new CompressedSetIterator();
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private void extendRangeBelow(LongRange rangeBelow, long indexToInclude) {
    // extend found range if applicable
    rangeBelow.setMax(indexToInclude);
    size++;
    // check if two adjacent ranges can be merged
    LongRange upperNeighbor = numbers.get(indexToInclude + 1);
    if (upperNeighbor != null) {
      numbers.remove(indexToInclude + 1);
      rangeBelow.setMax(upperNeighbor.getMax());
    }
  }

  private void extendRangeAboveOrInsertNumber(long i) {
    LongRange rangeAbove = numbers.get(i + 1);
    if (rangeAbove != null) {
      numbers.remove(i + 1);
      rangeAbove.setMin(i);
      numbers.put(i, rangeAbove);
    } else {
      insertNumber(i);
    }
    size++;
  }

  private void insertNumber(long i) {
    numbers.put(i, new LongRange(i, i));
  }


  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CompressedLongSet that = (CompressedLongSet) obj;
    return this.numbers.equals(that.numbers);
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

  public class CompressedSetIterator implements Iterator<Long> {

    protected Iterator<LongRange> longRangeIterator;
    protected LongRange currentLongRange;
    protected Long lastLong;

    protected CompressedSetIterator() {
      longRangeIterator = numbers.values().iterator();
      currentLongRange = null;
      lastLong = null;
    }

    @Override
    public boolean hasNext() {
      if (currentLongRange == null) {
        if (longRangeIterator != null && longRangeIterator.hasNext()) {
          currentLongRange = longRangeIterator.next();
          lastLong = null;
          return true;
        } else {
          longRangeIterator = null;
          return false;
        }
      }
      return (lastLong == null || lastLong < currentLongRange.max);
    }

    @Override
    public Long next() {
      if (longRangeIterator != null && currentLongRange == null) {
        if (longRangeIterator.hasNext()) {
          currentLongRange = longRangeIterator.next();
        } else {
          longRangeIterator = null;
        }
      }
      if (longRangeIterator == null || currentLongRange == null) {
        throw new NoSuchElementException("No 'next' value available. Check hasNext() before calling next().");
      }
      lastLong = (lastLong != null ? ++lastLong : currentLongRange.min);
      if (lastLong == currentLongRange.max) {
        currentLongRange = null;
      }
      return lastLong;
    }

    @Override
    public void remove() {
      CompressedLongSet.this.remove(lastLong);
    }
  }

}

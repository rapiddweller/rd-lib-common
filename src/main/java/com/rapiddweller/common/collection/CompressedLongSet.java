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

package com.rapiddweller.common.collection;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Set of {@link Long} values which stores subsequent values in a compressed format.
 * Created: 18.10.2010 08:32:15
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class CompressedLongSet {

  /**
   * The Numbers.
   */
  protected TreeMap<Long, LongRange> numbers;
  /**
   * The Size.
   */
  protected long size;

  /**
   * Instantiates a new Compressed long set.
   */
  public CompressedLongSet() {
    this.numbers = new TreeMap<>();
    this.size = 0;
  }

  /**
   * Clear.
   */
  public void clear() {
    numbers.clear();
    this.size = 0;
  }

  /**
   * Add all.
   *
   * @param numbers the numbers
   */
  public void addAll(int... numbers) {
    for (int number : numbers) {
      add(number);
    }
  }

  /**
   * Add.
   *
   * @param i the
   */
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
          // extend found range if applicable
          rangeBelow.setMax(i);
          size++;
          // check if two adjacent ranges can be merged
          LongRange upperNeighbor = numbers.get(i + 1);
          if (upperNeighbor != null) {
            numbers.remove(i + 1);
            rangeBelow.setMax(upperNeighbor.getMax());
          }
        } else {
          extendRangeAboveOrInsertNumber(i);
        }
      }
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

  /**
   * Contains boolean.
   *
   * @param i the
   * @return the boolean
   */
  public boolean contains(long i) {
    Entry<Long, LongRange> floorEntry = numbers.floorEntry(i);
    return (floorEntry != null && floorEntry.getValue().contains(i));
  }


  /**
   * Remove boolean.
   *
   * @param i the
   * @return the boolean
   */
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

  /**
   * Is empty boolean.
   *
   * @return the boolean
   */
  public boolean isEmpty() {
    return numbers.isEmpty();
  }

  /**
   * Size long.
   *
   * @return the long
   */
  public long size() {
    return size;
  }

  /**
   * Iterator iterator.
   *
   * @return the iterator
   */
  public Iterator<Long> iterator() {
    return new CompressedSetIterator();
  }

  // java.lang.Object overrrides -------------------------------------------------------------------------------------

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CompressedLongSet that = (CompressedLongSet) obj;
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

  /**
   * The type Compressed set iterator.
   */
  public class CompressedSetIterator implements Iterator<Long> {

    /**
     * The Long range iterator.
     */
    protected Iterator<LongRange> longRangeIterator;
    /**
     * The Current long range.
     */
    protected LongRange currentLongRange;
    /**
     * The Last long.
     */
    protected Long lastLong;

    /**
     * Instantiates a new Compressed set iterator.
     */
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
          currentLongRange = null;
        }
      }
      if (longRangeIterator == null || currentLongRange == null) {
        throw new IllegalStateException("No 'next' value available. Check hasNext() before calling next().");
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

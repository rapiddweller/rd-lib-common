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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Counts objects.
 * Created: 14.12.2006 18:03:47
 *
 * @param <E> the type of the counted elements
 * @author Volker Bergmann
 */
public class ObjectCounter<E> {

  private final Map<E, AtomicInteger> instances;
  /**
   * The Total count.
   */
  long totalCount;

  /**
   * Instantiates a new Object counter.
   *
   * @param initialCapacity the initial capacity
   */
  public ObjectCounter(int initialCapacity) {
    instances = new HashMap<>(initialCapacity);
    totalCount = 0;
  }

  // interface -------------------------------------------------------------------------------------------------------

  /**
   * Count.
   *
   * @param instance the instance
   */
  public void count(E instance) {
    AtomicInteger counter = instances.get(instance);
    if (counter == null) {
      instances.put(instance, new AtomicInteger(1));
    } else {
      counter.incrementAndGet();
    }
    totalCount++;
  }

  /**
   * Uncount.
   *
   * @param instance the instance
   */
  public void uncount(String instance) {
    AtomicInteger counter = instances.get(instance);
    if (counter == null) {
      throw new IllegalStateException("Cannot uncount: " + instance);
    }
    counter.decrementAndGet();
    totalCount--;
  }

  /**
   * Object set set.
   *
   * @return the set
   */
  public Set<E> objectSet() {
    return instances.keySet();
  }

  /**
   * Gets count.
   *
   * @param instance the instance
   * @return the count
   */
  public int getCount(E instance) {
    AtomicInteger counter = instances.get(instance);
    return (counter != null ? counter.intValue() : 0);
  }

  /**
   * Gets relative count.
   *
   * @param instance the instance
   * @return the relative count
   */
  public double getRelativeCount(E instance) {
    return (double) getCount(instance) / totalCount;
  }

  /**
   * Average count double.
   *
   * @return the double
   */
  public double averageCount() {
    return totalCount / instances.size();
  }

  /**
   * Total count double.
   *
   * @return the double
   */
  public double totalCount() {
    return totalCount;
  }

  /**
   * Gets counts.
   *
   * @return the counts
   */
  public Map<E, AtomicInteger> getCounts() {
    return instances;
  }

  /**
   * Equal distribution boolean.
   *
   * @param tolerance the tolerance
   * @return the boolean
   */
  public boolean equalDistribution(double tolerance) {
    double average = averageCount();
    Collection<AtomicInteger> counts = instances.values();
    for (AtomicInteger count : counts) {
      if (Math.abs((count.doubleValue() - average) / average) > tolerance) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder("[");
    Iterator<Map.Entry<E, AtomicInteger>> iterator = instances.entrySet().iterator();
    if (iterator.hasNext()) {
      Map.Entry<E, AtomicInteger> entry = iterator.next();
      buffer.append(entry.getKey()).append(':').append(entry.getValue());
    }
    while (iterator.hasNext()) {
      Map.Entry<E, AtomicInteger> entry = iterator.next();
      buffer.append(", ").append(entry.getKey()).append(':').append(entry.getValue());
    }
    buffer.append(']');
    return buffer.toString();
  }

}

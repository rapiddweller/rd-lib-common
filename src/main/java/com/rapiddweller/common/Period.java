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

package com.rapiddweller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Defines duration constants on millisecond base.
 * Created: 09.05.2007 19:22:27
 */
public class Period implements Comparable<Period> {

  private static final SortedSet<Period> instances = new TreeSet<>();

  /**
   * The constant MILLISECOND.
   */
  public static final Period MILLISECOND = new Period(1L, "ms");
  /**
   * The constant SECOND.
   */
  public static final Period SECOND = new Period(1000L, "s");
  /**
   * The constant MINUTE.
   */
  public static final Period MINUTE = new Period(60 * 1000L, "m");
  /**
   * The constant HOUR.
   */
  public static final Period HOUR = new Period(60 * 60 * 1000L, "h");
  /**
   * The constant DAY.
   */
  public static final Period DAY = new Period(24 * 60 * 60 * 1000L, "d");
  /**
   * The constant WEEK.
   */
  public static final Period WEEK = new Period(7 * 24 * 60 * 60 * 1000L, "w");
  /**
   * The constant MONTH.
   */
  public static final Period MONTH = new Period(30L * DAY.millis, "M");
  /**
   * The constant QUARTER.
   */
  public static final Period QUARTER = new Period(3L * MONTH.millis, "M");
  /**
   * The constant YEAR.
   */
  public static final Period YEAR = new Period(365L * DAY.millis, "y");

  private final long millis;
  private final String name;

  private Period(long millis, String name) {
    this.millis = millis;
    this.name = name;
    instances.add(this);
  }

  /**
   * Gets millis.
   *
   * @return the millis
   */
  public long getMillis() {
    return millis;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets instances.
   *
   * @return the instances
   */
  public static List<Period> getInstances() {
    return new ArrayList<>(instances);
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return millis == ((Period) o).millis;
  }

  @Override
  public int hashCode() {
    return (int) (millis ^ (millis >>> 32));
  }

  // Comparable interface --------------------------------------------------------------------------------------------

  @Override
  public int compareTo(Period that) {
    return Long.compare(this.millis, that.millis);
  }

  /**
   * Min instance period.
   *
   * @return the period
   */
  public static Period minInstance() {
    return instances.first();
  }

  /**
   * Max instance period.
   *
   * @return the period
   */
  public static Period maxInstance() {
    return instances.last();
  }

}

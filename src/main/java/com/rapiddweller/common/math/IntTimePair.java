/* (c) Copyright 2020 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.math;

import com.rapiddweller.common.RegexUtil;

import java.time.LocalTime;

/**
 * Combines an int and a {@link LocalTime} to a pair.<br/><br/>
 * Created: 27.02.2020 18:35:44
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class IntTimePair {

  private static final String TIMED_INT_PATTERN = "^(\\-?\\d+) (\\d{2,2}:\\d{2})$";

  private final int index;
  private final LocalTime time;

  /**
   * Instantiates a new Int time pair.
   *
   * @param index the index
   * @param time  the time
   */
  public IntTimePair(int index, LocalTime time) {
    this.index = index;
    this.time = time;
  }

  /**
   * Gets index.
   *
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * Gets time.
   *
   * @return the time
   */
  public LocalTime getTime() {
    return time;
  }

  @Override
  public String toString() {
    return index + " " + time;
  }

  /**
   * Parse int time pair.
   *
   * @param spec the spec
   * @return the int time pair
   */
  public static IntTimePair parse(String spec) {
    String[] groups = RegexUtil.parse(spec, TIMED_INT_PATTERN);
    if (groups == null) {
      throw new IllegalArgumentException("No match for " + spec);
    }
    int index = Integer.parseInt(groups[0]);
    LocalTime time = LocalTime.parse(groups[1]);
    return new IntTimePair(index, time);
  }

}

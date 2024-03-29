/* (c) Copyright 2020 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.math;

import com.rapiddweller.common.RegexUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.time.LocalTime;

/**
 * Combines an int and a {@link LocalTime} to a pair.<br/><br/>
 * Created: 27.02.2020 18:35:44
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class IntTimePair {

  private static final String TIMED_INT_PATTERN = "^(\\-?\\d+) (\\d{2,2}:\\d{2})$";

  private final int index;
  private final LocalTime time;

  public IntTimePair(int index, LocalTime time) {
    this.index = index;
    this.time = time;
  }

  public int getIndex() {
    return index;
  }

  public LocalTime getTime() {
    return time;
  }

  @Override
  public String toString() {
    return index + " " + time;
  }

  public static IntTimePair parse(String spec) {
    String[] groups = RegexUtil.parse(spec, TIMED_INT_PATTERN);
    if (groups == null) {
      throw ExceptionFactory.getInstance().illegalArgument("No match for " + spec);
    }
    int index = Integer.parseInt(groups[0]);
    LocalTime time = LocalTime.parse(groups[1]);
    return new IntTimePair(index, time);
  }

}

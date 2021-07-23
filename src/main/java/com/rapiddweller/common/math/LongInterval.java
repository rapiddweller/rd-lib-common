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

import com.rapiddweller.common.comparator.LongComparator;

/**
 * {@link Interval} implementation using {@link Long} as generic type.
 * Created: 30.12.2011 22:08:21
 *
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class LongInterval extends Interval<Long> {

  private static final long serialVersionUID = -7172324515734804326L;

  /**
   * Instantiates a new Long interval.
   *
   * @param min the min
   * @param max the max
   */
  public LongInterval(long min, long max) {
    this(min, true, max, true);
  }

  /**
   * Instantiates a new Long interval.
   *
   * @param min          the min
   * @param minInclusive the min inclusive
   * @param max          the max
   * @param maxInclusive the max inclusive
   */
  public LongInterval(long min, boolean minInclusive, long max, boolean maxInclusive) {
    super(min, minInclusive, max, maxInclusive, new LongComparator());
  }

}

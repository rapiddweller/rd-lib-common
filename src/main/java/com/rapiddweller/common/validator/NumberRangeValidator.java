/*
 * Copyright (C) 2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

package com.rapiddweller.common.validator;

import com.rapiddweller.common.Validator;
import com.rapiddweller.common.comparator.NumberComparator;
import com.rapiddweller.common.math.Interval;

/**
 * Validates a number range, optionally excluding one or both border values.<br/><br/>
 * Created: 01.10.2021 08:15:08
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class NumberRangeValidator<E extends Number> implements Validator<E> {

  private final Interval<E> interval;

  public NumberRangeValidator(E min, boolean minInclusive, E max, boolean maxInclusive) {
    this.interval = new Interval<>(min, minInclusive, max, maxInclusive, new NumberComparator<>());
  }

  @Override
  public boolean valid(E object) {
    return interval.contains(object);
  }

  @Override
  public String toString() {
    return "NumberRangeValidator{" + interval + '}';
  }

}

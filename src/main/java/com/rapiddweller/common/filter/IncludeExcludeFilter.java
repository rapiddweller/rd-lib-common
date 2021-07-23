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

package com.rapiddweller.common.filter;

import com.rapiddweller.common.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Multi-step inclusion/exclusion filter. Note that a sequence of inclusions forms an intersection, not a union.
 * For including a union of filter result sets, include an OrFilter with the respective single filters.
 * Created: 08.06.2012 19:40:57
 *
 * @param <E> the type of objects to filter
 * @author Volker Bergmann
 * @since 0.5.16
 */
public class IncludeExcludeFilter<E> implements Filter<E> {

  private final List<FilterStep<E>> steps;

  /**
   * Instantiates a new Include exclude filter.
   */
  public IncludeExcludeFilter() {
    this.steps = new ArrayList<>();
  }

  /**
   * Add inclusion include exclude filter.
   *
   * @param filter the filter
   * @return the include exclude filter
   */
  public IncludeExcludeFilter<E> addInclusion(Filter<E> filter) {
    steps.add(new FilterStep<>(filter, true));
    return this;
  }

  /**
   * Add exclusion include exclude filter.
   *
   * @param filter the filter
   * @return the include exclude filter
   */
  public IncludeExcludeFilter<E> addExclusion(Filter<E> filter) {
    steps.add(new FilterStep<>(filter, false));
    return this;
  }

  @Override
  public boolean accept(E candidate) {
    for (FilterStep<E> step : steps) {
      if (step.inclusion && !step.filter.accept(candidate)) {
        return false;
      }
      if (!step.inclusion && step.filter.accept(candidate)) {
        return false;
      }
    }
    return true;
  }

  private static class FilterStep<E> {

    /**
     * The Inclusion.
     */
    public final boolean inclusion;
    /**
     * The Filter.
     */
    public final Filter<E> filter;

    /**
     * Instantiates a new Filter step.
     *
     * @param filter    the filter
     * @param inclusion the inclusion
     */
    public FilterStep(Filter<E> filter, boolean inclusion) {
      this.inclusion = inclusion;
      this.filter = filter;
    }

  }

}

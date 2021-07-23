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
import java.util.Arrays;

/**
 * Abstract parent filter which combines several filter components.
 * Created: 08.06.2012 20:29:58
 *
 * @param <E> the type of objects to be filtered
 * @author Volker Bergmann
 * @since 0.5.16
 */
public abstract class CompositeFilter<E> implements Filter<E> {

  /**
   * The Components.
   */
  protected ArrayList<Filter<E>> components;

  /**
   * Instantiates a new Composite filter.
   *
   * @param components the components
   */
  @SafeVarargs
  protected CompositeFilter(Filter<E>... components) {
    this.components = new ArrayList<>();
    this.components.addAll(Arrays.asList(components));
  }

  /**
   * Add composite filter.
   *
   * @param filter the filter
   * @return the composite filter
   */
  public CompositeFilter<E> add(Filter<E> filter) {
    this.components.add(filter);
    return this;
  }

}

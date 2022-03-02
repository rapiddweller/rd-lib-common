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

/**
 * Combines {@link Filter} components in an AND manner: A candidate value is only accepted if each of the
 * components accepts it.
 * Created: 08.06.2012 20:36:23
 *
 * @param <E> the type of objects to be filtered
 * @author Volker Bergmann
 * @since 0.5.16
 */
public class AndFilter<E> extends CompositeFilter<E> {

  /**
   * Instantiates a new And filter.
   *
   * @param components the components
   */
  @SafeVarargs
  public AndFilter(Filter<E>... components) {
    super(components);
  }

  @Override
  public boolean accept(E candidate) {
    for (Filter<E> component : components) {
      if (component != null && !component.accept(candidate)) {
        return false;
      }
    }
    return true;
  }

}

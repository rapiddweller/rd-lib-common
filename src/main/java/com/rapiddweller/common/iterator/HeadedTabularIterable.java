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

package com.rapiddweller.common.iterator;

import java.util.Iterator;

/**
 * Special type of {@link Iterable} which proxies another {@link Iterable} and
 * wraps its produced {@link Iterator}s into {@link TabularIterator}s.
 * Created: 26.01.2012 18:28:06
 *
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class HeadedTabularIterable extends IterableProxy<Object[]> implements TabularIterable {

  /**
   * Instantiates a new Headed tabular iterable.
   *
   * @param source the source
   */
  public HeadedTabularIterable(Iterable<Object[]> source) {
    super(source);
  }

  @Override
  public TabularIterator iterator() {
    return new HeadedTabularIterator(super.iterator());
  }

}

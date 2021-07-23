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
 * Iterator that may iterate forward and backward.
 * Created: 08.05.2007 18:03:52
 *
 * @param <E> the type to iterate
 * @author Volker Bergmann
 */
public interface BidirectionalIterator<E> extends Iterator<E> {
  /**
   * First e.
   *
   * @return the e
   */
  E first();

  /**
   * Has previous boolean.
   *
   * @return the boolean
   */
  boolean hasPrevious();

  /**
   * Previous e.
   *
   * @return the e
   */
  E previous();

  /**
   * Last e.
   *
   * @return the e
   */
  E last();
}

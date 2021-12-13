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

package com.rapiddweller.common.comparator;

import java.util.Comparator;

/**
 * Abstract Comparator proxy.
 * Created: 22.05.2007 07:53:56
 * @param <E> the type of objects to be compared
 * @author Volker Bergmann
 */
public abstract class ComparatorProxy<E> implements Comparator<E> {

  private final Comparator<E> realComparator;

  protected ComparatorProxy(Comparator<E> realComparator) {
    this.realComparator = realComparator;
  }

  @Override
  public int compare(E e1, E e2) {
    return realComparator.compare(e1, e2);
  }
}

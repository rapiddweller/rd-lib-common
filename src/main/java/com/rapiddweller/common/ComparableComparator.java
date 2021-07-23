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

package com.rapiddweller.common;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Implementation of Comparator&lt;Comparable&gt;.
 * Created: 06.01.2005 20:12:40
 *
 * @param <E> the type of objects to compare
 * @author Volker Bergmann
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ComparableComparator<E extends Comparable> implements Comparator<E>, Serializable {

  private static final long serialVersionUID = 8000187188587657603L;

  @Override
  public int compare(E o1, E o2) {
    return o1.compareTo(o2);
  }

}

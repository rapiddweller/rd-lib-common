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

package com.rapiddweller.common.collection;

import com.rapiddweller.common.Named;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Map} which offers convenience methods for managing {@link Named} objects
 * in a Map semantics by their <code>name</code> property.
 * Created: 06.06.2012 20:15:36
 * @param <E> the type of the collection's elements
 * @author Volker Bergmann
 * @since 0.5.16
 */
public class NameMap<E extends Named> extends HashMap<String, E> {

  public NameMap() {
    super();
  }

  public NameMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public NameMap(int initialCapacity) {
    super(initialCapacity);
  }

  public NameMap(Collection<E> prototype) {
    super(prototype.size());
    for (E item : prototype) {
      super.put(item.getName(), item);
    }
  }

  @SafeVarargs
  public NameMap(E... elements) {
    super(elements.length);
    for (E element : elements) {
      super.put(element.getName(), element);
    }
  }

  public void put(E item) {
    super.put(item.getName(), item);
  }

}

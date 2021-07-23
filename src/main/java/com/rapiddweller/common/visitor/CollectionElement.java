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

package com.rapiddweller.common.visitor;

import com.rapiddweller.common.Element;
import com.rapiddweller.common.Visitor;

import java.util.Collection;

/**
 * Element implementation that wraps a Java collection.
 * Created: 04.02.2007 09:27:19
 *
 * @param <E> the collection element type
 * @author Volker Bergmann
 */
public class CollectionElement<E> implements Element<E> {

  private final Collection<E> collection;

  /**
   * Instantiates a new Collection element.
   *
   * @param collection the collection
   */
  public CollectionElement(Collection<E> collection) {
    this.collection = collection;
  }

  @Override
  public void accept(Visitor<E> visitor) {
    for (E item : collection) {
      visitor.visit(item);
    }
  }

}

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

package com.rapiddweller.common.tree;

/**
 * Defines a contract of mapping arbitrary objects to a parent/child structure.
 * Created: 31.07.2007 06:32:11
 *
 * @param <I> the id type
 * @param <V> the element type
 * @author Volker Bergmann
 */
public interface ChildModel<I, V> {
  /**
   * Gets id.
   *
   * @param element the element
   * @return the id
   */
  I getId(V element);

  /**
   * Gets parent id.
   *
   * @param child the child
   * @return the parent id
   */
  I getParentId(V child);
}

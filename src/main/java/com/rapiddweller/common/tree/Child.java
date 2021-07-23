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
 * Represents an identifiable Object that knows the identifier of its parent.
 * Created: 31.07.2007 06:54:28
 *
 * @param <I> the id type
 * @author Volker Bergmann
 */
public interface Child<I> {
  /**
   * Gets id.
   *
   * @return the id
   */
  I getId();

  /**
   * Gets parent id.
   *
   * @return the parent id
   */
  I getParentId();
}

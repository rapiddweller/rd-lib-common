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
 * The default implementation of the ChildModel interface
 * which expects its elements to implement the Child interface.
 * Created: 31.07.2007 06:55:23
 *
 * @param <I> the id type
 * @param <V> the element type
 * @author Volker Bergmann
 */
public class DefaultChildModel<I, V extends Child<I>> implements ChildModel<I, V> {

  @Override
  public I getId(V element) {
    return (element != null ? element.getId() : null);
  }

  @Override
  public I getParentId(V child) {
    return child.getParentId();
  }
}

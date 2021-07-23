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

package com.rapiddweller.common.wrapper;

import com.rapiddweller.common.Named;

/**
 * Wraps a {@link Named} object.
 * Created: 20.06.2013 06:48:24
 *
 * @param <E> the object type to wrap
 * @author Volker Bergmann
 * @since 0.5.24
 */
public class NamedWrapper<E> implements Named {

  private final String name;
  private E wrapped;

  /**
   * Instantiates a new Named wrapper.
   *
   * @param name    the name
   * @param wrapped the wrapped
   */
  public NamedWrapper(String name, E wrapped) {
    this.name = name;
    this.wrapped = wrapped;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets wrapped.
   *
   * @return the wrapped
   */
  public E getWrapped() {
    return wrapped;
  }

  /**
   * Sets wrapped.
   *
   * @param wrapped the wrapped
   */
  public void setWrapped(E wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public String toString() {
    return name;
  }

}

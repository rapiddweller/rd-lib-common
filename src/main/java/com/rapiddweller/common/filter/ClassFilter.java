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

package com.rapiddweller.common.filter;

import com.rapiddweller.common.Filter;

/**
 * {@link Filter} implementation which accepts objects that implement a certain class ({@link #acceptedClass})
 * or (if {@link #acceptingSubClasses} is true) a sub class.
 * Created: 07.06.2011 14:01:42
 *
 * @param <E> the type of objects to filter
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ClassFilter<E> implements Filter<E> {

  private final Class<? extends E> acceptedClass;
  private final boolean acceptingSubClasses;

  /**
   * Instantiates a new Class filter.
   *
   * @param acceptedClass       the accepted class
   * @param acceptingSubClasses the accepting sub classes
   */
  public ClassFilter(Class<? extends E> acceptedClass, boolean acceptingSubClasses) {
    this.acceptedClass = acceptedClass;
    this.acceptingSubClasses = acceptingSubClasses;
  }

  @Override
  public boolean accept(E candidate) {
    if (candidate == null) {
      return false;
    } else if (acceptingSubClasses) {
      return acceptedClass.isAssignableFrom(candidate.getClass());
    } else {
      return acceptedClass == candidate.getClass();
    }
  }

}

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

package com.rapiddweller.common.operation;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.Operation;

/**
 * Determines the elements common to all array arguments.
 *
 * @param <E> the argument and result type of the operation
 * @author Volker Bergmann
 * @since 0.4.0
 */
public class ArrayIntersectionOperation<E> implements Operation<E[], E[]> {

  private final Class<E> componentType;

  /**
   * Instantiates a new Array intersection operation.
   *
   * @param componentType the component type
   */
  public ArrayIntersectionOperation(Class<E> componentType) {
    this.componentType = componentType;
  }

  @SafeVarargs
  @Override
  public final E[] perform(E[]... sources) {
    return ArrayUtil.commonElements(componentType, sources);
  }

}

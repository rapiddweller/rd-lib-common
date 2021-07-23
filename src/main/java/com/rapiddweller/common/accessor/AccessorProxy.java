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

package com.rapiddweller.common.accessor;

import com.rapiddweller.common.Accessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Proxy of an accessor.
 * Created: 08.03.2006 15:44:51
 *
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 * @author Volker Bergmann
 */
public abstract class AccessorProxy<C, V> implements DependentAccessor<C, V> {

  /**
   * The Real accessor.
   */
  protected Accessor<C, V> realAccessor;

  /**
   * Instantiates a new Accessor proxy.
   *
   * @param realAccessor the real accessor
   */
  public AccessorProxy(Accessor<C, V> realAccessor) {
    this.realAccessor = realAccessor;
  }

  @Override
  public V getValue(C item) {
    return realAccessor.getValue(item);
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public List<? extends Accessor<?, ?>> getDependencies() {
    if (realAccessor instanceof DependentAccessor) {
      return ((DependentAccessor) realAccessor).getDependencies();
    } else {
      return new ArrayList();
    }
  }

}

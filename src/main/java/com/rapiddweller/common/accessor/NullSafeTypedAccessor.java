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

/**
 * Typed Accessor that returns a default value if invoked on argument null.
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object            Created: 22.02.2006 20:08:36
 * @author Volker Bergmann
 */
public class NullSafeTypedAccessor<C, V> extends NullSafeAccessor<C, V> implements TypedAccessor<C, V> {

  public NullSafeTypedAccessor(TypedAccessor<C, V> realAccessor, V nullValue) {
    super(realAccessor, nullValue);
  }

  @Override
  public Class<? extends V> getValueType() {
    return ((TypedAccessor<C, V>) realAccessor).getValueType();
  }

}

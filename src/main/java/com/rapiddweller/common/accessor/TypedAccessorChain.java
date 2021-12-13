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
 * Consecutively invokes a series of accessors
 * using the result value of each invocation as input value for the next.
 * Created: 21.07.2007 07:02:07
 * @author Volker Bergmann
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class TypedAccessorChain implements TypedAccessor {

  private final TypedAccessor[] subAccessors;

  public TypedAccessorChain(TypedAccessor[] realAccessors) {
    this.subAccessors = realAccessors;
  }

  public TypedAccessor[] getSubAccessors() {
    return subAccessors;
  }

  @Override
  public Class<?> getValueType() {
    return subAccessors[subAccessors.length - 1].getValueType();
  }

  @Override
  public Object getValue(Object target) {
    Object result = target;
    for (TypedAccessor accessor : subAccessors) {
      result = accessor.getValue(result);
      if (result == null) {
        return null;
      }
    }
    return result;
  }

}

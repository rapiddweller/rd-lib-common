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

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.BeanUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Converts arrays and collections to arrays and other object to an array of size 1.
 * Note: The target type is not declared Object[], since we also want to create byte[].
 * Created: 26.08.2007 16:01:38
 * @author Volker Bergmann
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ToArrayConverter extends ThreadSafeConverter {

  private final Class componentType;
  private boolean nullToEmpty;

  // constructors ----------------------------------------------------------------------------------------------------

  public ToArrayConverter() {
    this(Object.class);
  }

  public ToArrayConverter(Class componentType) {
    this(componentType, true);
  }

  public ToArrayConverter(Class componentType, boolean nullToEmpty) {
    super(Object.class, ArrayUtil.arrayType(componentType));
    this.componentType = componentType;
    this.nullToEmpty = nullToEmpty;
  }

  // properties ------------------------------------------------------------------------------------------------------

  public void setNullToEmpty(boolean nullToEmpty) {
    this.nullToEmpty = nullToEmpty;
  }

  // Converter interface implementation ------------------------------------------------------------------------------

  @Override
  public Object convert(Object sourceValue) {
    return convert(sourceValue, componentType, nullToEmpty);
  }

  // static utility methods ------------------------------------------------------------------------------------------

  public static Object convert(Object sourceValue, Class componentType) {
    return convert(sourceValue, componentType, true);
  }

  @SuppressWarnings("cast")
  public static Object convert(Object sourceValue, Class componentType, boolean nullToEmpty) {
    if (sourceValue == null) {
      return (nullToEmpty ? ArrayUtil.buildArrayOfType(componentType) : null);
    }
    if (sourceValue instanceof Collection) {
      Collection col = (Collection) sourceValue;
      Object[] array = (Object[]) Array.newInstance(componentType, col.size());
      int count = 0;
      for (Object item : col) {
        array[count++] = item;
      }
      return array;
    } else if (componentType == byte.class) {
      Method method = BeanUtil.getMethod(sourceValue.getClass(), "getBytes");
      return BeanUtil.invoke(sourceValue, method, null);
    } else if (sourceValue.getClass().isArray()) {
      return ArrayUtil.buildArrayOfType(componentType, (Object[]) sourceValue);
    } else {
      return ArrayUtil.buildArrayOfType(componentType, sourceValue);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + componentType + ")";
  }

}

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

package com.rapiddweller.common.bean;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Retrieves the value of a JavaBean property with knowledge of the bean type.
 * Created: 21.07.2007 10:18:00
 * @param <E> the object type to access
 * @author Volker Bergmann
 */
class TypedPropertyAccessor<E> implements PropertyAccessor<E, Object> {

  private final String propertyName;
  private Method accessorMethod;
  private final boolean strict;

  public TypedPropertyAccessor(Class<E> beanClass, String propertyName, boolean strict) {
    this.propertyName = propertyName;
    this.strict = strict;
    try {
      PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(beanClass, propertyName);
      if (propertyDescriptor == null) {
        if (strict) {
          throw ExceptionFactory.getInstance().configurationError("No property '" + propertyName + "' found in " + beanClass);
        }
      } else {
        this.accessorMethod = propertyDescriptor.getReadMethod();
        if (accessorMethod == null) {
          throw ExceptionFactory.getInstance().configurationError("No read method for property '" + propertyName + "'" +
              " found on " + beanClass);
        }
      }
    } catch (SecurityException e) {
      throw ExceptionFactory.getInstance().accessFailed("Failed to create " + getClass().getName() +
          " for " + propertyName, e);
    }
  }

  @Override
  public Object getValue(Object object) {
    if (object == null) {
      if (strict) {
        throw ExceptionFactory.getInstance().accessFailed(
            "Trying to get property value '" + propertyName + "' from null", null);
      } else {
        return null;
      }
    }
    try {
      return (accessorMethod != null ? accessorMethod.invoke(object) : null);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().accessFailed("Error accessing property " + propertyName, e);
    }
  }

  @Override
  public Class<?> getValueType() {
    return (accessorMethod != null ? accessorMethod.getReturnType() : null);
  }

  @Override
  public String getPropertyName() {
    return propertyName;
  }

}

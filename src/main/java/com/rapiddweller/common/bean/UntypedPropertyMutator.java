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
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.MutationFailed;
import com.rapiddweller.common.converter.AnyConverter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Mutates the value of a property on a JavaBean target object.
 * Created: 21.07.2007 09:01:19
 * @author Volker Bergmann
 */
public class UntypedPropertyMutator extends AbstractNamedMutator {

  private final boolean required;
  private final boolean autoConvert;

  public UntypedPropertyMutator(String propertyName, boolean required, boolean autoConvert) {
    super(propertyName);
    this.required = required;
    this.autoConvert = autoConvert;
  }

  @Override
  public void setValue(Object target, Object value) throws MutationFailed {
    setValue(target, value, this.required, this.autoConvert);
  }

  public void setValue(Object bean, Object propertyValue, boolean required, boolean autoConvert) throws MutationFailed {
    if (!checkBean(bean, required)) {
      return;
    }
    PropertyDescriptor propertyDescriptor = checkProperty(bean, required);
    if (propertyDescriptor == null) {
      return;
    }
    Method writeMethod = checkWriteMethod(bean, required, propertyDescriptor);
    if (writeMethod == null) {
      return;
    }
    if (autoConvert && propertyValue != null) {
      Class<?> sourceType = propertyValue.getClass();
      Class<?> targetType = writeMethod.getParameterTypes()[0];
      try {
        if (!targetType.isAssignableFrom(sourceType)) {
          propertyValue = AnyConverter.convert(propertyValue, targetType);
        }
      } catch (ConversionException e) {
        throw ExceptionFactory.getInstance().configurationError("Error converting " + propertyValue, e);
      }
    }
    BeanUtil.invoke(bean, writeMethod, new Object[] {propertyValue});
  }

  private boolean checkBean(Object bean, boolean required) {
    if (bean == null) {
      if (required) {
        throw ExceptionFactory.getInstance().mutationFailed(
            "Cannot set a property on 'null'", null);
      } else {
        return false;
      }
    }
    return true;
  }
  private PropertyDescriptor checkProperty(Object bean, boolean required) {
    PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(bean.getClass(), name);
    if (propertyDescriptor == null) {
      if (required) {
        throw ExceptionFactory.getInstance().mutationFailed(
            "property '" + name + "' not found in class " + bean.getClass(), null);
      } else {
        return null;
      }
    }
    return propertyDescriptor;
  }

  private Method checkWriteMethod(Object bean, boolean required, PropertyDescriptor propertyDescriptor) {
    Method writeMethod = propertyDescriptor.getWriteMethod();
    if (writeMethod == null) {
      if (required) {
        throw ExceptionFactory.getInstance().mutationFailed(
            "No write method found for property '" + name + "' in class " + bean.getClass(), null);
      } else {
        return null;
      }
    }
    return writeMethod;
  }

}

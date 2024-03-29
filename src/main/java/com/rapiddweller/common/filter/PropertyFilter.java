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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Condition;
import com.rapiddweller.common.Filter;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Filter that matches a JavaBean by checking a Condition for one of its property values.
 * Created: 04.02.2007 00:47:13
 * @param <E> the bean type
 * @param <P> the property type
 * @author Volker Bergmann
 * @since 0.1
 */
public class PropertyFilter<E, P> implements Filter<E> {

  private final Method propertyReadMethod;
  private final Condition<P> propertyCondition;

  /** Instantiates a new Property filter. */
  public PropertyFilter(Class<E> type, String propertyName, Condition<P> propertyCondition) {
    try {
      this.propertyReadMethod = type.getMethod(BeanUtil.readMethodName(propertyName, type));
      this.propertyCondition = propertyCondition;
    } catch (NoSuchMethodException e) {
      throw ExceptionFactory.getInstance().illegalArgument(
          "Error setting up PropertyFilter for property " + propertyName, e); // How could this ever happen!
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean accept(E candidate) {
    try {
      P propertyValue = (P) propertyReadMethod.invoke(candidate);
      return propertyCondition.evaluate(propertyValue);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw configurationException(e, propertyReadMethod);
    }
  }

  public static ConfigurationError configurationException(Exception cause, Method method) {
    String message;
    if (cause instanceof IllegalAccessException) {
      message = "No access to method: " + method;
    } else if (cause instanceof InvocationTargetException) {
      message = "Internal exception in method: " + method;
    } else if (cause instanceof IntrospectionException) {
      message = "Internal exception in method: " + method;
    } else {
      message = cause.getMessage();
    }
    return new ConfigurationError(message, cause);
  }

}

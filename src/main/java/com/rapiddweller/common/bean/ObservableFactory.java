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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates {@link ObservableBean} implementations from interfaces.
 * Created at 17.07.2008 14:57:53
 * @author Volker Bergmann
 * @since 0.4.5
 */
public class ObservableFactory {

  private ObservableFactory() {
    // private constructor to prevent instantiation of this utility class
  }

  @SuppressWarnings("unchecked")
  public static <E extends ObservableBean> E create(Class<E> type) {
    if (!type.isInterface()) {
      throw ExceptionFactory.getInstance().configurationError("Not an interface: " + type);
    }
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    ObservableBeanInvocationHandler handler = new ObservableBeanInvocationHandler(type);
    return (E) Proxy.newProxyInstance(classLoader, new Class[] {type}, handler);
  }

  private static class ObservableBeanInvocationHandler implements InvocationHandler {

    private final Map<String, Object> propertyValues = new HashMap<>();
    private PropertyChangeSupport support;
    private final Class<?> type;

    public ObservableBeanInvocationHandler(Class<?> type) {
      this.type = type;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
      if (support == null) {
        this.support = new PropertyChangeSupport(proxy);
      }
      String methodName = method.getName();
      if (methodName.startsWith("get") && ArrayUtil.isEmpty(args)) {
        String propertyName = methodName.substring(3);
        propertyName = StringUtil.uncapitalize(propertyName);
        return propertyValues.get(propertyName);
      } else if (methodName.startsWith("set")) {
        String propertyName = methodName.substring(3);
        propertyName = StringUtil.uncapitalize(propertyName);
        Object oldValue = propertyValues.get(propertyName);
        propertyValues.put(propertyName, args[0]);
        support.firePropertyChange(propertyName, oldValue, args[0]);
      } else if ("addPropertyChangeListener".equals(methodName)) {
        invokeAddPropertyChangeListener(args);
      } else if ("removePropertyChangeListener".equals(methodName)) {
        invokeRemovePropertyChangeListener(args);
      } else if ("equals".equals(methodName)) {
        return invokeEquals(proxy, args[0]);
      } else if ("hashCode".equals(methodName)) {
        return propertyValues.hashCode();
      } else if ("toString".equals(methodName)) {
        return type.getName() + propertyValues;
      } else {
        throw ExceptionFactory.getInstance().programmerUnsupported("Operation not supported: " + method);
      }
      return null;
    }

    private void invokeAddPropertyChangeListener(Object[] args) {
      if (args.length == 2) {
        support.addPropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
      } else {
        support.addPropertyChangeListener((PropertyChangeListener) args[0]);
      }
    }

    private void invokeRemovePropertyChangeListener(Object[] args) {
      if (args.length == 2) {
        support.removePropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
      } else {
        support.removePropertyChangeListener((PropertyChangeListener) args[0]);
      }
    }

    private Object invokeEquals(Object proxy, Object other) {
      if (proxy == other) {
        return true;
      }
      if (other == null || !proxy.getClass().equals(other.getClass())) {
        return false;
      }
      ObservableBeanInvocationHandler otherHandler = (ObservableBeanInvocationHandler) Proxy.getInvocationHandler(proxy);
      return (this.propertyValues.equals(otherHandler.propertyValues));
    }
  }

}

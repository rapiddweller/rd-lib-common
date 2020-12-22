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
package com.rapiddweller.commons.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.rapiddweller.commons.ArrayUtil;
import com.rapiddweller.commons.ConfigurationError;
import com.rapiddweller.commons.StringUtil;

/**
 * Creates {@link ObservableBean} implementations from interfaces.
 * Created at 17.07.2008 14:57:53
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class ObservableFactory {
	
	@SuppressWarnings("unchecked")
    public static <E extends ObservableBean> E create(Class<E> type) {
		if (!type.isInterface())
			throw new ConfigurationError("Not an interface: " + type);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ObservableBeanInvocationHandler handler = new ObservableBeanInvocationHandler(type);
		E bean = (E) Proxy.newProxyInstance(classLoader, new Class[] { type }, handler);
		return bean;
	}
	
	private static class ObservableBeanInvocationHandler implements InvocationHandler {
		
		private Map<String, Object> propertyValues = new HashMap<String, Object>();
		private PropertyChangeSupport support;
		private Class<?> type;
		
		public ObservableBeanInvocationHandler(Class<?> type) {
			this.type = type;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (support == null)
				this.support = new PropertyChangeSupport(proxy);
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
				if (args.length == 2)
					support.addPropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
				else
					support.addPropertyChangeListener((PropertyChangeListener) args[0]);
			} else if ("removePropertyChangeListener".equals(methodName)) {
				if (args.length == 2)
					support.removePropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
				else
					support.removePropertyChangeListener((PropertyChangeListener) args[0]);
			} else if ("equals".equals(methodName)) {
				Object other = args[0];
				if (proxy == other)
					return true;
				if (other == null)
					return false;
				if (!proxy.getClass().equals(other.getClass()))
					return false;
				ObservableBeanInvocationHandler otherHandler = (ObservableBeanInvocationHandler) Proxy.getInvocationHandler(proxy);
				return (this.propertyValues.equals(otherHandler.propertyValues));
			} else if ("hashCode".equals(methodName)) {
				return propertyValues.hashCode();
			} else if ("toString".equals(methodName)) {
				return type.getName() + propertyValues;
			} else
				throw new UnsupportedOperationException("Operation not supported: " + method);
			return null;
		}
		
	}
}

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
import com.rapiddweller.common.ConfigurationError;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Retrieves values of a JavaBean property without knowing the bean type.
 * Created: 21.07.2007 10:18:09
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class UntypedPropertyAccessor implements PropertyAccessor {

    private final String propertyName;
    private Class<?> propertyType;
    private final boolean strict;

    public UntypedPropertyAccessor(String propertyName, boolean strict) {
        this.propertyName = propertyName;
        this.propertyType = null;
        this.strict = strict;
    }

    @Override
	public Object getValue(Object bean) {
		PropertyDescriptor descriptor = getPropertyDescriptor(bean, propertyName, strict);
		if (descriptor == null)
			return null;
        this.propertyType = descriptor.getPropertyType();
        Method readMethod = getReadMethod(descriptor, bean, strict);
        if (readMethod == null)
        	return null;
		return BeanUtil.invoke(bean, readMethod, null);
    }

    @Override
	public Class<?> getValueType() {
        return propertyType;
    }

    @Override
	public String getPropertyName() {
        return propertyName;
    }
    
    
    // static implementation -------------------------------------------------------------------------------------------
    
    public static Object getValue(Object bean, String propertyName, boolean strict) {
		PropertyDescriptor descriptor = getPropertyDescriptor(bean, propertyName, strict);
		if (descriptor == null)
			return null;
        Method readMethod = getReadMethod(descriptor, bean, strict);
        if (readMethod == null)
        	return null;
		return BeanUtil.invoke(bean, readMethod, null);
	}
    
    
    // private helper methods ------------------------------------------------------------------------------------------

	private static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName, boolean strict) {
		if (bean == null)
            if (strict)
                throw new IllegalArgumentException("Trying to get property value '" + propertyName + "' from null");
            else
                return null;
        PropertyDescriptor descriptor = BeanUtil.getPropertyDescriptor(bean.getClass(), propertyName);
        if (descriptor == null)
            if (strict)
                throw new ConfigurationError("No property '" + propertyName + "' found in class " + bean.getClass());
            else
                return null;
		return descriptor;
	}

	private static Method getReadMethod(PropertyDescriptor descriptor, Object bean, boolean strict) {
		Method readMethod = descriptor.getReadMethod();
        if (readMethod == null)
            if (strict)
                throw new ConfigurationError("No reader for property '" + descriptor.getName() + "' found in class " + bean.getClass());
            else
                return null;
		return readMethod;
	}

}

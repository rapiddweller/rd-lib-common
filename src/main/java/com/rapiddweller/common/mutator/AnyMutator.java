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
package com.rapiddweller.common.mutator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Composite;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Escalator;
import com.rapiddweller.common.LoggerEscalator;
import com.rapiddweller.common.UpdateFailedException;
import com.rapiddweller.common.accessor.FeatureAccessor;
import com.rapiddweller.common.converter.AnyConverter;

/**
 * Mutator implementation for graphs of any object types.
 * Created: 31.01.2008 20:15:11
 * @since 0.3.0
 * @author Volker Bergmann
 */
public class AnyMutator implements NamedMutator {
    
    private static final Escalator escalator = new LoggerEscalator();
    
    private final String path;
    private final boolean required;
    private final boolean autoConvert;
    
    public AnyMutator(String path) {
        this(path, true, false);
    }

    public AnyMutator(String path, boolean required, boolean autoConvert) {
        this.path = Assert.notNull(path, "path");
        this.required = required;
        this.autoConvert = autoConvert;
    }

	@Override
	public String getName() {
		return path;
	}
    
    @Override
	public void setValue(Object target, Object value) throws UpdateFailedException {
        setValue(target, path, value, required, autoConvert);
    }
    
    public static <C, V> void setValue(C target, String path, V value) {
        setValue(target, path, value, true, false);
    }
    
    public static <C, V> void setValue(C target, String path, V value, boolean required, boolean autoConvert) {
        int sep = path.indexOf('.');
        if (sep < 0)
        	// it is a local property
            setLocal(target, path, value, required, autoConvert);
        else {
        	// a recursive feature path needs to be resolved
            String localName = path.substring(0, sep);
            Object subTarget = FeatureAccessor.getValue(target, localName, true);
            if (subTarget == null) {
                // feature exists but is null, so create an object and assign it to the feature
            	subTarget = setFeatureDefault(target, localName);
            }
            String remainingName = path.substring(sep + 1);
            setValue(subTarget, remainingName, value, required, autoConvert);
        }
    }

    public static Object setFeatureDefault(Object target, String featureName) {
    	// try JavaBean property
        PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(target.getClass(), featureName);
        if (propertyDescriptor != null) {
            try {
            	Object value = BeanUtil.newInstance(propertyDescriptor.getPropertyType());
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(target, value);
				return value;
            } catch (Exception e) {
                throw new ConfigurationError("Unable to write feature '" + featureName + "'", e);
            }
        } else {
        	// try attribute
        	Class<?> type = ((target instanceof Class) ? (Class<?>) target : target.getClass());
        	Field field = BeanUtil.getField(type, featureName);
        	if (field != null) {
            	Object value = BeanUtil.newInstance(field.getType());
        		BeanUtil.setAttributeValue(target, field, value);
        		return value;
        	} else {
                throw new ConfigurationError("Feature '" + featureName + "' not found in class " + type.getName());
        	}
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <C, V> void setLocal(C target, String featureName, V value, boolean required, boolean autoConvert) {
    	if (BeanUtil.hasWriteableProperty(target.getClass(), featureName))
            BeanUtil.setPropertyValue(target, featureName, value, required, autoConvert);
    	else if (target instanceof Context)
            ((Context) target).set(featureName, value);
        else if (target instanceof Map)
            ((Map) target).put(featureName, value);
        else if (target instanceof Composite)
            ((Composite) target).setComponent(featureName, value);
        else {
        	// try generic set(String, Object) method
        	Method genericSetMethod = BeanUtil.findMethod(target.getClass(), "set", String.class, Object.class);
        	if (genericSetMethod != null) {
        		BeanUtil.invoke(target, genericSetMethod, true, new Object[] { featureName, value });
        		return;
        	}
        	// try JavaBean property
        	try {
				Field field = target.getClass().getField(featureName);
		        if (autoConvert && value != null) {
		            Class<?> sourceType = value.getClass();
		            Class<?> targetType = field.getType();
		            try {
		                if (!targetType.isAssignableFrom(sourceType))
		                    value = (V) AnyConverter.convert(value, targetType);
		            } catch (ConversionException e) {
		                throw new ConfigurationError(e);
		            }
		        }
		        field.set(target, value);

			} catch (NoSuchFieldException e) {
	            String message = "No feature '" + featureName + "' found in " + target;
	            if (required)
	                throw new UnsupportedOperationException(message);
	            else
	                escalator.escalate(message, AnyMutator.class, null);
			} catch (IllegalAccessException e) {
				throw new UnsupportedOperationException("Error accessing attribute '" + 
						featureName + "' of class " + target.getClass().getName(), e);
			}
        }
    }

}

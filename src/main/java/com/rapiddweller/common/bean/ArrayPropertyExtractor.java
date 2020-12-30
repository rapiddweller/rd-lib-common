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
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.converter.ThreadSafeConverter;
import com.rapiddweller.common.converter.AnyConverter;

/**
 * Extracts property values from an array of JavaBeans in a way that
 * processing n beans results in an array of n elements with the property values.
 * @param <E> the bean type to access
 * Created: 02.08.2007 20:47:35
 * @author Volker Bergmann
 */
public class ArrayPropertyExtractor<E> extends ThreadSafeConverter<Object[], E[]> {

    private final String propertyName;
    private final Class<E> propertyType;

    @SuppressWarnings("unchecked")
    public ArrayPropertyExtractor(String propertyName, Class<E> propertyType) {
    	super(Object[].class, ArrayUtil.arrayType(propertyType));
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    @Override
	public E[] convert(Object[] sourceValue) throws ConversionException {
        return convert(sourceValue, propertyName, propertyType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] convert(Object[] sourceValue, String propertyName, Class<T> propertyType)
            throws ConversionException {
        T[] array = ArrayUtil.newInstance(propertyType, sourceValue.length);
        PropertyAccessor<Object, T> propertyAccessor = PropertyAccessorFactory.getAccessor(propertyName);
        for (int i = 0; i < sourceValue.length; i++) {
            Object value = propertyAccessor.getValue(sourceValue[i]);
            array[i] = AnyConverter.convert(value, propertyType);
        }
        return array;
    }
    
}

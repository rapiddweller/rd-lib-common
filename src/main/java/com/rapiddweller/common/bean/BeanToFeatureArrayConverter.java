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

import com.rapiddweller.common.Accessor;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.accessor.FeatureAccessor;
import com.rapiddweller.common.converter.ThreadSafeConverter;

/**
 * Maps a bean's feature (attributes, properties, Map contents) values to an array of values.
 * Created: 14.07.2014 15:24:31
 * @param <E> the object type to access
 * @since 0.5.33
 * @author Volker Bergmann
 */

public class BeanToFeatureArrayConverter<E> extends ThreadSafeConverter<E, Object[]> {

    private final Accessor<E, ?>[] accessors;

    public BeanToFeatureArrayConverter(String... featureNames) {
        this(null, featureNames);
    }

    @SuppressWarnings("unchecked")
    public BeanToFeatureArrayConverter(Class<E> beanClass, String... featureNames) {
    	super(beanClass, Object[].class);
        this.accessors = new Accessor[featureNames.length];
        for (int i = 0; i < featureNames.length; i++)
            this.accessors[i] = new FeatureAccessor<>(featureNames[i]);
    }

    @Override
	public Object[] convert(E bean) throws ConversionException {
        Object[] propertyValues = new Object[accessors.length];
        for (int i = 0; i < accessors.length; i++)
            propertyValues[i] = accessors[i].getValue(bean);
        return propertyValues;
    }
    
}

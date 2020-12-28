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

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.converter.ThreadSafeConverter;

/**
 * Wraps an Accessor with a Converter interface implementation.
 * Created: 25.06.2007 08:04:22
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyAccessConverter extends ThreadSafeConverter {

    private final PropertyAccessor accessor;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public PropertyAccessConverter(String propertyName) {
        this(propertyName, null, true);
    }

    public PropertyAccessConverter(String propertyName, Class<?> propertyType) {
        this(propertyName, propertyType, true);
    }

    public PropertyAccessConverter(String propertyName, Class<?> propertyType, boolean strict) {
    	super(Object.class, propertyType);
        this.accessor = PropertyAccessorFactory.getAccessor(propertyName, strict);
    }
    
    // Converter interface implementation ------------------------------------------------------------------------------

    @Override
	public Object convert(Object sourceValue) throws ConversionException {
        return accessor.getValue(sourceValue);
    }
    
}

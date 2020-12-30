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
package com.rapiddweller.common.converter;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.ConversionException;

import java.util.Collection;
import java.util.List;

/**
 * Converts arrays and collections to collections of same content, everything else is converted to a collection of size 1.
 * Created: 26.08.2007 16:16:15
 * @param <C> the collection type to convert to
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ToCollectionConverter<C extends Collection> extends ThreadSafeConverter<Object, C> {

    public ToCollectionConverter() {
        this((Class<C>) List.class);
    }

    public ToCollectionConverter(Class<C> targetType) {
        super(Object.class, targetType);
    }

    @Override
	public C convert(Object sourceValue) throws ConversionException {
        return (C) convert(sourceValue, targetType);
    }

    public static Collection convert(Object sourceValue, Class targetType) {
        if (sourceValue == null)
            return null;
        if (sourceValue.getClass() == targetType)
            return (Collection) sourceValue;
        Collection collection = CollectionUtil.newInstance(targetType);
        if (sourceValue instanceof Collection)
            collection.addAll((Collection) sourceValue);
        else if (sourceValue.getClass().isArray()) {
            Object[] array = (Object[]) sourceValue;
            for (Object o : array)
                collection.add(o);
        } else
            collection.add(sourceValue);
        return collection;
    }
    
}

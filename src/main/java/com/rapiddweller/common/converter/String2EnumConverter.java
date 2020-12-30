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

import com.rapiddweller.common.ConversionException;

/**
 * Instantiates enum instances by their name.
 * Created: 20.08.2007 07:11:16
 * @param <E> the enumeration type to convert to
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class String2EnumConverter<E extends Enum> extends ThreadSafeConverter<String, E> {

    public String2EnumConverter(Class<E> enumClass) {
        super(String.class, enumClass);
    }

    @Override
	public E convert(String sourceValue) throws ConversionException {
        return convert(sourceValue, targetType);
    }

    public static <T extends Enum> T convert(String sourceValue, Class<T> enumClass) throws ConversionException {
        if (sourceValue == null)
            return null;
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants)
            if (enumConstant.name().equals(sourceValue))
                return enumConstant;
        throw new ConversionException(enumClass + " does not have an instance of name " + sourceValue);
    }

}

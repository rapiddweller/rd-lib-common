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

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;

/**
 * Converts a {@link String} with a comma-separated list to an array.
 * Created: 27.02.2010 12:13:49
 * @param <T> the object type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class CommaSeparatedListConverter<T> extends ConverterWrapper<String, T> implements Converter<String, Object> {

	private final Class<T> targetComponentType;
	private final Class<T[]> targetType;

	@SuppressWarnings("unchecked")
    protected CommaSeparatedListConverter(Class<T> targetComponentType) {
	    super(ConverterManager.getInstance().createConverter(String.class, targetComponentType));
	    this.targetComponentType = targetComponentType;
	    this.targetType = ArrayUtil.arrayType(targetComponentType);
    }

	@Override
	public Object convert(String sourceValue) throws ConversionException {
		return ConverterManager.convertAll(ArrayFormat.parse(sourceValue, ",", String.class), realConverter, targetComponentType);
    }

	@Override
	public Class<String> getSourceType() {
	    return String.class;
    }

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Class<Object> getTargetType() {
	    return (Class) targetType;
    }

}

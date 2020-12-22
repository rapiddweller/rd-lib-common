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
package com.rapiddweller.commons.converter;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.StringUtil;

/**
 * Converts {@link String}s to {@link Number}s.
 * Created: 27.02.2010 11:22:10
 * @param <T> the {@link Number} type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class String2NumberConverter<T extends Number> extends ConstructorInvoker<String, T> {

	public String2NumberConverter(Class<T> targetType) {
	    super(String.class, BeanUtil.findConstructor(targetType, String.class));
    }

	@Override
    public T convert(String sourceValue) throws ConversionException {
		if (StringUtil.isEmpty(sourceValue))
			return null;
		else
			return super.convert(sourceValue);
	}

}

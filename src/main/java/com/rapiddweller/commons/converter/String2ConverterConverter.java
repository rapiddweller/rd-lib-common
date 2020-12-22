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

import java.text.Format;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.ConfigurationError;
import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.LoggerEscalator;
import com.rapiddweller.commons.StringUtil;

/**
 * Converts Strings to Converters and vice versa.
 * Created: 15.03.2008 12:49:10
 * @since 0.4.0
 * @author Volker Bergmann
 * @deprecated The class is obsolete and will be removed soon
 */
@Deprecated
@SuppressWarnings({ "unchecked", "rawtypes" })
public class String2ConverterConverter extends ThreadSafeConverter<String, Converter> {
	
	private static LoggerEscalator escalator = new LoggerEscalator();

    public String2ConverterConverter() {
        super(String.class, Converter.class);
        escalator.escalate("Class is deprecated: " + getClass(), this, null);
    }

	@Override
	public Converter convert(String sourceValue) throws ConversionException {
        if (StringUtil.isEmpty(sourceValue))
            return null;
        Object result = BeanUtil.newInstance(sourceValue);
        if (result instanceof Format)
            return new ParseFormatConverter(Object.class, (Format) result, false);
        else if (result instanceof Converter)
            return (Converter) result;
        else
            throw new ConfigurationError("Class is neither Converter nor Format: " + result.getClass());
    }

}

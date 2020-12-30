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

import java.text.Format;
import java.text.ParseException;

/**
 * Converts a String to an object by using a java.lang.Format object's format() method.
 * Created: 30.08.2006 19:48:09
 * @param <T> the object type to convert to
 * @since 0.1
 * @author Volker Bergmann
 */
public class ParseFormatConverter<T> extends FormatBasedConverter<String, T> {

    public ParseFormatConverter(Class<T> targetType, Format format, boolean threadSafe) {
        super(String.class, targetType, format, threadSafe);
    }

    /** Converts an object to a String by using the format's format() method. */
    @Override
	@SuppressWarnings("unchecked")
	public T convert(String source) throws ConversionException {
        if (source == null)
            return null;
        try {
            return (T) format.parseObject(source);
        } catch (ParseException e) {
            throw new ConversionException(e);
        }
    }

}

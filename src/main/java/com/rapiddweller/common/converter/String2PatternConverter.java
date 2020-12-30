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

import java.util.regex.Pattern;

import com.rapiddweller.common.ConversionException;

/**
 * Converts a String to a {@link Pattern}.
 * Created at 01.10.2009 10:09:38
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class String2PatternConverter extends ThreadSafeConverter<String, Pattern> {

    public String2PatternConverter() {
	    super(String.class, Pattern.class);
    }

	@Override
	public Pattern convert(String regex) throws ConversionException {
	    return Pattern.compile(regex);
    }

}

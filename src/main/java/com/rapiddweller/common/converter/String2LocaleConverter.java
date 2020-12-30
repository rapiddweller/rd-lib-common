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
import com.rapiddweller.common.LocaleUtil;

import java.util.Locale;

/**
 * Instantiates {@link Locale}s by their code, e.g. de_DE.
 * Created: 05.08.2007 06:29:58
 * @author Volker Bergmann
 */
public class String2LocaleConverter extends ThreadSafeConverter<String, Locale> {

    public String2LocaleConverter() {
        super(String.class, Locale.class);
    }

    @Override
	public Locale convert(String sourceValue) throws ConversionException {
        return LocaleUtil.getLocale(sourceValue);
    }

}

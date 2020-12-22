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

import java.time.YearMonth;

import com.rapiddweller.commons.ConversionException;

/**
 * Parses a string as {@link YearMonth}.<br><br>
 * Created: 28.07.2019 20:34:04
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class String2YearMonthConverter extends ThreadSafeConverter<String, YearMonth> {

    public String2YearMonthConverter() {
        super(String.class, YearMonth.class);
    }

    @Override
	public YearMonth convert(String target) throws ConversionException {
        return YearMonth.parse(target);
    }
    
}

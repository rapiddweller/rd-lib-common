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

import com.rapiddweller.commons.ConversionException;

import java.text.*;
import java.util.Locale;

/**
 * Formats a number as a String.
 * Created: 10.09.2007 07:35:18
 * @author Volker Bergmann
 */
public class Number2StringConverter extends ThreadSafeConverter<Number, String> {

    private final int minimumFractionDigits;
    private final int maximumFractionDigits;
    boolean groupingUsed;

    public Number2StringConverter(int minimumFractionDigits, int maximumFractionDigits, boolean groupingUsed) {
    	super(Number.class, String.class);
        this.minimumFractionDigits = minimumFractionDigits;
        this.maximumFractionDigits = maximumFractionDigits;
        this.groupingUsed = groupingUsed;
    }

    @Override
	public String convert(Number sourceValue) throws ConversionException {
        return convert(sourceValue, minimumFractionDigits, maximumFractionDigits, groupingUsed);
    }

    public static String convert(Number sourceValue, int minimumFractionDigits, int maximumFractionDigits, boolean groupingUsed) {
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(minimumFractionDigits);
        format.setMaximumFractionDigits(maximumFractionDigits);
        format.setGroupingUsed(groupingUsed);
        return format.format(sourceValue);
    }
    
}

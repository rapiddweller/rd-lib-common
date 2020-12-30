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
package com.rapiddweller.common;

import java.text.*;
import java.util.Locale;

/**
 * Rounds large numbers to the four most significant digits.
 * Created: 01.09.2007 16:19:14
 */
public class RoundedNumberFormat extends Format {

    private static final long serialVersionUID = 6188839664275513505L;

	@Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        return toAppendTo.append(format((Number)obj, 2));
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("Not supported");
    }

    public static String format(Number number, int fractionDigits) {
        NumberFormat nf = DecimalFormat.getInstance(Locale.US);
        //nf.setMinimumFractionDigits(fractionDigits);
        nf.setMaximumFractionDigits(fractionDigits);
        StringBuffer buffer = nf.format(number, new StringBuffer(), new FieldPosition(0));
        int nonNullDigits = 0;
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (Character.isDigit(c)) {
                if (nonNullDigits >= 2)
                    buffer.setCharAt(i, '0');
                nonNullDigits++;
            }
        }
        return buffer.toString();
    }

}

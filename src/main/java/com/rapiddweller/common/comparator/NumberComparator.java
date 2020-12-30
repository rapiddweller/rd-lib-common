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
package com.rapiddweller.common.comparator;

import java.util.Comparator;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Generic comparator for Number objects of different type.
 * Created: 09.10.2006 19:46:22
 * @param <E> the type of objects to be compared
 * @since 0.1
 * @author Volker Bergmann
 */
public class NumberComparator<E extends Number> implements Comparator<E> {

    @Override
	public int compare(E n1, E n2) {
        return compareNumbers(n1, n2);
    }

	public static <T extends Number> int compareNumbers(T n1, T n2) {
	    if (n1 == null || n2 == null)
            throw new IllegalArgumentException("comparing null value");
        if (n1 instanceof Integer)
            return ((Integer) n1).compareTo(n2.intValue());
        else if (n1 instanceof Long)
            return ((Long) n1).compareTo(n2.longValue());
        else if (n1 instanceof Short)
            return ((Short) n1).compareTo(n2.shortValue());
        else if (n1 instanceof Byte)
            return ((Byte) n1).compareTo(n2.byteValue());
        else if (n1 instanceof Float)
            return ((Float) n1).compareTo(n2.floatValue());
        else if (n1 instanceof Double)
            return ((Double) n1).compareTo(n2.doubleValue());
        else if (n1 instanceof BigInteger)
            return ((BigInteger) n1).compareTo((BigInteger) n2);
        else if (n1 instanceof BigDecimal)
            return ((BigDecimal) n1).compareTo((BigDecimal) n2);
        else
            throw new UnsupportedOperationException("Unsupported Number type: " + n1.getClass());
    }
}

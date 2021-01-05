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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the RoundedNumberFormat class.
 * Created: 01.09.2007 16:30:06
 *
 * @author Volker Bergmann
 */
public class RoundedNumberFormatTest {

    @Test
    public void test() {
        assertEquals("1,200", RoundedNumberFormat.format(1234, 0));
        assertEquals("1,200", RoundedNumberFormat.format(1234.45, 0));
        assertEquals("1,200", RoundedNumberFormat.format(1234.567, 0));
        assertEquals("1,200.00", RoundedNumberFormat.format(1234.567, 2));
        assertEquals("1,200,000", RoundedNumberFormat.format(1234567, 0));
        assertEquals("1,200,000", RoundedNumberFormat.format(1234567, 2));
    }

    @Test
    public void testFormat() {
        assertEquals("42", RoundedNumberFormat.format(new Integer(42), 1));
        assertEquals("-1", RoundedNumberFormat.format(new Integer(-1), 1));
        assertEquals("-2,100,000,000", RoundedNumberFormat.format(new Integer(-2147483648), 1));
    }

}

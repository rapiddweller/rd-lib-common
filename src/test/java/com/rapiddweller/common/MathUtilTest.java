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
 * Tests the {@link MathUtil} class.
 * @author Volker Bergmann
 */
public class MathUtilTest {

	@Test
    public void testDigitCount() {
        assertEquals(1, MathUtil.digitCount(1));
        assertEquals(1, MathUtil.digitCount(0));
        assertEquals(1, MathUtil.digitCount(-1));
        assertEquals(2, MathUtil.digitCount(10));
        assertEquals(2, MathUtil.digitCount(-10));
        assertEquals(6, MathUtil.digitCount(999999));
        assertEquals(7, MathUtil.digitCount(1000000));
        assertEquals(8, MathUtil.digitCount(99999999));
        assertEquals(9, MathUtil.digitCount(100000000));
    }

	@Test
    public void testPrefixDigitCount() {
        assertEquals(1, MathUtil.prefixDigitCount(1));
        assertEquals(1, MathUtil.prefixDigitCount(0));
        assertEquals(1, MathUtil.prefixDigitCount(-1));
        assertEquals(1, MathUtil.prefixDigitCount(0.001));
        assertEquals(1, MathUtil.prefixDigitCount(0.1));
        assertEquals(1, MathUtil.prefixDigitCount(0.9));
        assertEquals(1, MathUtil.prefixDigitCount(9.9));
        assertEquals(2, MathUtil.prefixDigitCount(10));
        assertEquals(2, MathUtil.prefixDigitCount(-10));
        assertEquals(6, MathUtil.prefixDigitCount(999999));
        assertEquals(7, MathUtil.prefixDigitCount(1000000));
        assertEquals(8, MathUtil.prefixDigitCount(99999999));
        assertEquals(9, MathUtil.prefixDigitCount(100000000));
    }

	@Test
    public void testFractionDigits() {
        assertEquals(0, MathUtil.fractionDigits(0));
        assertEquals(0, MathUtil.fractionDigits(1));
        assertEquals(0, MathUtil.fractionDigits(-1));
        assertEquals(1, MathUtil.fractionDigits(0.5));
        assertEquals(1, MathUtil.fractionDigits(0.1));
        assertEquals(1, MathUtil.fractionDigits(-0.1));
        assertEquals(1, MathUtil.fractionDigits(0.9));
        assertEquals(7, MathUtil.fractionDigits(0.9999999));
        assertEquals(7, MathUtil.fractionDigits(0.0000001));
        assertEquals(7, MathUtil.fractionDigits(0.0000009));
    }
    
	@Test
    public void testSumOfDigits() {
    	assertEquals(0, MathUtil.digitSum(0));
    	assertEquals(1, MathUtil.digitSum(1));
    	assertEquals(1, MathUtil.digitSum(10));
    	assertEquals(6, MathUtil.digitSum(123));
    }
	
}

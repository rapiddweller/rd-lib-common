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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.math.IntDoublePair;
import com.rapiddweller.common.math.Segmented;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests the {@link MathUtil} class.
 *
 * @author Volker Bergmann
 */
public class MathUtilTest {

    @Test
    public void testSegment() {
        Segmented actualSegmentResult = MathUtil.segment(1, 1);
        assertEquals(0, actualSegmentResult.getOffset());
        assertEquals(1, actualSegmentResult.getSegment());
    }

    @Test
    public void testSegment2() {
        assertThrows(ArithmeticException.class, () -> MathUtil.segment(1, 0));
    }

    @Test
    public void testSegment3() {
        Segmented actualSegmentResult = MathUtil.segment(1, 1);
        assertEquals(0, actualSegmentResult.getOffset());
        assertEquals(1, actualSegmentResult.getSegment());
    }

    @Test
    public void testSegment4() {
        assertThrows(ArithmeticException.class, () -> MathUtil.segment(1, 0));
    }

    @Test
    public void testNanToNull() {
        assertEquals(10.0, MathUtil.nanToNull(10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nanToNull(Double.NaN));
        assertEquals(10.0, MathUtil.nanToNull(10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nanToNull(Double.NaN));
    }

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
        assertEquals(1, MathUtil.digitCount(3L));
        assertEquals(1, MathUtil.digitCount(0L));
        assertEquals(1, MathUtil.digitCount(3L));
        assertEquals(1, MathUtil.digitCount(0L));
    }

    @Test
    public void testDigitalRoot() {
        assertEquals(1, MathUtil.digitalRoot(1));
        assertEquals(1, MathUtil.digitalRoot(1));
    }

    @Test
    public void testDigitSum() {
        assertEquals(1, MathUtil.digitSum(1));
        assertEquals(1, MathUtil.digitSum(1));
    }

    @Test
    public void testWeightedSumOfSumOfDigits() {
        assertEquals(42, MathUtil.weightedSumOfSumOfDigits("Number", 1, 3, 3, 3, 3));
        assertEquals(42, MathUtil.weightedSumOfSumOfDigits("Number", 1, 3, 3, 3, 3));
    }

    @Test
    public void testRangeIncludes() {
        assertFalse(MathUtil.rangeIncludes(2.0, 10.0, 10.0));
        assertTrue(MathUtil.rangeIncludes(10.0, 10.0, 10.0));
        assertFalse(MathUtil.rangeIncludes(10.0, 10.0, 1.0E-7));
        assertTrue(MathUtil.rangeIncludes(1L, 1L, 1L));
        assertFalse(MathUtil.rangeIncludes(0L, 1L, 1L));
        assertFalse(MathUtil.rangeIncludes(9223372036854775807L, 1L, 1L));
        assertFalse(MathUtil.rangeIncludes(2.0, 10.0, 10.0));
        assertTrue(MathUtil.rangeIncludes(10.0, 10.0, 10.0));
        assertFalse(MathUtil.rangeIncludes(10.0, 10.0, 1.0E-7));
        assertTrue(MathUtil.rangeIncludes(1L, 1L, 1L));
        assertFalse(MathUtil.rangeIncludes(0L, 1L, 1L));
        assertFalse(MathUtil.rangeIncludes(9223372036854775807L, 1L, 1L));
    }

    @Test
    public void testBetween() {
        assertFalse(MathUtil.between(2.0, 10.0, 10.0));
        assertTrue(MathUtil.between(2.0, 1.0E-7, 10.0));
        assertFalse(MathUtil.between(10.0, 1.0E-7, 10.0));
        assertFalse(MathUtil.between(1L, 1L, 1L));
        assertFalse(MathUtil.between(9223372036854775807L, 1L, 1L));
        assertTrue(MathUtil.between(0L, -1L, 1L));
        assertFalse(MathUtil.between(2.0, 10.0, 10.0));
        assertTrue(MathUtil.between(2.0, 1.0E-7, 10.0));
        assertFalse(MathUtil.between(10.0, 1.0E-7, 10.0));
        assertFalse(MathUtil.between(1L, 1L, 1L));
        assertFalse(MathUtil.between(9223372036854775807L, 1L, 1L));
        assertTrue(MathUtil.between(0L, -1L, 1L));
    }

    @Test
    public void testSum() {
        assertEquals(40.0, MathUtil.sum(new double[]{10.0, 10.0, 10.0, 10.0}).doubleValue(), 0.0);
        assertEquals(40.0, MathUtil.sum(new double[]{10.0, 10.0, 10.0, 10.0}).doubleValue(), 0.0);
    }

    @Test
    public void testMax() {
        assertEquals(10.0, MathUtil.max(10.0, 10.0, 10.0, 10.0), 0.0);
        assertEquals(10.0, MathUtil.max(1.0E-7, 10.0, 10.0, 10.0), 0.0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtil.max());
        assertEquals(1, MathUtil.max(1, 1, 1, 1));
        assertEquals(1, MathUtil.max(0, 1, 1, 1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtil.max());
        assertEquals(10.0, MathUtil.max(10.0, 10.0, 10.0, 10.0), 0.0);
        assertEquals(10.0, MathUtil.max(1.0E-7, 10.0, 10.0, 10.0), 0.0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtil.max());
        assertEquals(1, MathUtil.max(1, 1, 1, 1));
        assertEquals(1, MathUtil.max(0, 1, 1, 1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtil.max());
    }

    @Test
    public void testMin() {
        assertEquals(10.0, MathUtil.min(10.0, 10.0, 10.0, 10.0), 0.0);
        assertEquals(1.0E-7, MathUtil.min(10.0, 1.0E-7, 10.0, 10.0), 0.0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtil.min());
        assertEquals(10.0, MathUtil.min(10.0, 10.0, 10.0, 10.0), 0.0);
        assertEquals(1.0E-7, MathUtil.min(10.0, 1.0E-7, 10.0, 10.0), 0.0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtil.min());
    }

    @Test(expected = NullPointerException.class)
    public void testNullableProduct() {
        assertEquals(10.0, MathUtil.nullableProduct(10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableProduct());
        assertEquals(100.0, MathUtil.nullableProduct(10.0, 10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableProduct(10.0, null));
        assertNull(MathUtil.nullableProduct(null));
        assertEquals(10.0, MathUtil.nullableProduct(10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableProduct(null));
        assertNull(MathUtil.nullableProduct());
        assertEquals(100.0, MathUtil.nullableProduct(10.0, 10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableProduct(10.0, null));
    }

    @Test(expected = NullPointerException.class)
    public void testNullableDivision() {
        assertEquals(1.0, MathUtil.nullableDivision(10.0, 10.0), 0.0);
        assertNull(MathUtil.nullableDivision(10.0, null));
        assertNull(MathUtil.nullableDivision(10.0));
        assertNull(MathUtil.nullableDivision(10.0, 10.0, null));
        assertNull(MathUtil.nullableDivision(null, 10.0));
        assertEquals(1.0, MathUtil.nullableDivision(10.0, 10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableDivision(null, 10.0));
        assertNull(MathUtil.nullableDivision(10.0, null));
        assertNull(MathUtil.nullableDivision(10.0));
        assertNull(MathUtil.nullableDivision(10.0, 10.0, null));
    }

    @Test(expected = NullPointerException.class)
    public void testNullableSum() {
        assertEquals(10.0, MathUtil.nullableSum(10.0), 0.0);
        assertNull(MathUtil.nullableSum());
        assertEquals(20.0, MathUtil.nullableSum(10.0, 10.0), 0.0);
        assertNull(MathUtil.nullableSum(10.0, null));
        assertEquals(10.0, MathUtil.nullableSum(10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableSum());
        assertEquals(20.0, MathUtil.nullableSum(10.0, 10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableSum(10.0, null));
        assertNull(MathUtil.nullableSum(null));
    }

    @Test(expected = NullPointerException.class)
    public void testNullableSubtraction() {
        assertEquals(0.0, MathUtil.nullableSubtraction(10.0, 10.0), 0.0);
        assertNull(MathUtil.nullableSubtraction(10.0, null));
        assertEquals(0.0, MathUtil.nullableSubtraction(10.0, 10.0).doubleValue(), 0.0);
        assertNull(MathUtil.nullableSubtraction(null, 10.0));
        assertNull(MathUtil.nullableSubtraction(10.0, null));
        assertNull(MathUtil.nullableSubtraction(10.0));
        assertNull(MathUtil.nullableSubtraction(10.0, 10.0, null));
    }

    @Test
    public void testFactorial() {
        assertEquals(1L, MathUtil.factorial(1));
        assertEquals(1L, MathUtil.factorial(1));
    }

    @Test
    public void testDegToRad() {
        assertEquals(0.17453292519943295, MathUtil.degToRad(10.0), 0.0);
        assertEquals(0.17453292519943295, MathUtil.degToRad(10.0), 0.0);
    }

    @Test
    public void testRadToDeg() {
        assertEquals(572.9577951308232, MathUtil.radToDeg(10.0), 0.0);
        assertEquals(572.9577951308232, MathUtil.radToDeg(10.0), 0.0);
    }

    @Test
    public void testSquare() {
        assertEquals(4.0, MathUtil.square(2.0), 0.0);
        assertEquals(4.0, MathUtil.square(2.0), 0.0);
    }

    @Test
    public void testAverage() {
        assertEquals(10.0, MathUtil.average(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
        assertEquals(10.0, MathUtil.average(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
    }

    @Test
    public void testVariance() {
        assertEquals(0.0, MathUtil.variance(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
        assertEquals(0.0, MathUtil.variance(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
    }

    @Test
    public void testStandardDeviation() {
        assertEquals(0.0, MathUtil.standardDeviation(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
        assertEquals(0.0, MathUtil.standardDeviation(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
    }

    @Test
    public void testCorrectedStandardDeviation() {
        assertEquals(0.0, MathUtil.correctedStandardDeviation(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
        assertEquals(0.0, MathUtil.correctedStandardDeviation(new double[]{10.0, 10.0, 10.0, 10.0}), 0.0);
    }

    @Test
    public void testMinValue() {
        IntDoublePair intDoublePair = new IntDoublePair(1, 10.0);
        ArrayList<IntDoublePair> intDoublePairList = new ArrayList<IntDoublePair>();
        intDoublePairList.add(intDoublePair);
        assertSame(intDoublePair, MathUtil.minValue(intDoublePairList));
    }

    @Test
    public void testMinValue2() {
        IntDoublePair intDoublePair = new IntDoublePair(1, 10.0);
        ArrayList<IntDoublePair> intDoublePairList = new ArrayList<IntDoublePair>();
        intDoublePairList.add(intDoublePair);
        assertSame(intDoublePair, MathUtil.minValue(intDoublePairList));
    }

    @Test
    public void testMaxValue() {
        IntDoublePair intDoublePair = new IntDoublePair(1, 10.0);
        ArrayList<IntDoublePair> intDoublePairList = new ArrayList<IntDoublePair>();
        intDoublePairList.add(intDoublePair);
        assertSame(intDoublePair, MathUtil.maxValue(intDoublePairList));
    }

    @Test
    public void testMaxValue2() {
        IntDoublePair intDoublePair = new IntDoublePair(1, 10.0);
        ArrayList<IntDoublePair> intDoublePairList = new ArrayList<IntDoublePair>();
        intDoublePairList.add(intDoublePair);
        assertSame(intDoublePair, MathUtil.maxValue(intDoublePairList));
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
        assertEquals(2, MathUtil.prefixDigitCount(10.0));
        assertEquals(1, MathUtil.prefixDigitCount(1.0E-7));
        assertEquals(2, MathUtil.prefixDigitCount(10.0));
        assertEquals(1, MathUtil.prefixDigitCount(1.0E-7));
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
        assertEquals(0, MathUtil.fractionDigits(10.0));
        assertEquals(7, MathUtil.fractionDigits(1.0E-7));
        assertEquals(0, MathUtil.fractionDigits(10.0));
        assertEquals(7, MathUtil.fractionDigits(1.0E-7));
    }

    @Test
    public void testIsIntegralValue() {
        assertTrue(MathUtil.isIntegralValue(10.0));
        assertFalse(MathUtil.isIntegralValue(1.0E-7));
        assertTrue(MathUtil.isIntegralValue(10.0));
        assertFalse(MathUtil.isIntegralValue(1.0E-7));
    }

    @Test
    public void testSumOfDigits() {
        assertEquals(0, MathUtil.digitSum(0));
        assertEquals(1, MathUtil.digitSum(1));
        assertEquals(1, MathUtil.digitSum(10));
        assertEquals(6, MathUtil.digitSum(123));
    }

}

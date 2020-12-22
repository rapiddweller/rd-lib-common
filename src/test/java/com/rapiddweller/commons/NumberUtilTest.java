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
package com.rapiddweller.commons;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import static com.rapiddweller.commons.NumberUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * Created: 21.06.2007 08:32:34
 * @author Volker Bergmann
 */
public class NumberUtilTest {

	@Test
    public void test() {
        assertEquals("1.23", NumberUtil.format(1.23, 2));
        assertEquals("1", NumberUtil.format(1.23, 0));
    }

	@Test
    public void testBitsUsed() {
        assertEquals(1, bitsUsed(0));
        assertEquals(1, bitsUsed(1));
        assertEquals(2, bitsUsed(2));
        assertEquals(8, bitsUsed(0xff));
        assertEquals(9, bitsUsed(0x100));
        assertEquals(16, bitsUsed(0xffff));
        assertEquals(17, bitsUsed(0x10000));
        assertEquals(32, bitsUsed(0xffffffffL));
        assertEquals(63, bitsUsed(0x7fffffffffffffffL));
        assertEquals(64, bitsUsed(0xffffffffffffffffL));
        assertEquals(64, bitsUsed(-1L));
    }

	@Test
    public void testFormatHex() {
        assertEquals("0001", NumberUtil.formatHex( 1, 4));
        assertEquals("ffff", NumberUtil.formatHex(-1, 4));
    }
    
	@Test
	public void testTotalDigits() {
		assertEquals(3, NumberUtil.totalDigits(byte.class));
		assertEquals(3, NumberUtil.totalDigits(Byte.class));
		assertEquals(5, NumberUtil.totalDigits(short.class));
		assertEquals(5, NumberUtil.totalDigits(Short.class));
		assertEquals(10, NumberUtil.totalDigits(int.class));
		assertEquals(10, NumberUtil.totalDigits(Integer.class));
		assertEquals(19, NumberUtil.totalDigits(long.class));
		assertEquals(19, NumberUtil.totalDigits(Long.class));
		assertEquals(39, NumberUtil.totalDigits(float.class));
		assertEquals(39, NumberUtil.totalDigits(Float.class));
		assertEquals(309, NumberUtil.totalDigits(double.class));
		assertEquals(309, NumberUtil.totalDigits(Double.class));
		assertEquals(309, NumberUtil.totalDigits(BigInteger.class));
		assertEquals(309, NumberUtil.totalDigits(BigDecimal.class));
	}
	
}

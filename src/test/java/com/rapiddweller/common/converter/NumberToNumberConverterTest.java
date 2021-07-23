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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link NumberToNumberConverter}.
 * Created: 15.11.2012 10:34:24
 *
 * @author Volker Bergmann
 * @since 0.5.20
 */
public class NumberToNumberConverterTest {

  @Test
  public void testStaticConversion() {
    checkStaticConversion(5L, Long.class);
    checkStaticConversion(5L, long.class);
    checkStaticConversion(5, Integer.class);
    checkStaticConversion(5, int.class);
    checkStaticConversion((short) 5, Short.class);
    checkStaticConversion((short) 5, short.class);
    checkStaticConversion((byte) 5, Byte.class);
    checkStaticConversion((byte) 5, byte.class);
    checkStaticConversion((double) 5, Double.class);
    checkStaticConversion((double) 5, double.class);
    checkStaticConversion((float) 5, Float.class);
    checkStaticConversion((float) 5, float.class);
    checkStaticConversion(new BigInteger("5"), BigInteger.class);
    checkStaticConversion(new BigDecimal("5.0"), BigDecimal.class);
  }

  @Test
  public void testInstanceConversion() {
    checkInstanceConversion(5L, Long.class);
    checkInstanceConversion(5L, long.class);
    checkInstanceConversion(5, Integer.class);
    checkInstanceConversion(5, int.class);
    checkInstanceConversion((short) 5, Short.class);
    checkInstanceConversion((short) 5, short.class);
    checkInstanceConversion((byte) 5, Byte.class);
    checkInstanceConversion((byte) 5, byte.class);
    checkInstanceConversion((double) 5, Double.class);
    checkInstanceConversion((double) 5, double.class);
    checkInstanceConversion((float) 5, Float.class);
    checkInstanceConversion((float) 5, float.class);
    checkInstanceConversion(new BigInteger("5"), BigInteger.class);
    checkInstanceConversion(new BigDecimal("5.0"), BigDecimal.class);
  }

  private static void checkStaticConversion(Number expectedResult, Class<? extends Number> targetType) {
    Number[] sourceValues = new Number[] {(long) 5, 5, (short) 5, (byte) 5, (double) 5, (float) 5, new BigInteger("5"), new BigDecimal("5")};
    for (Number sourceValue : sourceValues) {
      assertEquals(expectedResult, NumberToNumberConverter.convert(sourceValue, targetType));
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void checkInstanceConversion(Number expectedResult, Class<? extends Number> targetType) {
    Number[] sourceValues = new Number[] {(long) 5, 5, (short) 5, (byte) 5, (double) 5, (float) 5, new BigInteger("5"), new BigDecimal("5")};
    for (Number sourceValue : sourceValues) {
      assertEquals(expectedResult, new NumberToNumberConverter(sourceValue.getClass(), targetType).convert(sourceValue));
    }
  }

}

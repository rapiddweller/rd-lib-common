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
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Tests the CaseConverter.
 * Created: 29.09.2006 15:50:03
 *
 * @author Volker Bergmann
 */
public class CaseConverterTest extends AbstractConverterTest {

  @Test
  public void testConstructor() {
    CaseConverter actualCaseConverter = new CaseConverter();
    Class<String> expectedTargetType = actualCaseConverter.targetType;
    Class<String> targetType = actualCaseConverter.getTargetType();
    assertSame(expectedTargetType, targetType);
    assertSame(targetType, actualCaseConverter.getSourceType());
  }

  @Test
  public void testConstructor2() {
    CaseConverter actualCaseConverter = new CaseConverter(true);
    Class<String> expectedTargetType = actualCaseConverter.targetType;
    Class<String> targetType = actualCaseConverter.getTargetType();
    assertSame(expectedTargetType, targetType);
    assertSame(targetType, actualCaseConverter.getSourceType());
  }

  @Test
  public void testConvert() {
    assertEquals("SOURCE", (new CaseConverter()).convert("Source"));
    assertNull((new CaseConverter()).convert(null));
  }

  public CaseConverterTest() {
    super(CaseConverter.class);
  }

  @Test
  public void testToUpper() throws ConversionException {
    CaseConverter converter = new CaseConverter(true, Locale.ENGLISH);
    assertEquals("ABC,123", converter.convert("ABC,123"));
    assertEquals("ABC,123", converter.convert("abc,123"));
    assertEquals("", converter.convert(""));
    assertEquals(null, converter.convert(null));
  }

  @Test
  public void testToLower() throws ConversionException {
    CaseConverter converter = new CaseConverter(false, Locale.ENGLISH);
    assertEquals("abc,123", converter.convert("abc,123"));
    assertEquals("abc,123", converter.convert("ABC,123"));
    assertEquals("", converter.convert(""));
    assertEquals(null, converter.convert(null));
  }

}

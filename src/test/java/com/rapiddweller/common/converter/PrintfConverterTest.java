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
 * Tests the {@link PrintfConverter}.
 * Created at 20.07.2009 07:30:34
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */

public class PrintfConverterTest extends AbstractConverterTest {

  public PrintfConverterTest() {
    super(PrintfConverter.class);
  }

  @Test
  public void testConstructor() {
    PrintfConverter actualPrintfConverter = new PrintfConverter();
    assertEquals("", actualPrintfConverter.getPattern());
    Class<String> expectedTargetType = actualPrintfConverter.targetType;
    assertSame(expectedTargetType, actualPrintfConverter.getTargetType());
    Class<Object> expectedSourceType = actualPrintfConverter.sourceType;
    assertSame(expectedSourceType, actualPrintfConverter.getSourceType());
  }

  @Test
  public void testConstructor2() {
    PrintfConverter actualPrintfConverter = new PrintfConverter("Pattern");
    assertEquals("Pattern", actualPrintfConverter.getPattern());
    Class<String> expectedTargetType = actualPrintfConverter.targetType;
    assertSame(expectedTargetType, actualPrintfConverter.getTargetType());
    Class<Object> expectedSourceType = actualPrintfConverter.sourceType;
    assertSame(expectedSourceType, actualPrintfConverter.getSourceType());
  }

  @Test
  public void testSetLocale() {
    Locale locale = new Locale("en");
    PrintfConverter printfConverter = new PrintfConverter();
    printfConverter.setLocale(locale);
    assertSame(locale, printfConverter.getLocale());
  }

  @Test
  public void testSetPattern() {
    PrintfConverter printfConverter = new PrintfConverter();
    printfConverter.setPattern("Pattern");
    assertEquals("Pattern", printfConverter.getPattern());
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("", (new PrintfConverter()).convert("sourceValue"));
    assertNull((new PrintfConverter()).convert(null));
  }

  @Test
  public void test() {
    PrintfConverter converter = new PrintfConverter("(%1$s)");
    assertEquals("(X)", converter.convert("X"));
  }

}

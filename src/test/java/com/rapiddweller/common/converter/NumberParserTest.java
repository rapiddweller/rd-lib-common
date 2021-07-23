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

import com.rapiddweller.common.LocaleUtil;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link NumberParser}.
 * Created: 26.02.2010 08:52:12
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class NumberParserTest extends AbstractConverterTest {

  public NumberParserTest() {
    super(NumberParser.class);
  }

  @Test
  public void testEmpty() {
    NumberParser converter = new NumberParser();
    assertEquals(null, converter.convert(""));
    assertEquals(null, converter.convert(null));
  }

  @Test
  public void testIntegral() {
    NumberParser converter = new NumberParser();
    assertEquals(0, converter.convert("0").intValue());
    assertEquals(1000, converter.convert("1000").intValue());
  }

  @Test
  public void testConvert_US() {
    LocaleUtil.runInLocale(Locale.US, this::checkConversion);
  }

  @Test
  public void testConvert_DE() {
    LocaleUtil.runInLocale(Locale.GERMANY, this::checkConversion);
  }

  void checkConversion() {
    NumberParser converter = new NumberParser();
    // default
    assertEquals(-1., converter.convert("-1.0").doubleValue(), 0.0001);
    assertEquals(0., converter.convert("0.0").doubleValue(), 0.0001);
    assertEquals(1000., converter.convert("1000.0").doubleValue(), 0.0001);
    // pattern
    converter.setPattern("0.00");
    assertEquals(0., converter.convert("0.00").doubleValue(), 0.0001);
    // decimal separator
    converter.setDecimalSeparator(',');
    assertEquals(0., converter.convert("0,00").doubleValue(), 0.0001);
    // grouping
    converter.setGroupingSeparator('.');
    converter.setPattern("#,##0");
    assertEquals(1000, converter.convert("1.000").intValue(), 0.0001);
    // grouping and decimal separator
    converter.setPattern("#,##0.00");
    assertEquals(1000., converter.convert("1.000,00").doubleValue(), 0.0001);
  }

}

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
 * Created at 22.03.2009 08:02:42
 *
 * @author Volker Bergmann
 * @since 0.4.9
 */

public class NumberFormatterTest extends AbstractConverterTest {

  public NumberFormatterTest() {
    super(NumberFormatter.class);
  }

  @Test
  public void testEmpty() {
    NumberFormatter converter = new NumberFormatter();
    assertEquals("", converter.convert(null));
  }

  @Test
  public void testIntegralNumber() {
    NumberFormatter converter = new NumberFormatter();
    assertEquals("-1", converter.convert(-1));
    assertEquals("1000", converter.convert(1000));
  }

  @Test
  public void testConvert_US() {
    LocaleUtil.runInLocale(Locale.US, this::checkConversions);
  }

  @Test
  public void testConvert_DE() {
    LocaleUtil.runInLocale(Locale.GERMANY, this::checkConversions);
  }

  void checkConversions() {
    NumberFormatter converter = new NumberFormatter();
    // default
    assertEquals("0", converter.convert(0.));
    assertEquals("1000", converter.convert(1000.));
    // pattern
    converter.setPattern("0.00");
    assertEquals("0.00", converter.convert(0.));
    // decimal separator
    converter.setDecimalSeparator(',');
    assertEquals("0,00", converter.convert(0.));
    // grouping pattern
    converter.setPattern("#,##0");
    assertEquals("1,000", converter.convert(1000.));
    // decimal and grouping separator
    converter.setPattern("#,##0.00");
    converter.setGroupingSeparator('.');
    assertEquals("1.000,00", converter.convert(1000.));
  }

}

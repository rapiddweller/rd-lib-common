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

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link AnyConverter}.
 * Created: 29.09.2006 12:12:22
 *
 * @author Volker Bergmann
 */
public class AnyConverterTest extends AbstractConverterTest {

  public AnyConverterTest() {
    super(AnyConverter.class);
  }

  @Test
  public void testConstructor() {
    AnyConverter<Object> actualAnyConverter = new AnyConverter<>(Object.class);
    assertNull(actualAnyConverter.getStringQuote());
    assertNull(actualAnyConverter.decimalConverter);
    assertNull(actualAnyConverter.integralConverter);
    assertEquals("yyyy-MM-dd'T'HH:mm:ss", actualAnyConverter.getDateTimePattern());
    assertEquals("", actualAnyConverter.getNullString());
    assertEquals(Capitalization.mixed, actualAnyConverter.getDateTimeCapitalization());
    assertEquals("yyyy-MM-dd", actualAnyConverter.getDatePattern());
    assertEquals("HH:mm:ss", actualAnyConverter.getTimePattern());
    assertEquals(Capitalization.mixed, actualAnyConverter.getTimestampCapitalization());
    assertEquals("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS", actualAnyConverter.getTimestampPattern());
    assertNull(actualAnyConverter.getCharQuote());
    assertEquals("AnyConverter(Object)", actualAnyConverter.toString());
    assertEquals(Capitalization.mixed, actualAnyConverter.getDateCapitalization());
  }

  @Test
  public void testConstructor2() {
    AnyConverter<Object> actualAnyConverter = new AnyConverter<>(Object.class, "2020-03-01");
    assertNull(actualAnyConverter.getStringQuote());
    assertNull(actualAnyConverter.decimalConverter);
    assertNull(actualAnyConverter.integralConverter);
    assertEquals("yyyy-MM-dd'T'HH:mm:ss", actualAnyConverter.getDateTimePattern());
    assertEquals("", actualAnyConverter.getNullString());
    assertEquals(Capitalization.mixed, actualAnyConverter.getDateTimeCapitalization());
    assertEquals("2020-03-01", actualAnyConverter.getDatePattern());
    assertEquals("HH:mm:ss", actualAnyConverter.getTimePattern());
    assertEquals(Capitalization.mixed, actualAnyConverter.getTimestampCapitalization());
    assertEquals("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS", actualAnyConverter.getTimestampPattern());
    assertNull(actualAnyConverter.getCharQuote());
    assertEquals("AnyConverter(Object)", actualAnyConverter.toString());
    assertEquals(Capitalization.mixed, actualAnyConverter.getDateCapitalization());
  }

  public static final double DELTA = 0.001;

  @Test
  public void testFromNullConversion() throws ConversionException {
    Set<Class<? extends Number>> classes = JavaType.getNumberTypes();
    for (Class<? extends Number> dstType : classes) {
      Number x = AnyConverter.convert(null, dstType);
      assertNull(x);
    }
  }

  @Test
  public void testFromIntConversion() throws ConversionException {
    Set<Class<? extends Number>> classes = JavaType.getNumberTypes();
    for (Class<? extends Number> dstType : classes) {
      Number x = AnyConverter.convert(1, dstType);
      assertEquals(JavaType.getWrapperClass(dstType), x.getClass());
      assertEquals(1., x.doubleValue(), DELTA);
    }
  }

  @Test
  public void testAnyConversion() throws ConversionException {
    Set<Class<? extends Number>> classes = JavaType.getNumberTypes();
    for (Class<? extends Number> srcType : classes) {
      for (Class<? extends Number> dstType : classes) {
        Number s = AnyConverter.convert(1, srcType);
        Number t = AnyConverter.convert(s, dstType);
        assertEquals(JavaType.getWrapperClass(dstType), t.getClass());
        assertEquals(1., t.doubleValue(), DELTA);
      }
    }
  }

  @Test
  public void testToStringConversion() throws ConversionException {
    assertEquals("true", AnyConverter.convert(Boolean.TRUE, String.class));
    assertEquals("false", AnyConverter.convert(Boolean.FALSE, String.class));
    assertEquals("1", AnyConverter.convert(1, String.class));
    assertEquals("0", AnyConverter.convert(0, String.class));
    assertEquals("-1", AnyConverter.convert(-1, String.class));
    assertEquals(null, AnyConverter.convert(null, String.class));
  }

  @Test
  public void testIdConversion() throws ConversionException {
    assertEquals(true, AnyConverter.convert(true, boolean.class));
    assertEquals("text", AnyConverter.convert("text", String.class));
    assertEquals(1, (int) AnyConverter.convert(1, int.class));
  }

  @Test
  public void testFromStringConversion() throws Exception {
    assertEquals(true, AnyConverter.convert("true", Boolean.class));
    assertEquals(true, AnyConverter.convert("true", boolean.class));
    assertEquals(1, (int) AnyConverter.convert("1", Integer.class));
    assertEquals(1, (int) AnyConverter.convert("1", int.class));
    assertEquals(new SimpleDateFormat("S").parse("1"), AnyConverter.convert("00:00:00.001", Time.class));
  }

  @Test
  public void testStringToCharConversion() {
    assertEquals('1', (char) AnyConverter.convert("1", char.class));
  }

  @Test
  public void testBooleanConversion() {
    assertEquals(0, (int) AnyConverter.convert(false, int.class));
    assertEquals(1, (int) AnyConverter.convert(true, int.class));
    assertEquals(1, (int) AnyConverter.convert(Boolean.TRUE, int.class));
    assertEquals(1L, (long) AnyConverter.convert(Boolean.TRUE, Long.class));
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("sourceValue", (new AnyConverter<>(Object.class)).convert("sourceValue"));
    assertEquals("sourceValue", (new AnyConverter<>(Object.class, "2020-03-01")).convert("sourceValue"));
    assertNull((new AnyConverter<>(Object.class)).convert(null));
    assertEquals("source", AnyConverter.convert("source", Object.class));
    assertNull(AnyConverter.convert(null, Object.class));
    assertEquals("source",
        AnyConverter.convert("source", Object.class, "2020-03-01", "Time Pattern", "Timestamp Pattern"));
    assertNull(AnyConverter.convert(null, Object.class, "2020-03-01", "Time Pattern", "Timestamp Pattern"));
    assertEquals("source",
        AnyConverter.convert("source", Object.class, "2020/03/01", "Time Pattern", "Timestamp Pattern"));
  }

  @Test
  public void testToString() {
    assertEquals("AnyConverter(Object)", (new AnyConverter<>(Object.class)).toString());
    assertEquals("AnyConverter(Object)", (new AnyConverter<>(Object.class, "2020-03-01")).toString());
  }

}

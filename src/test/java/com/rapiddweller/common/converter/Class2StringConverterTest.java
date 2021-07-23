package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Class2StringConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    Class<?> clazz = Object.class;
    assertEquals("java.lang.Object", (new Class2StringConverter()).convert(clazz));
  }
}


package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Base64ToByteArrayConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals(0, (new Base64ToByteArrayConverter()).convert("").length);
  }
}


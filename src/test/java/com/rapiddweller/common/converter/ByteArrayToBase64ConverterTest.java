package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteArrayToBase64ConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals("QUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFB", (new ByteArrayToBase64Converter()).convert(
        new byte[] {65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65}));
  }
}


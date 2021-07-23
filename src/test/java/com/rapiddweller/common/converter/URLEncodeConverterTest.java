package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class URLEncodeConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals("https%3A%2F%2Fexample.org%2Fexample",
        URLEncodeConverter.convert("https://example.org/example", "UTF-8"));
    assertThrows(ConversionException.class,
        () -> URLEncodeConverter.convert("https://example.org/example", "https://example.org/example"));
  }

  @Test
  public void testConvertUTF8() throws ConversionException {
    assertEquals("https%3A%2F%2Fexample.org%2Fexample", URLEncodeConverter.convertUTF8("https://example.org/example"));
  }
}


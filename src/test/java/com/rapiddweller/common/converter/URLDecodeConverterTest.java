package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class URLDecodeConverterTest {

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("https%3A%2F%2Fexample.org%2Fexample",
        new URLDecodeConverter().convert("https://example.org/example"));
    assertNull((new URLDecodeConverter()).convert(""));
    assertEquals("https%3A%2F%2Fexample.org%2Fexample",
        URLDecodeConverter.convert("https://example.org/example", "UTF-8"));
    assertNull(URLDecodeConverter.convert("", "UTF-8"));
    assertThrows(ConversionException.class,
        () -> URLDecodeConverter.convert("https://example.org/example", "not_an_encoding"));
  }

}


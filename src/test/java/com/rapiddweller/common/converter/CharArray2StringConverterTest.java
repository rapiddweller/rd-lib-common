package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharArray2StringConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals("AAAAAAAA",
        (new CharArray2StringConverter()).convert(new char[] {'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A'}));
  }
}


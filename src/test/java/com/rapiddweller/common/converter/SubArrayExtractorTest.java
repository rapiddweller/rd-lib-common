package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SubArrayExtractorTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals(8, (new SubArrayExtractor(1, 1, 1, 1, 1, 1, 1, 1)).convert(new Object[] {"foo", "foo", "foo"}).length);
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new SubArrayExtractor(8, 1, 1, 1, 1, 1, 1, 1)).convert(new Object[] {"foo", "foo", "foo"}));
  }
}


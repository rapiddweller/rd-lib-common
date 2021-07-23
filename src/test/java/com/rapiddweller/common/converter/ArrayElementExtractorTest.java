package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class ArrayElementExtractorTest {
  @Test
  public void testConstructor() {
    ArrayElementExtractor<Object> actualArrayElementExtractor = new ArrayElementExtractor<>(Object.class, 1);
    Class<Object> expectedTargetType = actualArrayElementExtractor.targetType;
    assertSame(expectedTargetType, actualArrayElementExtractor.getTargetType());
    assertEquals("[Ljava.lang.Object;", actualArrayElementExtractor.getSourceType().getName());
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("foo",
        (new ArrayElementExtractor<>(Object.class, 1)).convert(new Object[] {"foo", "foo", "foo"}));
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new ArrayElementExtractor<>(Object.class, -1)).convert(new Object[] {"foo", "foo", "foo"}));
  }
}


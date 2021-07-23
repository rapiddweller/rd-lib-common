package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommaSeparatedListConverterTest {
  @Test
  public void testConstructor() {
    CommaSeparatedListConverter<Object> actualCommaSeparatedListConverter = new CommaSeparatedListConverter<>(
        Object.class);
    assertTrue(actualCommaSeparatedListConverter.isParallelizable());
    assertEquals("[Ljava.lang.Object;", actualCommaSeparatedListConverter.getTargetType().getName());
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals(1,
        ((Object[]) (new CommaSeparatedListConverter<>(Object.class)).convert("Source Value")).length);
  }
}


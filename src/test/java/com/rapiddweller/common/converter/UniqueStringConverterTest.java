package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class UniqueStringConverterTest {
  @Test
  public void testConstructor() {
    UniqueStringConverter actualUniqueStringConverter = new UniqueStringConverter();
    Class<String> expectedTargetType = actualUniqueStringConverter.targetType;
    Class<String> targetType = actualUniqueStringConverter.getTargetType();
    assertSame(expectedTargetType, targetType);
    assertSame(targetType, actualUniqueStringConverter.getSourceType());
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("Source Value", (new UniqueStringConverter()).convert("Source Value"));
  }
}


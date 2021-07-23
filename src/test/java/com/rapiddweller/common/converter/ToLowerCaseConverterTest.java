package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ToLowerCaseConverterTest {
  @Test
  public void testConstructor() {
    ToLowerCaseConverter actualToLowerCaseConverter = new ToLowerCaseConverter();
    Class<String> expectedTargetType = actualToLowerCaseConverter.targetType;
    Class<String> targetType = actualToLowerCaseConverter.getTargetType();
    assertSame(expectedTargetType, targetType);
    assertSame(targetType, actualToLowerCaseConverter.getSourceType());
  }
}


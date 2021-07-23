package com.rapiddweller.common.converter;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ToUpperCaseConverterTest {
  @Test
  public void testConstructor() {
    ToUpperCaseConverter actualToUpperCaseConverter = new ToUpperCaseConverter();
    Class<String> expectedTargetType = actualToUpperCaseConverter.targetType;
    Class<String> targetType = actualToUpperCaseConverter.getTargetType();
    assertSame(expectedTargetType, targetType);
    assertSame(targetType, actualToUpperCaseConverter.getSourceType());
  }
}


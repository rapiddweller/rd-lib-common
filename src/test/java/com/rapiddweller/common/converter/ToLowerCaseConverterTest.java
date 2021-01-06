package com.rapiddweller.common.converter;

import static org.junit.Assert.assertSame;

import org.junit.Test;

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


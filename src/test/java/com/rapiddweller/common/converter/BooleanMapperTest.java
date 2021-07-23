package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class BooleanMapperTest {
  @Test
  public void testConstructor() {
    BooleanMapper<Object> actualBooleanMapper = new BooleanMapper<>();
    Class<Object> expectedTargetType = actualBooleanMapper.targetType;
    assertSame(expectedTargetType, actualBooleanMapper.getTargetType());
    Class<Boolean> expectedSourceType = actualBooleanMapper.sourceType;
    assertSame(expectedSourceType, actualBooleanMapper.getSourceType());
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("true", (new BooleanMapper<>()).convert(true));
    assertEquals("false", (new BooleanMapper<>()).convert(false));
    assertNull((new BooleanMapper<>()).convert(null));
  }

  @Test
  public void testConstructor2() {
    BooleanMapper<Object> actualBooleanMapper = new BooleanMapper<>("trueValue", "falseValue", "nullValue");
    Class<Object> expectedTargetType = actualBooleanMapper.targetType;
    assertSame(expectedTargetType, actualBooleanMapper.getTargetType());
    Class<Boolean> expectedSourceType = actualBooleanMapper.sourceType;
    assertSame(expectedSourceType, actualBooleanMapper.getSourceType());
  }
}


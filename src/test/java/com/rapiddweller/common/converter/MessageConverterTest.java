package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MessageConverterTest {
  @Test
  public void testConstructor() {
    MessageConverter actualMessageConverter = new MessageConverter();
    Class<String> expectedTargetType = actualMessageConverter.targetType;
    assertSame(expectedTargetType, actualMessageConverter.getTargetType());
    Class<Object> expectedSourceType = actualMessageConverter.sourceType;
    assertSame(expectedSourceType, actualMessageConverter.getSourceType());
  }

  @Test
  public void testConstructor2() {
    MessageConverter actualMessageConverter = new MessageConverter("Pattern");
    Class<String> expectedTargetType = actualMessageConverter.targetType;
    assertSame(expectedTargetType, actualMessageConverter.getTargetType());
    Class<Object> expectedSourceType = actualMessageConverter.sourceType;
    assertSame(expectedSourceType, actualMessageConverter.getSourceType());
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("sourceValue", (new MessageConverter()).convert("sourceValue"));
    assertEquals("{0}", (new MessageConverter()).convert(null));
  }
}


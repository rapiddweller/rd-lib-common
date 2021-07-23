package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.condition.EqualsCondition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConditionalConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    EqualsCondition<Object> condition = new EqualsCondition<>("reference");
    assertEquals("sourceValue",
        (new ConditionalConverter(condition, new Base64ToByteArrayConverter())).convert("sourceValue"));
  }

  @Test
  public void testConvert2() throws ConversionException {
    EqualsCondition<Object> condition = new EqualsCondition<>("sourceValue");
    assertEquals("SOURCEVALUE", (new ConditionalConverter(condition, new CaseConverter())).convert("sourceValue"));
  }
}


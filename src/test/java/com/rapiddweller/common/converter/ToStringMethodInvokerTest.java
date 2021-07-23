package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ToStringMethodInvokerTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals("sourceValue", (new ToStringMethodInvoker<>(Object.class)).convert("sourceValue"));
    assertNull((new ToStringMethodInvoker<>(Object.class)).convert(null));
  }
}


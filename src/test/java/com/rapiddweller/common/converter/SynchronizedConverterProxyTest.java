package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SynchronizedConverterProxyTest {
  @Test
  public void testConvert() throws ConversionException {
    assertEquals(1, ((ArrayList) (new SynchronizedConverterProxy<Object, Object>(new ToCollectionConverter()))
        .convert("sourceValue")).size());
    assertEquals(1,
        ((ArrayList) (new SynchronizedConverterProxy<>(
            new SynchronizedConverterProxy<Object, Object>(new ToCollectionConverter()))).convert("sourceValue"))
            .size());
    assertEquals("sourceValue",
        (new SynchronizedConverterProxy<Object, Object>(new AnyConverter(Object.class))).convert("sourceValue"));
  }
}


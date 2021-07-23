package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class NullSafeConverterProxyTest {
  @Test
  public void testGetTargetType() {
    Class<Object> actualTargetType = (new NullSafeConverterProxy<Object, Object>(new ToCollectionConverter(),
        "nullResult")).getTargetType();
    assertSame(List.class, actualTargetType);
  }

  @Test
  public void testGetTargetType2() {
    Class<Object> actualTargetType = (new NullSafeConverterProxy<>(
        new NullSafeConverterProxy<Object, Object>(new ToCollectionConverter(), "nullResult"), "nullResult"))
        .getTargetType();
    assertSame(List.class, actualTargetType);
  }

  @Test
  public void testConvert() throws ConversionException {
    assertEquals(1, ((ArrayList) (new NullSafeConverterProxy<Object, Object>(new ToCollectionConverter(), "nullResult"))
        .convert("sourceValue")).size());
    assertEquals(1,
        ((ArrayList) (new NullSafeConverterProxy<Object, Object>(new ToCollectionConverter(Collection.class),
            "nullResult")).convert("sourceValue")).size());
    assertEquals(1,
        ((ArrayList) (new NullSafeConverterProxy<>(
            new NullSafeConverterProxy<Object, Object>(new ToCollectionConverter(), "nullResult"), "nullResult"))
            .convert("sourceValue")).size());
    assertEquals("sourceValue",
        (new NullSafeConverterProxy<Object, Object>(new AnyConverter(Object.class), "nullResult"))
            .convert("sourceValue"));
  }

  @Test
  public void testConvert2() throws ConversionException {
    NullSafeConverterProxy<Object, Object> nullSafeConverterProxy = new NullSafeConverterProxy<Object, Object>(
        new ToCollectionConverter(), "nullResult");
    assertSame(nullSafeConverterProxy.nullResult, nullSafeConverterProxy.convert(null));
    assertEquals("nullResult", nullSafeConverterProxy.convert(null));
  }
}


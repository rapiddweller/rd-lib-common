package com.rapiddweller.common.converter;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class String2ZonedDateTimeConverterTest {
  @Test
  public void testConstructor() {
    ZoneId ofOffsetResult = ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(1));
    new String2ZonedDateTimeConverter("Y", ofOffsetResult);
    assertTrue(ofOffsetResult instanceof ZoneOffset);
  }

  @Test
  public void testConvert() {
    assertNull((new String2ZonedDateTimeConverter(null)).convert(null));
  }
}


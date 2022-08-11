package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class Time2StringConverterTest {

  @Test
  public void testNullValue() {
    assertNull(new Time2StringConverter().convert(null));
  }

  @Test
  public void testConvert() throws ConversionException {
    Time target = new Time(10L);
    assertNotNull((new Time2StringConverter()).convert(target));
  }

  @Test
  public void testConvert2() throws ConversionException {
    Time target = new Time(0L);
    assertNotNull((new Time2StringConverter()).convert(target));
  }
}


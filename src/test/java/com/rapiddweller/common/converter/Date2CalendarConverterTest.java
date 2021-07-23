package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class Date2CalendarConverterTest {
  @Test
  public void testConvert() throws ConversionException {
    Date sourceValue = new Date(1L);
    assertTrue((new Date2CalendarConverter()).convert(sourceValue) instanceof java.util.GregorianCalendar);
  }

  @Test
  public void testConvert2() throws ConversionException {
    Date sourceValue = new Date(0L);
    assertTrue((new Date2CalendarConverter()).convert(sourceValue) instanceof java.util.GregorianCalendar);
  }
}


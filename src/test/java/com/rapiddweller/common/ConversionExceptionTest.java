package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ConversionExceptionTest {
  @Test
  public void testConstructor() {
    ConversionException actualConversionException = new ConversionException();
    assertEquals("com.rapiddweller.common.ConversionException", actualConversionException.toString());
    assertNull(actualConversionException.getLocalizedMessage());
    assertNull(actualConversionException.getCause());
    assertNull(actualConversionException.getMessage());
    assertEquals(0, actualConversionException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    ConversionException actualConversionException = new ConversionException("An error occurred");
    assertEquals("com.rapiddweller.common.ConversionException: An error occurred",
        actualConversionException.toString());
    assertEquals("An error occurred", actualConversionException.getLocalizedMessage());
    assertNull(actualConversionException.getCause());
    assertEquals("An error occurred", actualConversionException.getMessage());
    assertEquals(0, actualConversionException.getSuppressed().length);
  }

  @Test
  public void testConstructor3() {
    Throwable throwable = new Throwable();
    assertSame((new ConversionException("An error occurred", throwable)).getCause(), throwable);
  }

  @Test
  public void testConstructor4() {
    Throwable throwable = new Throwable();
    assertSame((new ConversionException(throwable)).getCause(), throwable);
  }
}


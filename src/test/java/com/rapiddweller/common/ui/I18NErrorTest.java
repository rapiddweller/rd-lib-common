package com.rapiddweller.common.ui;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class I18NErrorTest {
  @Test
  public void testConstructor() {
    I18NError actualI18nError = new I18NError();
    assertEquals("com.rapiddweller.common.ui.I18NError", actualI18nError.toString());
    assertNull(actualI18nError.getLocalizedMessage());
    assertNull(actualI18nError.getCause());
    assertNull(actualI18nError.getMessage());
    assertEquals(0, actualI18nError.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    I18NError actualI18nError = new I18NError("Code");
    assertEquals("com.rapiddweller.common.ui.I18NError: Code", actualI18nError.toString());
    assertEquals("Code", actualI18nError.getLocalizedMessage());
    assertNull(actualI18nError.getCause());
    assertEquals("Code", actualI18nError.getMessage());
    assertEquals(0, actualI18nError.getSuppressed().length);
  }

  @Test
  public void testConstructor3() {
    Object[] objectArray = new Object[] {"foo", "foo", "foo"};
    Throwable throwable = new Throwable();
    assertSame((new I18NError("Code", throwable, objectArray)).getCause(), throwable);
    assertEquals(3, objectArray.length);
  }

  @Test
  public void testConstructor4() {
    Throwable throwable = new Throwable();
    assertSame((new I18NError(throwable)).getCause(), throwable);
  }
}


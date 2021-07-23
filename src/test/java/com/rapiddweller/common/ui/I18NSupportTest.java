package com.rapiddweller.common.ui;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Locale;
import java.util.MissingResourceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class I18NSupportTest {
  @Ignore
  @Test
  public void testConstructor() throws MissingResourceException {
    Locale locale = new Locale("en");
    new I18NSupport("Name", locale);
    assertEquals("en", locale.getLanguage());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", locale.getDisplayScript());
    assertFalse(locale.hasExtensions());
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("English", locale.getDisplayName());
    assertEquals("", locale.getISO3Country());
    assertEquals("en", locale.toString());
  }
}


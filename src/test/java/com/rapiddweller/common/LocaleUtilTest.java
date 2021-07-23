/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

import org.junit.Test;

import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link LocaleUtil} class.
 * Created: 27.08.2006 21:13:29
 *
 * @author Volker Bergmann
 */
public class LocaleUtilTest {

  @Test
  public void testLetters() {
    check("de");
    check("de", "ch");
    check("de", "at");
    check("en");
    check("en", "us");
    check("en", "uk");
    check("fr");
    check("fr", "ca");
    check("en", "ch");
    check("it");
    check("it", "ch");
    check("es");
  }

  @Test
  public void testParent() {
    assertEquals(null, LocaleUtil.parent(new Locale("de")));
    assertEquals(new Locale("de"), LocaleUtil.parent(new Locale("de", "DE")));
    assertEquals(new Locale("de", "DE"), LocaleUtil.parent(new Locale("de", "DE", "BY")));
    assertEquals(new Locale("de", "DE", "BY"), LocaleUtil.parent(new Locale("de", "DE", "BY_MUC")));
    assertEquals(new Locale("de", "DE", "BY_MUC"), LocaleUtil.parent(new Locale("de", "DE", "BY_MUC_SCHWABING")));
  }

  @Test
  public void testGetFallbackLocale() {
    assertEquals(Locale.US, LocaleUtil.getFallbackLocale());
  }

  @Test
  public void testGetLocale() {
    assertEquals(Locale.GERMAN, LocaleUtil.getLocale("de"));
    assertEquals(Locale.GERMANY, LocaleUtil.getLocale("de_DE"));
    assertEquals(new Locale("de", "DE", "BY"), LocaleUtil.getLocale("de_DE_BY"));
    assertEquals(new Locale("de", "DE", "BY_MUC"), LocaleUtil.getLocale("de_DE_BY_MUC"));
    assertEquals(new Locale("de", "DE", "BY_MUC_SCHWABING"), LocaleUtil.getLocale("de_DE_BY_MUC_SCHWABING"));
  }

  @Test
  public void testGetDefaultCountryCode() {
    Locale defaultLocale = Locale.getDefault();
    try {
      Locale.setDefault(Locale.GERMANY);
      assertEquals("DE", LocaleUtil.getDefaultCountryCode());
      Locale.setDefault(Locale.GERMAN);
      assertEquals("US", LocaleUtil.getDefaultCountryCode());
      Locale.setDefault(Locale.US);
      assertEquals("US", LocaleUtil.getDefaultCountryCode());
    } finally {
      Locale.setDefault(defaultLocale);
    }
  }

  @Test
  public void testLanguage() {
    assertEquals(Locale.GERMAN, LocaleUtil.language(Locale.GERMANY));
    assertEquals(Locale.ENGLISH, LocaleUtil.language(Locale.US));
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void check(String language) {
    check(new Locale(language));
  }

  private static void check(String language, String country) {
    check(new Locale(language, country));
  }

  private static void check(Locale locale) {
    Set<Character> set = LocaleUtil.letters(locale);
    assertTrue(set.contains('A'));
    assertTrue(set.contains('a'));
    if ("DE".equals(locale.getCountry())) {
      assertTrue(set.contains('�'));
    }
    if ("de".equals(locale.getLanguage()) && "CH".equals(locale.getCountry())) {
      assertFalse(set.contains('�'));
    }
  }

}

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link CharSet}.
 * Created: 21.06.2007 08:28:50
 *
 * @author Volker Bergmann
 */
public class CharSetTest {

  @Test
  public void testDefaultConstructor() {
    CharSet set = new CharSet();
    assertEquals("Set is expected to be empty after default construction.", 0, set.size());
  }

  @Test
  public void testGerman() {
    CharSet set = new CharSet(Locale.GERMAN);
    assertEquals("Set is expected to be empty after construction with Locale.", 0, set.size());
    set.addWordChars();
    assertEquals(70, set.size());
    assertTrue(set.contains('a'));
    assertTrue(set.contains('ä'));
    assertTrue(set.contains('_'));
    assertTrue(set.contains('9'));
    set.removeDigits();
    assertEquals(60, set.size());
    assertFalse(set.contains('9'));
    set.removeAll();
    assertEquals(0, set.size());
  }

  @Test
  public void testEqualsAndHashCode() {
    CharSet sg = new CharSet(Locale.GERMAN);
    CharSet se = new CharSet(Locale.ENGLISH);
    assertTrue(sg.equals(se));
    assertEquals(sg.hashCode(), se.hashCode());
    sg.add('a');
    se.add('a');
    assertTrue(sg.equals(se));
    assertEquals(sg.hashCode(), se.hashCode());
    sg.add('ä');
    assertFalse(sg.equals(se));
    assertTrue(sg.hashCode() != se.hashCode());
  }

}

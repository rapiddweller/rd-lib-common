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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Testing {@link RegexUtilTest}.<br><br>
 * Created: 23.10.2019 09:45:40
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */

public class RegexUtilTest {

  private static final String PATTERN = "[A-Z]{4}Y";

  @Test
  public void testParse() {
    assertNull(RegexUtil.parse("Text", "Regex"));
    assertEquals(0, RegexUtil.parse("Regex", "Regex").length);
  }

  @Test
  public void testMatches() {
    assertTrue(RegexUtil.matches(PATTERN, "ABCDY"));
    assertFalse(RegexUtil.matches(PATTERN, "ABCY"));
    assertFalse(RegexUtil.matches(PATTERN, "abcdY"));
    assertFalse(RegexUtil.matches(PATTERN, "ABCDE"));
    assertFalse(RegexUtil.matches("Regex", "Text"));
    assertTrue(RegexUtil.matches("Regex", "Regex"));
  }

}

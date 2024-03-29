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

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link SubstringExtractor}.
 * Created: 26.02.2010 11:05:31
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class SubstringExtractorTest extends AbstractConverterTest {

  @Test
  public void testConvert() throws ConversionException {
    assertEquals("Source Value", (new SubstringExtractor()).convert("Source Value"));
    assertEquals("", (new SubstringExtractor(1, 1)).convert("Source Value"));
    assertNull((new SubstringExtractor()).convert(null));
    assertEquals("", (new SubstringExtractor()).convert(""));
    assertEquals("", (new SubstringExtractor(-1, 1)).convert("Source Value"));
  }

  public SubstringExtractorTest() {
    super(SubstringExtractor.class);
  }

  @Test
  public void testDefault() {
    assertEquals("ABC", new SubstringExtractor().convert("ABC"));
  }

  @Test
  public void testWithoutTo() {
    assertEquals("ABC", new SubstringExtractor(0).convert("ABC"));
    assertEquals("BC", new SubstringExtractor(1).convert("ABC"));
    assertEquals("BC", new SubstringExtractor(-2).convert("ABC"));
    assertEquals("", new SubstringExtractor(3).convert("ABC"));
  }

  @Test
  public void testStringEnd() {
    assertEquals("BC", new SubstringExtractor(-2, 0).convert("ABC"));
    assertEquals("ABC", new SubstringExtractor(-4, 0).convert("ABC"));
    assertEquals("B", new SubstringExtractor(-2, -1).convert("ABC"));
    assertEquals("A", new SubstringExtractor(-4, -2).convert("ABC"));
    assertEquals("", new SubstringExtractor(-20, -10).convert("ABC"));
  }

  @Test
  public void testWithTo() {
    assertEquals("ABC", new SubstringExtractor(0, 3).convert("ABC"));
    assertEquals("", new SubstringExtractor(0, 0).convert("ABC"));
    assertEquals("", new SubstringExtractor(3, 3).convert("ABC"));
    assertEquals("AB", new SubstringExtractor(0, 2).convert("ABC"));
    assertEquals("B", new SubstringExtractor(1, 2).convert("ABC"));
    assertEquals("B", new SubstringExtractor(-2, 2).convert("ABC"));
    assertEquals("C", new SubstringExtractor(-1, 3).convert("ABC"));
    assertEquals("AB", new SubstringExtractor(-3, -1).convert("ABC"));
  }

  @Test
  public void testTooShortArgument() {
    assertEquals("", new SubstringExtractor(0, 3).convert(""));
    assertEquals("AB", new SubstringExtractor(0, 3).convert("AB"));
    assertEquals("", new SubstringExtractor(4, 11).convert(""));
    assertEquals("ICE", new SubstringExtractor(2, 8).convert("ALICE"));
  }

}

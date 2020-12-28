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
package com.rapiddweller.commons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Locale;

import com.rapiddweller.commons.converter.ToStringConverter;

/**
 * Tests the {@link ArrayFormat}.
 * Created: 20.06.2007 08:52:26
 * @author Volker Bergmann
 */
public class ArrayFormatTest {

    private static final Locale[] LOCALES = { Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH };

	@Test
    public void testInstanceFormat() {
        assertEquals("1, 2, 3", ArrayFormat.format(1, 2, 3));
    }

	@Test
    public void testParse() {
        String[] tokens = ArrayFormat.parse("a, b, c", ", ", String.class);
        assertTrue(Arrays.equals(new String[] {"a", "b", "c"}, tokens));
    }

	@Test
    public void testFormatSimple() {
        assertEquals("1, 2, 3", ArrayFormat.format(1, 2, 3));
    }

	@Test
    public void testFormatWithSeparator() {
        assertEquals("de_DE_BY", ArrayFormat.formatStrings("_", "de", "DE", "BY"));
    }

	@Test
    public void testFormatWithFormatAndSeparator() {
        assertEquals("de/en/fr", ArrayFormat.format(new ToStringConverter(), "/", LOCALES));
    }

	@Test
    public void testFormatPartSimple() {
        assertEquals("de, en", ArrayFormat.formatPart(0, 2, LOCALES));
    }

	@Test
    public void testFormatPartWithSeparator() {
        assertEquals("de/en", ArrayFormat.formatPart("/", 0, 2, LOCALES));
    }

	@Test
    public void testFormatPartWithFormatAndSeparator() {
        assertEquals("de/en", ArrayFormat.formatPart(new ToStringConverter(), "/", 0, 2, LOCALES));
    }
	@Test
    public void testFormatIntArray() {
        assertEquals("1.2.3", ArrayFormat.formatInts(".", 1, 2, 3));
    }
}

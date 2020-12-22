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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link StringCharacterIterator}.
 * Created: 21.06.2007 08:34:31
 * @author Volker Bergmann
 */
public class StringCharacterIteratorTest {

	@Test
    public void testIteration() {
    	StringCharacterIterator iterator = new StringCharacterIterator("abc");
        assertTrue(iterator.hasNext());
        assertEquals('a', iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals('b', iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals('c', iterator.next());
        assertFalse(iterator.hasNext());
    }

	@Test
    public void testLifeCycle() {
    	StringCharacterIterator iterator = new StringCharacterIterator("ab");
        assertTrue(iterator.hasNext());
        assertEquals('a', iterator.next());
        iterator.assertNext('b');
        assertFalse(iterator.hasNext());
        iterator.pushBack();
        assertTrue(iterator.hasNext());
        iterator.assertNext('b');
        assertFalse(iterator.hasNext());
    }

	@Test
    public void testLineColumn() {
    	StringCharacterIterator iterator = new StringCharacterIterator("a\nb");
    	assertPosition(1, 1, iterator);
    	iterator.next();
    	assertPosition(1, 2, iterator);
    	iterator.next();
    	assertPosition(2, 1, iterator);
    	iterator.pushBack();
    	assertPosition(1, 2, iterator);
    	iterator.pushBack();
    	assertPosition(1, 1, iterator);
    }

	private static void assertPosition(int expectedLine, int expectedColumn, StringCharacterIterator iterator) {
		assertEquals("Unexpected line,", expectedLine, iterator.line());
		assertEquals("Unexpected column,", expectedColumn, iterator.column());
	}
}

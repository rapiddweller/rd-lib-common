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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link StringCharacterIterator}.
 * Created: 21.06.2007 08:34:31
 *
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

    @Test
    public void testConstructor() {
        StringCharacterIterator actualStringCharacterIterator = new StringCharacterIterator("Source");
        assertEquals(1, actualStringCharacterIterator.column());
        assertEquals(1, actualStringCharacterIterator.line());
        assertEquals("Source", actualStringCharacterIterator.toString());
        assertEquals(0, actualStringCharacterIterator.getOffset());
    }

    @Test
    public void testConstructor2() {
        assertThrows(IllegalArgumentException.class, () -> new StringCharacterIterator(null));
    }

    @Test
    public void testConstructor3() {
        StringCharacterIterator actualStringCharacterIterator = new StringCharacterIterator("Source", 2);
        assertEquals(1, actualStringCharacterIterator.column());
        assertEquals(1, actualStringCharacterIterator.line());
        assertEquals("Source", actualStringCharacterIterator.toString());
        assertEquals(2, actualStringCharacterIterator.getOffset());
    }

    @Test
    public void testConstructor4() {
        assertThrows(IllegalArgumentException.class, () -> new StringCharacterIterator(null, 2));
    }

    @Test
    public void testHasNext() {
        assertTrue((new StringCharacterIterator("Source")).hasNext());
        assertFalse((new StringCharacterIterator("")).hasNext());
    }

    @Test
    public void testNext() {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("Source");
        assertEquals('S', stringCharacterIterator.next());
        assertEquals(2, stringCharacterIterator.column());
        assertEquals(1, stringCharacterIterator.getOffset());
    }

    @Test
    public void testNext2() {
        assertThrows(IllegalStateException.class, () -> (new StringCharacterIterator("")).next());
    }

    @Test
    public void testPeekNext() {
        assertEquals('S', (new StringCharacterIterator("Source")).peekNext());
        assertEquals('\u0000', (new StringCharacterIterator("")).peekNext());
    }

    @Test
    public void testPushBack() {
        assertThrows(IllegalStateException.class, () -> (new StringCharacterIterator("Source")).pushBack());
    }

    @Test
    public void testPushBack2() {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("Source", 2);
        stringCharacterIterator.pushBack();
        assertEquals(0, stringCharacterIterator.column());
        assertEquals(1, stringCharacterIterator.getOffset());
    }

    @Test
    public void testPushBack3() {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("", 2);
        stringCharacterIterator.pushBack();
        assertEquals(0, stringCharacterIterator.column());
        assertEquals(1, stringCharacterIterator.getOffset());
    }

    @Test
    public void testSetOffset() {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("Source");
        stringCharacterIterator.setOffset(2);
        assertEquals(2, stringCharacterIterator.getOffset());
    }

    @Test
    public void testParseLetters() {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("Source");
        assertEquals("Source", stringCharacterIterator.parseLetters());
        assertEquals(6, stringCharacterIterator.getOffset());
    }

    @Test
    public void testRemainingText() {
        assertEquals("Source", (new StringCharacterIterator("Source")).remainingText());
    }

    @Test
    public void testAssertNext() {
        assertThrows(ParseException.class, () -> (new StringCharacterIterator("Source")).assertNext('A'));
        assertThrows(ParseException.class, () -> (new StringCharacterIterator("")).assertNext('A'));
    }

    @Test
    public void testAssertNext2() {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("Source");
        stringCharacterIterator.assertNext('S');
        assertEquals(2, stringCharacterIterator.column());
        assertEquals(1, stringCharacterIterator.getOffset());
    }

    @Test
    public void testLine() {
        assertEquals(1, (new StringCharacterIterator("Source")).line());
    }

    @Test
    public void testColumn() {
        assertEquals(1, (new StringCharacterIterator("Source")).column());
    }
}

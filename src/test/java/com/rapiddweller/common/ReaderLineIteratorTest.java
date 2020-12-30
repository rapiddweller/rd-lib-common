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
import com.rapiddweller.common.SystemInfo;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Reader;

/**
 * Tests the {@link ReaderLineIterator}.
 * Created: 01.05.2007 09:36:47
 * @author Volker Bergmann
 */
public class ReaderLineIteratorTest {

    private static final String SEP = SystemInfo.getLineSeparator();

	@Test
    public void testDefaultIteration() {
        Reader reader = new StringReader("alpha " + SEP + " beta" + SEP);
        ReaderLineIterator iterator = new ReaderLineIterator(reader);
        checkIteration(iterator);
    }
    
	@Test
    public void testSkipEmptyLines() {
        Reader reader = new StringReader("alpha " + SEP + SEP + " beta" + SEP);
        ReaderLineIterator iterator = new ReaderLineIterator(reader, true);
        checkIteration(iterator);
    }

    private static void checkIteration(ReaderLineIterator iterator) {
	    assertTrue(iterator.hasNext());
        assertEquals("alpha ", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(" beta", iterator.next());
        assertFalse(iterator.hasNext());
    }
    
}

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
package com.rapiddweller.commons.iterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Parent class for {@link Iterator} test classes.
 * @author Volker Bergmann
 */
public abstract class IteratorTestCase {

    public static <T> void checkUniqueIteration(Iterator<T> iterator, int count) {
        Set<T> items = new HashSet<>(count);
        for (int i = 0; i < count; i++) {
            assertTrue(iterator.hasNext());
            T item = iterator.next();
            assertFalse(items.contains(item)); // check uniqueness
            items.add(item);
        }
    }

	@SafeVarargs
	public static <T> NextHelper expectNextElements(Iterator<?> iterator, T... expectedValues) {
		for (T expected : expectedValues) {
			assertTrue("Iterator is expected to have a next, but does not", iterator.hasNext());
			Object actual = iterator.next();
			assertEquals(expected, actual);
		}
		return new NextHelper(iterator);
	}
	
	public static class NextHelper {
		
		Iterator<?> iterator;

		public NextHelper(Iterator<?> iterator) {
			this.iterator = iterator;
		}
		
		public void withNext() {
			assertTrue("Iterator is expected to have a next, but it does not", iterator.hasNext());
		}
		
		public void withNoNext() {
			assertFalse("Iterator is expected to have no next, but it has", iterator.hasNext());
		}
	}
	
}

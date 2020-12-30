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
import static org.junit.Assert.assertTrue;

import java.util.*;

/**
 * Tests the {@link ArrayUtil} class.
 * Created: 20.06.2007 18:03:10
 * @author Volker Bergmann
 */
public class ArrayUtilTest {

    private final Integer[] NONE         = {            };
    private final Integer[] ONE          = { 1          };
    private final Integer[] ONE_TWO      = { 1, 2       };
    private final Integer[] TWO_ONE      = { 2, 1       };
    private final Integer[] ONE_TO_THREE = { 1, 2, 3    };
    private final Integer[] THREE_TO_ONE = { 3, 2, 1    };
    private final Integer[] ONE_TO_FOUR  = { 1, 2, 3, 4 };
    private final Integer[] FOUR_TO_ONE  = { 4, 3, 2, 1 };

	@Test
    public void testCopyOfRange() {
        Integer[] array = new Integer[] { 0, 1, 2 };
        assertTrue(Arrays.equals(new Integer[0], ArrayUtil.copyOfRange(array, 0, 0)));
        assertTrue(Arrays.equals(new Integer[] { 0 }, ArrayUtil.copyOfRange(array, 0, 1)));
        assertTrue(Arrays.equals(new Integer[] { 1 }, ArrayUtil.copyOfRange(array, 1, 1)));
        assertTrue(Arrays.equals(new Integer[] { 2 }, ArrayUtil.copyOfRange(array, 2, 1)));
        assertTrue(Arrays.equals(new Integer[] { 0, 1 }, ArrayUtil.copyOfRange(array, 0, 2)));
        assertTrue(Arrays.equals(new Integer[] { 1, 2 }, ArrayUtil.copyOfRange(array, 1, 2)));
        assertTrue(Arrays.equals(new Integer[] { 0, 1, 2 }, ArrayUtil.copyOfRange(array, 0, 3)));
    }

	@Test
    public void testRemove() {
        assertTrue(Arrays.equals(new Integer[] { 2, 3 }, ArrayUtil.remove(0, ONE_TO_THREE)));
        assertTrue(Arrays.equals(new Integer[] { 1, 3 }, ArrayUtil.remove(1, ONE_TO_THREE)));
        assertTrue(Arrays.equals(new Integer[] { 1, 2 }, ArrayUtil.remove(2, ONE_TO_THREE)));
    }

	@Test
    public void testRemoveAll() {
        assertTrue(Arrays.equals(new Integer[] { 3, 4 }, ArrayUtil.removeAll(ONE_TWO, ONE_TO_FOUR)));
        assertTrue(Arrays.equals(new Integer[0], ArrayUtil.removeAll(ONE_TO_FOUR, ONE_TO_FOUR)));
        assertTrue(Arrays.equals(ONE_TWO, ArrayUtil.removeAll(new Integer[] { 3, 4 }, ONE_TO_FOUR)));
        assertTrue(Arrays.equals(new Integer[] { 2, 3 }, ArrayUtil.removeAll(new Integer[] { 1, 4 }, ONE_TO_FOUR)));
    }

	@Test
    public void testContains() {
        assertTrue( ArrayUtil.contains(1, ONE_TO_THREE));
        assertTrue( ArrayUtil.contains(2, ONE_TO_THREE));
        assertTrue( ArrayUtil.contains(3, ONE_TO_THREE));
        assertFalse(ArrayUtil.contains(0, ONE_TO_THREE));
        assertFalse(ArrayUtil.contains(4, ONE_TO_THREE));
    }

	@Test
    public void testEndsWithSequence() {
        assertTrue(ArrayUtil.endsWithSequence(ONE_TO_THREE, new Integer[] { 1, 2, 3 }));
        assertTrue(ArrayUtil.endsWithSequence(ONE_TO_THREE, new Integer[] { 2, 3 }));
        assertTrue(ArrayUtil.endsWithSequence(ONE_TO_THREE, new Integer[] { 3 }));
        assertTrue(ArrayUtil.endsWithSequence(ONE_TO_THREE, new Integer[] { }));
        assertFalse(ArrayUtil.endsWithSequence(ONE_TO_THREE, new Integer[] { 0, 1, 2, 3 }));
        assertFalse(ArrayUtil.endsWithSequence(ONE_TO_THREE, new Integer[] { 2, 2 }));
    }

	@Test
    public void testCommonElements() {
        assertTrue(Arrays.equals(ONE_TO_THREE, ArrayUtil.commonElements(ONE_TO_THREE, ONE_TO_THREE)));
        assertTrue(Arrays.equals(ONE, ArrayUtil.commonElements(new Integer[] {0, 1}, ONE_TO_THREE)));
        assertTrue(Arrays.equals(NONE, ArrayUtil.commonElements(new Integer[] {8}, ONE_TO_THREE)));
        assertTrue(Arrays.equals(NONE, ArrayUtil.commonElements(new Integer[0], ONE_TO_THREE)));
    }

	@Test
    public void testEqualsIgnoreOrder() {
        assertTrue(ArrayUtil.equalsIgnoreOrder(ONE_TO_THREE, ONE_TO_THREE));
        assertTrue(ArrayUtil.equalsIgnoreOrder(new Integer[] { 3, 2, 1 }, ONE_TO_THREE));
        assertFalse(ArrayUtil.equalsIgnoreOrder(new Integer[] {2, 3, 4}, ONE_TO_THREE));
    }

	@Test
    public void testIndexOf() {
        assertEquals(-1, ArrayUtil.indexOf(0, ONE_TO_THREE));
        assertEquals( 0, ArrayUtil.indexOf(1, ONE_TO_THREE));
        assertEquals( 1, ArrayUtil.indexOf(2, ONE_TO_THREE));
        assertEquals( 2, ArrayUtil.indexOf(3, ONE_TO_THREE));
        assertEquals(-1, ArrayUtil.indexOf(4, ONE_TO_THREE));
    }
    
	@Test
    public void testToArray() {
        assertTrue(Arrays.equals(ONE_TO_THREE, ArrayUtil.toArray(1, 2, 3)));
    }
    
	@Test
    public void testRevertObjects() {
        assertTrue(Arrays.equals(TWO_ONE, ArrayUtil.revert(ONE_TWO)));
        assertTrue(Arrays.equals(THREE_TO_ONE, ArrayUtil.revert(ONE_TO_THREE)));
        assertTrue(Arrays.equals(FOUR_TO_ONE, ArrayUtil.revert(ONE_TO_FOUR)));
    }
    
	@Test
    public void testRevertChars() {
        assertTrue(Arrays.equals(new char[] {'c', 'b', 'a'}, ArrayUtil.revert(new char[] {'a', 'b', 'c'})));
        assertTrue(Arrays.equals(new char[] {'a'}, ArrayUtil.revert(new char[] {'a'})));
        assertTrue(Arrays.equals(new char[0], ArrayUtil.revert(new char[0])));
    }
    
	@Test
    public void testArrayType() {
        assertEquals(String[].class, ArrayUtil.arrayType(String.class));
    }

	@Test
    public void testNewInstance() {
        Integer[] instance = ArrayUtil.newInstance(Integer.class, 3);
        assertTrue(Arrays.equals(new Integer[3], instance));
    }

}

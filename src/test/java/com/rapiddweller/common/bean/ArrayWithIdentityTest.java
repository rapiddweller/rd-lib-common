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
package com.rapiddweller.common.bean;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests the {@link ArrayWithIdentity}.
 * Created: 05.02.2012 09:40:15
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class ArrayWithIdentityTest {

	@Test
	public void testGetElementCount() {
		assertEquals(2, new ArrayWithIdentity(new Object[] { 1, 2 }).getElementCount());
	}
	
	@Test
	public void testEquals() {
		ArrayWithIdentity array1 = new ArrayWithIdentity(new Object[] { 1, 2 });
		ArrayWithIdentity array2 = new ArrayWithIdentity(new Object[] { 1, 2 });
		ArrayWithIdentity array3 = new ArrayWithIdentity(new Object[] { 2, 1 });
		assertEquals(array1, array2);
		assertEquals(array2, array1);
		assertFalse(array1.equals(array3));
		assertFalse(array3.equals(array1));
	}
	
	@Test
	public void testHashcode() {
		assertEquals(Arrays.hashCode(new int[] { 1, 2 }), new ArrayWithIdentity(new Object[] { 1, 2 }).hashCode());
		assertEquals(Arrays.hashCode(new int[] { 1, 3 }), new ArrayWithIdentity(new Object[] { 1, 3 }).hashCode());
	}
	
	@Test
	public void testToString() {
		assertEquals("1, 2", new ArrayWithIdentity(new Object[] { 1, 2 }).toString());
	}
	
}

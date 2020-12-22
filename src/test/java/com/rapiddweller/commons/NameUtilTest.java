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

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Tests the {@link NameUtil} class.
 * Created: 12.08.2010 11:47:03
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NameUtilTest {
	
	Named AB  = new X("AB");
	Named A_B = new X("A_B");
	Named AC  = new X("AC");
	Named A_C = new X("A_C");
	
	@Test
	public void testOrderByName() {
		Named[] array = new Named[] { A_C, AC, A_B, AB };
		NameUtil.orderByName(array);
		Named[] expected = new Named[] { A_B, A_C, AB, AC };
		assertTrue(Arrays.equals(expected, array));
	}
	
	@Test
	public void testIndexOf_list() {
		List<Named> list = CollectionUtil.toList(A_C, AC, A_B, AB);
		assertEquals(-1, NameUtil.indexOf("XY", list));
		assertEquals( 0, NameUtil.indexOf("A_C", list));
		assertEquals( 3, NameUtil.indexOf("AB", list));
	}
	
	@Test
	public void testIndexOf_array() {
		Named[] list = new Named[] { A_C, AC, A_B, AB };
		assertEquals(-1, NameUtil.indexOf("XY", list));
		assertEquals( 0, NameUtil.indexOf("A_C", list));
		assertEquals( 3, NameUtil.indexOf("AB", list));
	}
	
	private static final class X implements Named {
		
		private String name;

		public X(String name) {
	        this.name = name;
        }

		@Override
		public String getName() {
	        return name;
        }
		
		@Override
		public String toString() {
		    return name;
		}
	}
	
}

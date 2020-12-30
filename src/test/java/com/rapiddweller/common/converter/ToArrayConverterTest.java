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

import java.util.ArrayList;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.Assert;
import com.rapiddweller.common.CollectionUtil;

import org.junit.Test;

/**
 * Tests the {@link ToArrayConverter}.
 * Created at 04.05.2008 08:00:15
 * @since 0.5.3
 * @author Volker Bergmann
 */
public class ToArrayConverterTest extends AbstractConverterTest {

	public ToArrayConverterTest() {
	    super(ToArrayConverter.class);
    }

	@Test
	public void testNull() {
		check(new Object[0], null, Object.class);
	}
	
	@Test
	public void testArray() {
		check(new Integer[0], new Integer[0], Integer.class);
		check(ArrayUtil.toArray(1), ArrayUtil.toArray(1), Integer.class);
	}

	@Test
	public void testList() {
		check(new Integer[0], new ArrayList<Integer>(), Integer.class);
		check(ArrayUtil.toArray(1), CollectionUtil.toList(1), Integer.class);
		check(ArrayUtil.toArray(1, 2), CollectionUtil.toList(1, 2), Integer.class);
	}

	@Test
	public void testString() {
		check(new byte[0], "");
		check(new byte[] { 'A', 'B' }, "AB");
	}

	@Test
	public void testSingleObject() {
		check(ArrayUtil.toArray("Alpha"), "Alpha", String.class);
	}
	
	// helpers ---------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
    private static <T> void check(T[] expected, Object source, Class<T> componentType) {
		Assert.equals(expected, (T[]) ToArrayConverter.convert(source, componentType));
	}

	private static <T> void check(byte[] expected, Object source) {
		Assert.equals(expected, (byte[]) ToArrayConverter.convert(source, byte.class));
	}
	
}

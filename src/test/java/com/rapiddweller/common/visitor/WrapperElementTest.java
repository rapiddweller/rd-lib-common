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
package com.rapiddweller.common.visitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import com.rapiddweller.common.Element;
import com.rapiddweller.common.Visitor;

import org.junit.Test;

/**
 * Tests the {@link WrapperElement} class.
 * Created at 02.05.2008 12:29:30
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class WrapperElementTest {
	
	@Test
	public void testEquals() {
		IntWrapper nullWrapper = new IntWrapper(null);
		IntWrapper oneWrapper = new IntWrapper(1);
		assertFalse(nullWrapper.equals(null));
		assertFalse(nullWrapper.equals(""));
		assertFalse(nullWrapper.equals(oneWrapper));
		assertTrue(nullWrapper.equals(nullWrapper));
		assertTrue(nullWrapper.equals(new IntWrapper(null)));
		assertTrue(oneWrapper.equals(new IntWrapper(1)));
		assertFalse(oneWrapper.equals(nullWrapper));
		assertFalse(nullWrapper.equals(oneWrapper));
	}
	
	public static class IntWrapper extends WrapperElement<Integer> {

		protected IntWrapper(Integer wrappedInt) {
			super(wrappedInt);
		}

		@Override
        protected Collection<Element<Integer>> getChildren(
				Visitor<Integer> visitor) {
			return new ArrayList<>();
		}
	}

}

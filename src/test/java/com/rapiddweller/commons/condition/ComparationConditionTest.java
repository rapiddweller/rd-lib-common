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
package com.rapiddweller.commons.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.commons.ArrayUtil;

import org.junit.Test;

/**
 * Tests the {@link ComparationCondition}.
 * Created at 29.04.2008 18:18:45
 * @since 0.4.2
 * @author Volker Bergmann
 */
public class ComparationConditionTest {

	@Test
	public void testEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.EQUAL);
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
	}
    
	@Test
	public void testNotEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.NOT_EQUAL);
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
	}

	@Test
	public void testGreaterOrEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.GREATER_OR_EQUAL);
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}

	@Test
	public void testGreater() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.GREATER);
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}

	@Test
	public void testLessOrEqual() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.LESS_OR_EQUAL);
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}

	@Test
	public void testLess() {
		ComparationCondition<Integer> condition = new ComparationCondition<Integer>(ComparationCondition.LESS);
		assertFalse(condition.evaluate(ArrayUtil.toArray(1, 1)));
		assertTrue(condition.evaluate(ArrayUtil.toArray(1, 2)));
		assertFalse(condition.evaluate(ArrayUtil.toArray(2, 1)));
	}
	
}

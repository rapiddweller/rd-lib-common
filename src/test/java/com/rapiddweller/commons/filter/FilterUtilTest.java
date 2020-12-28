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
package com.rapiddweller.commons.filter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.rapiddweller.commons.CollectionUtil;
import com.rapiddweller.commons.Filter;

import org.junit.Test;

/**
 * Tests the {@link FilterUtil} class.
 * Created at 04.05.2008 10:08:18
 * @since 0.5.3
 * @author Volker Bergmann
 *
 */
public class FilterUtilTest {

    @Test
    @SuppressWarnings("unchecked")
	public void test() {
		EvenFilter evenFilter = new EvenFilter();
		List<List<Integer>> groups = FilterUtil.filterGroups(
				new Integer[] { 1, 2, 3},
				new InverseFilter<>(evenFilter), evenFilter);
		assertEquals(2, groups.size());
		assertEquals(CollectionUtil.toList(1, 3), groups.get(0));
		assertEquals(CollectionUtil.toList(2), groups.get(1));
	}
	
	public class EvenFilter implements Filter<Integer> {
		@Override
		public boolean accept(Integer i) {
			return ((i % 2) == 0);
		}
	}
	
}

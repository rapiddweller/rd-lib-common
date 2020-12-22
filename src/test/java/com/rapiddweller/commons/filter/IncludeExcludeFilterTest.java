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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rapiddweller.commons.CollectionUtil;
import com.rapiddweller.commons.Filter;
import org.junit.Test;

/**
 * Tests the {@link IncludeExcludeFilter}.
 * Created: 08.06.2012 19:55:15
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class IncludeExcludeFilterTest {
	
	private static final Filter<Integer> ODD = new Filter<Integer>() {
		@Override
		public boolean accept(Integer n) {
			return (n % 2) == 1;
		}
	};
	
	private static final Filter<Integer> SMALL = new Filter<Integer>() {
		@Override
		public boolean accept(Integer n) {
			return n < 10;
		}
	};
	
	private static final Filter<Integer> PRIME = new Filter<Integer>() {
		final Set<Integer> PRIMES = CollectionUtil.toSet(2, 3, 5, 7, 11, 13, 17, 19);
		@Override
		public boolean accept(Integer n) {
			return PRIMES.contains(n);
		}
	};
	
	// single-step tests -----------------------------------------------------------------------------------------------
	
	@Test
	public void testSmall() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL), 
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}

	@Test
	public void testNotSmall() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL), 
				10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
	}

	// two-step tests --------------------------------------------------------------------------------------------------
	
	@Test
	public void testSmallAndOdd() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addInclusion(ODD), 
				1, 3, 5, 7, 9);
	}
	
	@Test
	public void testSmallAndNotOdd() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addExclusion(ODD), 
				0, 2, 4, 6, 8);
	}
	
	@Test
	public void testNotSmallAndOdd() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addInclusion(ODD), 
				11, 13, 15, 17, 19);
	}
	
	@Test
	public void testNotSmallAndNotOdd() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addExclusion(ODD), 
				10, 12, 14, 16, 18);
	}
	
	// three-step tests ------------------------------------------------------------------------------------------------
	
	@Test
	public void testSmallAndOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addInclusion(ODD).addInclusion(PRIME), 
				3, 5, 7);
	}
	
	@Test
	public void testSmallAndOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addInclusion(ODD).addExclusion(PRIME), 
				1, 9);
	}
	
	@Test
	public void testSmallAndNotOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addExclusion(ODD).addInclusion(PRIME), 
				2);
	}
	
	@Test
	public void testSmallAndNotOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addInclusion(SMALL).addExclusion(ODD).addExclusion(PRIME), 
				0, 4, 6, 8);
	}
	
	@Test
	public void testNotSmallAndOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addInclusion(ODD).addInclusion(PRIME), 
				11, 13, 17, 19);
	}
	
	@Test
	public void testNotSmallAndOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addInclusion(ODD).addExclusion(PRIME), 
				15);
	}
	
	@Test
	public void testNotSmallAndNotOddAndPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addExclusion(ODD).addInclusion(PRIME));
	}
	
	@Test
	public void testNotSmallAndNotOddAndNotPrime() {
		check(new IncludeExcludeFilter<Integer>().addExclusion(SMALL).addExclusion(ODD).addExclusion(PRIME), 
				10, 12, 14, 16, 18);
	}
	
	// check helper ----------------------------------------------------------------------------------------------------
	
	private static void check(IncludeExcludeFilter<Integer> filter, Integer... expected) {
		List<Integer> actual = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++)
			if (filter.accept(i))
				actual.add(i);
		assertEquals(CollectionUtil.toList(expected), actual);
	}

}

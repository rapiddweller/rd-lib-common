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
package com.rapiddweller.commons.math;

import static org.junit.Assert.*;

import com.rapiddweller.commons.comparator.IntComparator;
import org.junit.Test;

/**
 * Tests the {@link IntervalsParser}.
 * Created: 10.03.2011 17:36:33
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalsParserTest {

	@Test
	public void testWildcard() {
		Intervals<Integer> collection = parse("*");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(null, false, null, false), collection.getInterval(0));
	}

	@Test
	public void testEndpoint() {
		Intervals<Integer> collection = parse("1");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 1, true), collection.getInterval(0));
	}

	@Test
	public void testSingleInterval() {
		Intervals<Integer> collection = parse("[1,2]");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
	}

	@Test
	public void testMinInclusiveEndpoint() {
		Intervals<Integer> collection = parse(">=2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(2, true, null, false), collection.getInterval(0));
	}

	@Test
	public void testMinExclusiveEndpoint() {
		Intervals<Integer> collection = parse(">2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(2, false, null, false), collection.getInterval(0));
	}

	@Test
	public void testMaxInclusiveEndpoint() {
		Intervals<Integer> collection = parse("<=2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(null, false, 2, true), collection.getInterval(0));
	}

	@Test
	public void testMaxExclusiveEndpoint() {
		Intervals<Integer> collection = parse("<2");
		assertEquals(1, collection.intervalCount());
		assertEquals(createIntInterval(null, false, 2, false), collection.getInterval(0));
	}

	@Test
	public void testTwoEndpoints() {
		Intervals<Integer> collection = parse("1,3");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 1, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, true, 3, true), collection.getInterval(1));
	}

	@Test
	public void testTwoIntervals() {
		Intervals<Integer> collection = parse("[1,2],]3,5[");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, false, 5, false), collection.getInterval(1));
	}

	@Test
	public void testTwoIntervalsWithWhitespace() {
		Intervals<Integer> collection = parse("[ 1 , 2 ] , ] 3 , 5 [");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, false, 5, false), collection.getInterval(1));
	}

	@Test
	public void testIntervalAndBound() {
		Intervals<Integer> collection = parse("[1,2] , >= 3");
		assertEquals(2, collection.intervalCount());
		assertEquals(createIntInterval(1, true, 2, true), collection.getInterval(0));
		assertEquals(createIntInterval(3, true, null, false), collection.getInterval(1));
	}

	// helpers ---------------------------------------------------------------------------------------------------------
	
	private static Interval<Integer> createIntInterval(Integer min, boolean minInclusive, Integer max, boolean maxInclusive) {
		return new Interval<Integer>(min, minInclusive, max, maxInclusive, new IntComparator());
	}

	private static Intervals<Integer> parse(String spec) {
		return IntervalsParser.parse(spec, new IntParser(), new IntComparator());
	}
	
}

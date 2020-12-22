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

import java.text.ParsePosition;
import java.util.Comparator;

import static com.rapiddweller.commons.ParseUtil.skipWhiteSpace;
import com.rapiddweller.commons.Parser;

/**
 * {@link Parser} implementation for {@link Intervals}.
 * The supported syntax is as follows:
 * <pre>
 * Syntax                   Description
 * *                        Any version
 * 1.1	                    Version 1.1
 * 1.1, 1.2                 Versions 1.1 and 1.2
 * &gt;=1.1                    Version 1.1 and all subsequent ones
 * &gt; 1.0                    All versions after 1.0 
 * &lt; 2.0                    All versions before 2.0
 * &lt;= 2.1                   All versions until 2.1 (inclusive)
 * [1.1.2, 1.1.8]           All versions from 1.1.2 to 1.1.8
 * [1.1.2, 2.0[             All versions from 1.1.2 before 2.0
 * ]1.1.2, 2.0[             All versions after 1.1.2 and before 2.0
 * [1.1, 1.3], [1.5, 1.8]   Versions 1.1 through 1.3 and versions 1.5 to 1.8
 * [1.1, 1.4[,  ]1.4, 1.8]  Versions 1.1 through 1.8, excluding 1.4 
 * </pre>
 * 
 * Created: 10.03.2011 17:37:08
 * @param <E> the type of the bounds that define the interval
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntervalsParser<E> extends Parser<Intervals<E>> {

	private Parser<E> endpointParser;
	private Comparator<E> endpointComparator;
	
	public static <T> Intervals<T> parse(String text, Parser<T> endpointParser, Comparator<T> endpointComparator) {
		return new IntervalsParser<T>(endpointParser, endpointComparator).parseObject(text, new ParsePosition(0));
	}
	
	public IntervalsParser(Parser<E> endpointParser, Comparator<E> endpointComparator) {
		this.endpointParser = endpointParser;
		this.endpointComparator = endpointComparator;
	}

	@Override
	public Intervals<E> parseObject(String text, ParsePosition pos) {
		return parseObject(text, pos, new Intervals<E>());
	}

	public Intervals<E> parseObject(String text, ParsePosition pos, Intervals<E> target) {
		boolean continued;
		do {
			Interval<E> interval = parseRange(text, pos);
			if (interval != null)
				target.add(interval);
			skipWhiteSpace(text, pos);
			int index = pos.getIndex();
			continued = (index < text.length() && text.charAt(index) == ',');
			if (continued)
				advance(pos);
		} while (continued);
		return target;
	}

	private Interval<E> parseRange(String text, ParsePosition pos) {
		skipWhiteSpace(text, pos);
		char c = text.charAt(pos.getIndex());
		if (c == '*') {
			advance(pos);
			return new Interval<E>(null, false, null, false, endpointComparator);
		} else if (c == '>' || c == '<') {
			return parseBound(text, pos);
		} else if (c == '[' || c == ']') {
			return parseInterval(text, pos);
		} else {
			return parseEndpoint(text, pos);
		}
	}

	private Interval<E> parseBound(String text, ParsePosition pos) {
		char comparison = text.charAt(pos.getIndex());
		advance(pos);
		boolean orEqual = (text.charAt(pos.getIndex()) == '=');
		if (orEqual)
			advance(pos);
		skipWhiteSpace(text, pos);
		E endpoint = endpointParser.parseObject(text, pos);
		E min = null;
		E max = null;
		boolean minInclusive = false;
		boolean maxInclusive = false;
		if (comparison == '>') {
			min = endpoint;
			minInclusive = orEqual;
		} else {
			max = endpoint;
			maxInclusive = orEqual;
		}
		return new Interval<E>(min, minInclusive, max, maxInclusive, endpointComparator);
	}

	private Interval<E> parseInterval(String text, ParsePosition pos) {
		return new IntervalParser<E>(endpointParser, endpointComparator).parseObject(text, pos);
	}

	private Interval<E> parseEndpoint(String text, ParsePosition pos) {
		E endpoint = endpointParser.parseObject(text, pos);
		return new Interval<E>(endpoint, true, endpoint, true, endpointComparator);
	}

	private static void advance(ParsePosition pos) {
		pos.setIndex(pos.getIndex() + 1);
	}

}

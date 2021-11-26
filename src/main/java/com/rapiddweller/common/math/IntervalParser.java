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

package com.rapiddweller.common.math;

import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.ParseException;
import com.rapiddweller.common.Parser;

import java.text.ParsePosition;
import java.util.Comparator;

import static com.rapiddweller.common.ParseUtil.skipWhiteSpace;

/**
 * Parses an {@link Interval} generically using an endpoint parser and an endpoint comparator.
 * The endpoint parser has to be able to parse the interval endpoint values.
 * Created: 10.03.2011 15:33:01
 * @param <E> the type of the bounds that define the interval
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class IntervalParser<E> extends Parser<Interval<E>> {

  private final Parser<E> endpointParser;
  private final Comparator<E> endpointComparator;

  public static <T> Interval<T> parse(String text, Parser<T> endpointParser, Comparator<T> endpointComparator) {
    return new IntervalParser<T>(endpointParser, endpointComparator).parseObject(text, new ParsePosition(0));
  }

  public IntervalParser(Parser<E> endPointParser, Comparator<E> endpointComparator) {
    this.endpointParser = endPointParser;
    this.endpointComparator = endpointComparator;
  }

  @Override
  public Interval<E> parseObject(String text, ParsePosition pos) throws ParseException {
    skipWhiteSpace(text, pos);

    // parse left bracket
    boolean minInclusive;
    char c = text.charAt(pos.getIndex());
    switch (c) {
      case '[':
        minInclusive = true;
        break;
      case ']':
        minInclusive = false;
        break;
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForNothing("Expected '[' or ']', found: " + c, null);
    }
    pos.setIndex(pos.getIndex() + 1);
    skipWhiteSpace(text, pos);

    // parse lower endpoint
    E min = endpointParser.parseObject(text, pos);
    skipWhiteSpace(text, pos);

    // parse comma
    c = text.charAt(pos.getIndex());
    if (c != ',') {
      throw ExceptionFactory.getInstance().syntaxErrorForNothing("Expected ',', found '" + c + "'", null);
    }
    pos.setIndex(pos.getIndex() + 1);
    skipWhiteSpace(text, pos);

    // parse upper endpoint
    E max = endpointParser.parseObject(text, pos);
    skipWhiteSpace(text, pos);

    // parse right bracket
    boolean maxInclusive;
    c = text.charAt(pos.getIndex());
    switch (c) {
      case ']':
        maxInclusive = true;
        break;
      case '[':
        maxInclusive = false;
        break;
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForNothing("Expected '[' or ']', found: " + c, null);
    }
    pos.setIndex(pos.getIndex() + 1);
    skipWhiteSpace(text, pos);

    return new Interval<>(min, minInclusive, max, maxInclusive, endpointComparator);
  }

}

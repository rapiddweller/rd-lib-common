/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses strings which represents an integer which ist greater or equals 0.<br/><br/>
 * Created: 08.12.2021 14:50:57
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class NonNegativeLongParser extends TypedParser<Long> {

  public NonNegativeLongParser() {
    super("Non-negative long value", Long.class);
  }

  @Override
  protected Long parseImpl(String spec) {
    long result = Long.parseLong(spec);
    if (result < 0) {
      throw syntaxError(spec, null);
    }
    return result;
  }

}

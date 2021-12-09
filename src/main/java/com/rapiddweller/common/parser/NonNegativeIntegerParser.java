/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses strings which represent non-negative integers.<br/><br/>
 * Created: 08.12.2021 17:30:17
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class NonNegativeIntegerParser extends TypedParser<Integer> {

  public NonNegativeIntegerParser() {
    super("non-negative integer", Integer.class);
  }

  @Override
  protected Integer parseImpl(String spec) {
    int result = Integer.parseInt(spec);
    if (result < 0) {
      throw syntaxError(spec, null);
    }
    return result;
  }

}

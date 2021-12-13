/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses a string which represents a positive integer.<br/><br/>
 * Created: 08.12.2021 15:52:39
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class PositiveIntegerParser extends AbstractTypedParser<Integer> {

  public PositiveIntegerParser() {
    super("positive integer value", Integer.class);
  }

  @Override
  protected Integer parseImpl(String spec) {
    int result = Integer.parseInt(spec);
    if (result <= 0) {
      throw syntaxError(spec, null);
    }
    return result;
  }

}

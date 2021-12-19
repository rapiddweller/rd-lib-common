/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses string which represent long values greater than zero.<br/><br/>
 * Created: 08.12.2021 17:25:04
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class PositiveLongParser extends AbstractTypedParser<Long> {

  public PositiveLongParser() {
    super("positive long", Long.class);
  }

  @Override
  protected Long parseImpl(String spec) {
    long result = Long.parseLong(spec);
    if (result <= 0) {
      throw syntaxError(spec, null);
    }
    return result;
  }

}
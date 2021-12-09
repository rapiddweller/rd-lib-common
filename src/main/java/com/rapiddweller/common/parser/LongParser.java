/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses long values.<br/><br/>
 * Created: 08.12.2021 14:40:00
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LongParser extends TypedParser<Long> {

  public LongParser() {
    super("long value", Long.class);
  }
  protected Long parseImpl(String spec) {
    return Long.parseLong(spec);
  }

}

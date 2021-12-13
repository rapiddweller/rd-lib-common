/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses strings which represent integer values.<br/><br/>
 * Created: 08.12.2021 17:27:42
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class IntegerParser extends AbstractTypedParser<Integer> {

  protected IntegerParser() {
    super("integer value", Integer.class);
  }

  @Override
  protected Integer parseImpl(String spec) {
    return Integer.parseInt(spec);
  }
}

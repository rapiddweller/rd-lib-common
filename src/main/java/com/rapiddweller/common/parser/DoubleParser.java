/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses Strings that represent double values.<br/><br/>
 * Created: 08.12.2021 20:14:35
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DoubleParser extends AbstractTypedParser<Double> {

  public DoubleParser() {
    super("double value", Double.class);
  }

  @Override
  protected Double parseImpl(String spec) {
    return Double.parseDouble(spec);
  }

}

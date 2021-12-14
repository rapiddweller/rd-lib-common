/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.CommonErrorIds;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parses Strings that represent double values.<br/><br/>
 * Created: 08.12.2021 20:14:35
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DoubleParser extends AbstractTypedParser<Double> {

  Double min;
  Double max;

  public DoubleParser() {
    this(null, null);
  }

  public DoubleParser(Double min, Double max) {
    super("double value", Double.class);
    this.min = min;
    this.max = max;
  }

  @Override
  protected Double parseImpl(String spec) {
    double result = Double.parseDouble(spec);
    if ((min != null && result < min) || (max != null && result > max)) {
      throw ExceptionFactory.getInstance().illegalArgument("", null,
          CommonErrorIds.GEN_ILLEGAL_ARGUMENT);
    }
    return result;
  }

}

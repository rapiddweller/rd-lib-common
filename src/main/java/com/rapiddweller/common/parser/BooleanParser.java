/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parses strings representing a boolean.<br/><br/>
 * Created: 08.12.2021 15:27:36
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class BooleanParser extends TypedParser<Boolean> {

  public BooleanParser() {
    super("boolean value", Boolean.class);
  }

  protected Boolean parseImpl(String spec) {
    if ("true".equals(spec)) {
      return true;
    } else if ("false".equals(spec)) {
      return false;
    } else {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Not a boolean", spec);
    }
  }

}

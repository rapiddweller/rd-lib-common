/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.converter.LiteralParserConverter;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parses a literal.<br/><br/>
 * Created: 15.12.2021 14:36:58
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LiteralParser implements TypedParser<Object> {

  @Override
  public Object parse(String spec) {
    try {
      return LiteralParserConverter.parse(spec);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Not a literal: ", spec);
    }
  }

  @Override
  public String getDescription() {
    return "literal";
  }

  @Override
  public Class<Object> getResultType() {
    return Object.class;
  }
}

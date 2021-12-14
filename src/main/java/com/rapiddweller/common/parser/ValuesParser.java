/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.util.Set;

/**
 * Parses string that match one element of a values list.<br/><br/>
 * Created: 14.12.2021 20:28:59
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ValuesParser extends AbstractTypedParser<String> {

  private final Set<String> values;

  public ValuesParser(String name, String... values) {
    super(name, String.class);
    this.values = CollectionUtil.toSet(values);
  }

  @Override
  protected String parseImpl(String spec) {
    if (!values.contains(spec)) {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Illegal argument", spec);
    }
    return spec;
  }

}

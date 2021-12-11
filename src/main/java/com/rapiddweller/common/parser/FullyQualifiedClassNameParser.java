/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.BeanUtil;

/**
 * Parses fully qualified names of Java classes.<br/><br/>
 * Created: 08.12.2021 16:53:12
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FullyQualifiedClassNameParser extends RegexBasedStringParser {

  private final boolean required;

  public FullyQualifiedClassNameParser(boolean required) {
    super("Java class name", "([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");
    this.required = required;
  }

  @Override
  protected String parseImpl(String spec) {
    spec = super.parseImpl(spec);
    if (required) {
      BeanUtil.forName(spec);
    }
    return spec;
  }

}

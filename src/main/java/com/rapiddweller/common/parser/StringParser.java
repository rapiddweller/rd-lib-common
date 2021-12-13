/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.StringUtil;

/**
 * Parses strings without constraints.<br/><br/>
 * Created: 12.12.2021 15:41:25
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class StringParser extends AbstractTypedParser<String> {

  private final boolean unescape;

  public StringParser() {
    this(true);
  }

  public StringParser(boolean unescape) {
    super("string", String.class);
    this.unescape = unescape;
  }

  @Override
  protected String parseImpl(String spec) {
    if (unescape) {
      return StringUtil.unescape(spec);
    } else {
      return spec;
    }
  }

}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses strings without constraints.<br/><br/>
 * Created: 12.12.2021 15:41:25
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class StringParser extends TypedParser<String> {

  public StringParser() {
    super("string", String.class);
  }

  @Override
  protected String parseImpl(String spec) {
    return spec;
  }

}

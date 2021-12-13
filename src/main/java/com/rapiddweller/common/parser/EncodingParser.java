/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import java.nio.charset.Charset;

/**
 * Parses the names of character encodings.<br/><br/>
 * Created: 08.12.2021 15:37:43
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class EncodingParser extends AbstractTypedParser<String> {

  public EncodingParser() {
    super("encoding", String.class);
  }

  @Override
  protected String parseImpl(String spec) {
    Charset.forName(spec);
    return spec;
  }

}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses the names of character encodings.<br/><br/>
 * Created: 08.12.2021 15:37:43
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class EncodingParser extends RegexBasedStringParser {
  public EncodingParser() {
    super("encoding", "[A-Za-z0-9_\\-\\+\\.\\:]+");
  }
}

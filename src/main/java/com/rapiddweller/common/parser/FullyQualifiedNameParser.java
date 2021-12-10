/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses a fully qualified name.<br/><br/>
 * Created: 10.12.2021 13:02:45
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FullyQualifiedNameParser extends RegexBasedStringParser {
  public FullyQualifiedNameParser() {
    super("fully qualified name", "([a-zA-Z_][a-zA-Z0-9_]*\\.)*[a-zA-Z_][a-zA-Z0-9_]*");
  }
}

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses fully qualified names of Java classes.<br/><br/>
 * Created: 08.12.2021 16:53:12
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FullyQualifiedClassNameParser extends RegexBasedStringParser {

  public FullyQualifiedClassNameParser() {
    super("Java class name", "([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");
  }

}

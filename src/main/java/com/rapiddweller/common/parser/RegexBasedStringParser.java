/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.RegexUtil;

/**
 * Validates a string with a regular expression and returns it if valid.<br/><br/>
 * Created: 08.12.2021 15:18:02
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class RegexBasedStringParser extends AbstractTypedParser<String> {

  private final String regex;

  public RegexBasedStringParser(String description, String regex) {
    super(description, String.class);
    this.regex = regex;
  }

  @Override
  protected String parseImpl(String spec) {
    if (!RegexUtil.matches(regex, spec)) {
      throw syntaxError(spec, null);
    } else {
      return spec;
    }
  }

}

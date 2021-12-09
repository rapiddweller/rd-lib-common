/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses a string that represents a character.<br/><br/>
 * Created: 08.12.2021 16:03:15
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CharacterParser extends TypedParser<Character> {

  public CharacterParser() {
    super("character", Character.class);
  }

  @Override
  protected Character parseImpl(String spec) {
    if (spec.length() == 1) {
      return spec.charAt(0);
    } else {
      throw syntaxError(spec, null);
    }
  }
}

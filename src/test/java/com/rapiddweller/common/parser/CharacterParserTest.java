/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link CharacterParser}.<br/><br/>
 * Created: 09.12.2021 17:48:58
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CharacterParserTest {

  CharacterParser p = new CharacterParser();

  @Test
  public void testOK() {
    assertEquals('x', (char) p.parse("x"));
  }

  @Test
  public void testTooLong() {
    assertThrows(SyntaxError.class, () -> p.parse("xx"));
  }

  @Test
  public void testNull() {
    assertThrows(SyntaxError.class, () -> p.parse(null));
  }

  @Test
  public void testEmpty() {
    assertThrows(SyntaxError.class, () -> p.parse(""));
  }

}

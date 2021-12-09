/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link PositiveLongParser}.<br/><br/>
 * Created: 09.12.2021 18:42:28
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class PositiveLongParserTest {

  PositiveLongParser p = new PositiveLongParser();

  @Test
  public void testOK() {
    assertEquals(123, (long) p.parse("123"));
    assertEquals(1, (long) p.parse("1"));
  }

  @Test
  public void testIllegal() {
    assertThrows(SyntaxError.class, () -> p.parse("xxx"));
    assertThrows(SyntaxError.class, () -> p.parse("3.141"));
    assertThrows(SyntaxError.class, () -> p.parse("0"));
    assertThrows(SyntaxError.class, () -> p.parse("-100"));
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

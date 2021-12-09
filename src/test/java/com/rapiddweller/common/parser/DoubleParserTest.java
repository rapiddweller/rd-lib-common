/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link DoubleParser}.<br/><br/>
 * Created: 09.12.2021 17:54:51
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DoubleParserTest {

  DoubleParser p = new DoubleParser();

  @Test
  public void testOK() {
    assertEquals(3.141, p.parse("3.141"), 0.0001);
    assertEquals(-3.141, p.parse("-3.141"), 0.0001);
    assertEquals(0., p.parse("0"), 0.0001);
    assertEquals(0., p.parse("0."), 0.0001);
    assertEquals(0., p.parse("0.0"), 0.0001);
  }

  @Test
  public void testIllegal() {
    assertThrows(SyntaxError.class, () -> p.parse("xxx"));
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

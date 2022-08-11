/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link FloatParser}.<br/><br/>
 * Created: 09.12.2021 18:15:32
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FloatParserTest {

  FloatParser p = new FloatParser();

  @Test
  public void testOK() {
    assertEquals(3.141f, p.parse("3.141"), 0.0001);
    assertEquals(-3.141f, p.parse("-3.141"), 0.0001);
    assertEquals(0.f, p.parse("0"), 0.0001);
    assertEquals(0.f, p.parse("0."), 0.0001);
    assertEquals(0.f, p.parse("0.0"), 0.0001);
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

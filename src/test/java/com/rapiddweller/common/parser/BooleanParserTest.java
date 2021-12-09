/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link BooleanParser}.<br/><br/>
 * Created: 09.12.2021 09:05:35
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class BooleanParserTest {

  BooleanParser p = new BooleanParser();

  @Test
  public void testTrue() {
    assertTrue(p.parse("true"));
  }

  @Test
  public void testFalse() {
    assertFalse(p.parse("false"));
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

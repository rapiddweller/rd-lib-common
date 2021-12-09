/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.Level;
import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link EnumParser}.<br/><br/>
 * Created: 09.12.2021 18:04:10
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class EnumParserTest {

  EnumParser<Level> p = new EnumParser<>(Level.class);

  @Test
  public void test() {
    assertEquals(Level.fatal, p.parse("fatal"));
    assertEquals(Level.trace, p.parse("trace"));
  }

  @Test
  public void testIllegal() {
    assertThrows(SyntaxError.class, () -> p.parse("hello"));
  }

  @Test
  public void testNull() {
    assertNull(p.parse(null));
  }

  @Test
  public void testEmpty() {
    assertThrows(SyntaxError.class, () -> p.parse(""));
  }

}

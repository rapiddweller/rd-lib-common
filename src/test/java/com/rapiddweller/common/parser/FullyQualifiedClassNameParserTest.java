/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.Level;
import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link FullyQualifiedClassNameParser}.<br/><br/>
 * Created: 09.12.2021 18:18:04
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FullyQualifiedClassNameParserTest {

  FullyQualifiedClassNameParser p = new FullyQualifiedClassNameParser(true);

  @Test
  public void test() {
    assertEquals("java.lang.String", p.parse("java.lang.String"));
  }

  @Test
  public void testIllegal() {
    assertThrows(SyntaxError.class, () -> p.parse("123"));
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

/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link FullyQualifiedNameParser}.<br/><br/>
 * Created: 10.12.2021 13:04:44
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FullyQualifiedNameParserTest {

  FullyQualifiedNameParser p = new FullyQualifiedNameParser();

  @Test
  public void test() {
    assertEquals("java.util.String", p.parse("java.util.String"));
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

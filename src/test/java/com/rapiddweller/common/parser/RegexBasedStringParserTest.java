/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the RegexStr.<br/><br/>
 * Created: 09.12.2021 18:44:51
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class RegexBasedStringParserTest {

  RegexBasedStringParser p = new RegexBasedStringParser("test", "[ABC][12]");

  @Test
  public void test() {
    assertEquals("A1", p.parse("A1"));
    assertEquals("B2", p.parse("B2"));
    assertEquals("C1", p.parse("C1"));
  }

  @Test
  public void testIllegal() {
    assertThrows(SyntaxError.class, () -> p.parse("123"));
    assertThrows(SyntaxError.class, () -> p.parse("A3"));
    assertThrows(SyntaxError.class, () -> p.parse("D1"));
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

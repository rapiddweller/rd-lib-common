/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.SyntaxError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link EncodingParser}.<br/><br/>
 * Created: 09.12.2021 18:01:34
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class EncodingParserTest {

  EncodingParser p = new EncodingParser();

  @Test
  public void testOK() {
    assertEquals("UTF-8", p.parse("UTF-8"));
    assertEquals("ASCII", p.parse("ASCII"));
    assertEquals("ISO-8859-1", p.parse("ASCII"));
  }

  @Test
  public void testIllegal() {
    assertThrows(SyntaxError.class, () -> p.parse("+*-.!"));
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

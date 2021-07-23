package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharUtilTest {
  @Test
  public void testOrdinal() {
    assertEquals(65, CharUtil.ordinal('A'));
  }

  @Test
  public void testCharacter() {
    assertEquals('\u0001', CharUtil.character(1));
  }
}


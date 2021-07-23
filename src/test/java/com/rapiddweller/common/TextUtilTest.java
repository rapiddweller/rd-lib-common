package com.rapiddweller.common;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TextUtilTest {
  @Test
  public void testFormatList() {
    assertEquals("", TextUtil.formatList(new ArrayList<>()));
  }

  @Test
  public void testFormatTable() {
    assertEquals("table\n", TextUtil.formatTable(new Object[][] {new Object[] {"table"}}, 'A'));
    assertEquals("65\n", TextUtil.formatTable(new Object[][] {new Object[] {65}}, 'A'));
    assertEquals("tableAtable\n", TextUtil.formatTable(new Object[][] {new Object[] {"table", "table"}}, 'A'));
  }
}


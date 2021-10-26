package com.rapiddweller.common;

import com.rapiddweller.common.format.Alignment;
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

  @Test
  public void testLinedTable_without_title() {
    String[] title = null;
    Object[][] data = new Object[][] {
        { "Name", "Age" },
        { "Alice", 25 },
        { "Bob", 32}
    };
    assertEquals(
        "+-------+-----+\n" +
            "| Name  | Age |\n" +
            "+-------+-----+\n" +
            "| Alice |  25 |\n" +
            "+-------+-----+\n" +
            "| Bob   |  32 |\n" +
            "+-------+-----+\n", TextUtil.formatLinedTable(title, data));
  }

  @Test
  public void testLinedTable_with_short_title() {
    String[] title = new String[] { "People" };
    Object[][] data = new Object[][] {
        { "Name", "Age" },
        { "Alice", 25 },
        { "Bob", 32}
    };
    assertEquals(
        "" +
            "+-------------+\n" +
            "| People      |\n" +
            "+-------+-----+\n" +
            "| Name  | Age |\n" +
            "+-------+-----+\n" +
            "| Alice |  25 |\n" +
            "+-------+-----+\n" +
            "| Bob   |  32 |\n" +
            "+-------+-----+\n", TextUtil.formatLinedTable(title, data));
  }

  @Test
  public void testLinedTable_with_long_title() {
    String[] title = new String[] {
        "Way too big header, but try anyway",
        "It's just a test, so who really cares?"
    };
    Object[][] data = new Object[][] {
        { "Name", "Age" },
        { "Alice", 25 },
        { "Bob", 32}
    };
    assertEquals(
        "" +
            "+----------------------------------------+\n" +
            "| Way too big header, but try anyway     |\n" +
            "| It's just a test, so who really cares? |\n" +
            "+----------------------------------+-----+\n" +
            "|               Name               | Age |\n" +
            "+----------------------------------+-----+\n" +
            "| Alice                            |  25 |\n" +
            "+----------------------------------+-----+\n" +
            "| Bob                              |  32 |\n" +
            "+----------------------------------+-----+\n", TextUtil.formatLinedTable(title, data));
  }

  @Test
  public void testLinedTable_with_alignment() {
    String[] title = new String[] { "People" };
    Object[][] data = new Object[][] {
        { "Name", "Age" },
        { "Alice", 25 },
        { "Bob", 102}
    };
    Alignment[] alignments = { Alignment.RIGHT, Alignment.LEFT };
    String formatted = TextUtil.formatLinedTable(title, data, alignments, false);
    assertEquals(
        "" +
            "+-------------+\n" +
            "| People      |\n" +
            "+-------+-----+\n" +
            "|  Name | Age |\n" +
            "+-------+-----+\n" +
            "| Alice | 25  |\n" +
            "+-------+-----+\n" +
            "|   Bob | 102 |\n" +
            "+-------+-----+\n", formatted);
  }


}


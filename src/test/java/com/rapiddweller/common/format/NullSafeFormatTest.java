package com.rapiddweller.common.format;

import com.rapiddweller.common.ArrayFormat;
import org.junit.Test;

import java.text.ParsePosition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NullSafeFormatTest {
  @Test
  public void testParseObject() {
    NullSafeFormat nullSafeFormat = new NullSafeFormat(new ArrayFormat(), "Null String");
    assertEquals(1, ((String[]) nullSafeFormat.parseObject("Source", new ParsePosition(1))).length);
  }

  @Test
  public void testParseObject2() {
    NullSafeFormat nullSafeFormat = new NullSafeFormat(new ArrayFormat(), "ource");
    assertNull(nullSafeFormat.parseObject("Source", new ParsePosition(1)));
  }

  @Test
  public void testParseObject3() {
    NullSafeFormat nullSafeFormat = new NullSafeFormat(new ArrayFormat(), "Null String");
    assertNull(nullSafeFormat.parseObject(null, new ParsePosition(1)));
  }

  @Test
  public void testParseObject4() {
    NullSafeFormat nullSafeFormat = new NullSafeFormat(new NullSafeFormat(new ArrayFormat(), "Null String"),
        "Null String");
    assertEquals(1, ((String[]) nullSafeFormat.parseObject("Source", new ParsePosition(1))).length);
  }
}


package com.rapiddweller.common.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LongRangeTest {
  @Test
  public void testConstructor() {
    LongRange actualLongRange = new LongRange(1L, 1L);
    assertEquals(1L, actualLongRange.getMax());
    assertEquals(1L, actualLongRange.getMin());
  }

  @Test
  public void testSetMin() {
    LongRange longRange = new LongRange(1L, 1L);
    longRange.setMin(1L);
    assertEquals(1L, longRange.getMin());
  }

  @Test
  public void testSetMax() {
    LongRange longRange = new LongRange(1L, 1L);
    longRange.setMax(1L);
    assertEquals(1L, longRange.getMax());
  }

  @Test
  public void testContains() {
    assertTrue((new LongRange(1L, 1L)).contains(1L));
    assertFalse((new LongRange(9223372036854775807L, 1L)).contains(1L));
    assertFalse((new LongRange(1L, 0L)).contains(1L));
  }

  @Test
  public void testHashCode() {
    assertEquals(993, (new LongRange(1L, 1L)).hashCode());
  }

  @Test
  public void testEquals() {
    assertFalse((new LongRange(1L, 1L)).equals("obj"));
    assertFalse((new LongRange(1L, 1L)).equals(null));
  }

  @Test
  public void testToString() {
    assertEquals("1", (new LongRange(1L, 1L)).toString());
    assertEquals("0...1", (new LongRange(0L, 1L)).toString());
  }
}


package com.rapiddweller.common.accessor;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class MapAccessorTest {
  @Test
  public void testGetValue() {
    MapAccessor<Map<Object, Object>, Object, Object> mapAccessor = new MapAccessor<>(
        "key");
    assertNull(mapAccessor.getValue(new HashMap<>()));
  }

  @Test
  public void testGetValue2() {
    MapAccessor<Map<Object, Object>, Object, Object> mapAccessor = new MapAccessor<>(
        0);
    assertNull(mapAccessor.getValue(new HashMap<>(1)));
  }

  @Test
  public void testEquals() {
    assertFalse((new MapAccessor<Map<Object, Object>, Object, Object>("key")).equals("o"));
    assertFalse((new MapAccessor<Map<Object, Object>, Object, Object>("key")).equals(null));
  }

  @Test
  public void testHashCode() {
    assertEquals(106079, (new MapAccessor<Map<Object, Object>, Object, Object>("key")).hashCode());
    assertEquals(0, (new MapAccessor<>(null)).hashCode());
  }
}


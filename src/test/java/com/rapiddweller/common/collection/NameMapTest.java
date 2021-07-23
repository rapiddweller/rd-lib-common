package com.rapiddweller.common.collection;

import com.rapiddweller.common.CharSet;
import com.rapiddweller.common.Named;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NameMapTest {
  @Test
  public void testConstructor() {
    assertTrue((new NameMap<>(1)).isEmpty());
    assertTrue((new NameMap<>(1, 10.0f)).isEmpty());
    assertTrue((new NameMap<>(new ArrayList<>())).isEmpty());
  }

  @Test
  public void testConstructor2() {
    ArrayList<Named> namedList = new ArrayList<>();
    namedList.add(new CharSet());
    assertEquals(1, (new NameMap<>(namedList)).size());
  }

  @Test
  public void testConstructor3() {
    CharSet charSet = new CharSet();
    CharSet charSet1 = new CharSet();
    assertEquals(1, (new NameMap<Named>(charSet, charSet1, new CharSet())).size());
  }
}


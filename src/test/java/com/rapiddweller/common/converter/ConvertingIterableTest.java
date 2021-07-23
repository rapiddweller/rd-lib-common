package com.rapiddweller.common.converter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class ConvertingIterableTest {
  @Test
  public void testGetType() {
    ArrayList<Object> iterable = new ArrayList<>();
    Class<Object> actualType = (new ConvertingIterable<Object, Object>(iterable, new ToCollectionConverter()))
        .getType();
    assertSame(List.class, actualType);
  }

  @Test
  public void testIterator() {
    ArrayList<Object> iterable = new ArrayList<>();
    assertFalse((new ConvertingIterable<Object, Object>(iterable, new ToCollectionConverter())).iterator().hasNext());
  }
}


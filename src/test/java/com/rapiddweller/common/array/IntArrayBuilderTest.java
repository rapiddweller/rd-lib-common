package com.rapiddweller.common.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class IntArrayBuilderTest {
  @Test
  public void testConstructor() {
    assertEquals(10, (new IntArrayBuilder()).buffer.length);
    assertEquals(1, (new IntArrayBuilder(1)).buffer.length);
    assertThrows(NegativeArraySizeException.class, () -> new IntArrayBuilder(-1));
  }

  @Test
  public void testLength() {
    assertEquals(0, (new IntArrayBuilder()).length());
  }

  @Test
  public void testGet() {
    assertEquals(0, (new IntArrayBuilder()).get(1));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new IntArrayBuilder(1)).get(1));
  }

  @Test
  public void testSet() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder(1);
    intArrayBuilder.set(1, 42);
    assertEquals("42", intArrayBuilder.toString());
    assertEquals(1, intArrayBuilder.itemCount);
    assertEquals(2, intArrayBuilder.buffer.length);
  }

  @Test
  public void testSet2() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    intArrayBuilder.set(10, 42);
    assertEquals("42", intArrayBuilder.toString());
    assertEquals(1, intArrayBuilder.itemCount);
  }

  @Test
  public void testSet3() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new IntArrayBuilder()).set(-1, 42));
  }

  @Test
  public void testSet4() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new IntArrayBuilder(0)).set(1, 42));
  }

  @Test
  public void testSet5() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder(1);
    intArrayBuilder.set(2, 42);
    assertEquals("0, 42", intArrayBuilder.toString());
    assertEquals(2, intArrayBuilder.itemCount);
    assertEquals(4, intArrayBuilder.buffer.length);
  }

  @Test
  public void testSet6() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new IntArrayBuilder(0)).set(0, 42));
  }

  @Test
  public void testAdd() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    IntArrayBuilder actualAddResult = intArrayBuilder.add(42);
    assertSame(intArrayBuilder, actualAddResult);
    assertEquals("42", actualAddResult.toString());
    assertEquals(1, actualAddResult.itemCount);
  }

  @Test
  public void testAdd2() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder(1);
    IntArrayBuilder actualAddResult = intArrayBuilder.add(42);
    assertSame(intArrayBuilder, actualAddResult);
    assertEquals("42", actualAddResult.toString());
    assertEquals(1, actualAddResult.itemCount);
    assertEquals(2, actualAddResult.buffer.length);
  }

  @Test
  public void testAdd3() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new IntArrayBuilder(0)).add(42));
  }

  @Test
  public void testAddAll() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    intArrayBuilder.addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1});
    assertEquals("1, 1, 1, 1, 1, 1, 1, 1", intArrayBuilder.toString());
    assertEquals(8, intArrayBuilder.itemCount);
  }

  @Test
  public void testAddAll2() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder(1);
    intArrayBuilder.addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1});
    assertEquals("1, 1, 1, 1, 1, 1, 1, 1", intArrayBuilder.toString());
    assertEquals(8, intArrayBuilder.itemCount);
    assertEquals(16, intArrayBuilder.buffer.length);
  }

  @Test
  public void testAddAll3() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    intArrayBuilder.addAll(new int[] {2, 2, 2, 2});
    assertEquals("2, 2, 2, 2", intArrayBuilder.toString());
    assertEquals(4, intArrayBuilder.itemCount);
  }

  @Test
  public void testAddAll4() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new IntArrayBuilder(0)).addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1}));
  }

  @Test
  public void testAddAll5() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    intArrayBuilder.addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1}, 0, 1);
    assertEquals("1", intArrayBuilder.toString());
    assertEquals(1, intArrayBuilder.itemCount);
  }

  @Test
  public void testAddAll6() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new IntArrayBuilder()).addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1}, -1, 1));
  }

  @Test
  public void testAddAll7() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder(1);
    intArrayBuilder.addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1}, 0, 1);
    assertEquals("1", intArrayBuilder.toString());
    assertEquals(1, intArrayBuilder.itemCount);
    assertEquals(2, intArrayBuilder.buffer.length);
  }

  @Test
  public void testAddAll8() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new IntArrayBuilder(0)).addAll(new int[] {1, 1, 1, 1, 1, 1, 1, 1}, 0, 1));
  }

  @Test
  public void testGetAndDeleteBuffer() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    int[] actualAndDeleteBuffer = intArrayBuilder.getAndDeleteBuffer();
    assertSame(intArrayBuilder.buffer, actualAndDeleteBuffer);
    assertEquals(10, actualAndDeleteBuffer.length);
  }

  @Test
  public void testToArray() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    assertEquals(0, intArrayBuilder.toArray().length);
    assertEquals(0, intArrayBuilder.itemCount);
    assertNull(intArrayBuilder.buffer);
  }

  @Test
  public void testToString() {
    assertEquals("", (new IntArrayBuilder()).toString());
  }

  @Test
  public void testToString2() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    intArrayBuilder.add(42);
    assertEquals("42", intArrayBuilder.toString());
  }

  @Test
  public void testToString3() {
    IntArrayBuilder intArrayBuilder = new IntArrayBuilder();
    intArrayBuilder.add(42);
    intArrayBuilder.add(2);
    assertEquals("42, 2", intArrayBuilder.toString());
  }
}


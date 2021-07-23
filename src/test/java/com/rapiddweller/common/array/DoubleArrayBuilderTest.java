package com.rapiddweller.common.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class DoubleArrayBuilderTest {
  @Test
  public void testConstructor() {
    DoubleArrayBuilder actualDoubleArrayBuilder = new DoubleArrayBuilder();
    assertEquals("", actualDoubleArrayBuilder.toString());
    assertEquals(0, actualDoubleArrayBuilder.size());
  }

  @Test
  public void testConstructor2() {
    DoubleArrayBuilder actualDoubleArrayBuilder = new DoubleArrayBuilder(1);
    assertEquals("", actualDoubleArrayBuilder.toString());
    assertEquals(0, actualDoubleArrayBuilder.size());
  }

  @Test
  public void testConstructor3() {
    assertThrows(NegativeArraySizeException.class, () -> new DoubleArrayBuilder(-1));
  }

  @Test
  public void testClear() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    doubleArrayBuilder.clear();
    assertEquals(0, doubleArrayBuilder.size());
  }

  @Test
  public void testGet() {
    assertEquals(0.0, (new DoubleArrayBuilder()).get(1), 0.0);
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new DoubleArrayBuilder(1)).get(1));
  }

  @Test
  public void testAdd() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    DoubleArrayBuilder actualAddResult = doubleArrayBuilder.add(10.0);
    assertSame(doubleArrayBuilder, actualAddResult);
    assertEquals("10", actualAddResult.toString());
    assertEquals(1, actualAddResult.size());
  }

  @Test
  public void testAdd2() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder(1);
    DoubleArrayBuilder actualAddResult = doubleArrayBuilder.add(10.0);
    assertSame(doubleArrayBuilder, actualAddResult);
    assertEquals("10", actualAddResult.toString());
    assertEquals(1, actualAddResult.size());
  }

  @Test
  public void testAdd3() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new DoubleArrayBuilder(0)).add(10.0));
  }

  @Test
  public void testAddAll() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    doubleArrayBuilder.addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0});
    assertEquals("10, 10, 10, 10, 10, 10, 10, 10", doubleArrayBuilder.toString());
    assertEquals(8, doubleArrayBuilder.size());
  }

  @Test
  public void testAddAll2() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder(1);
    doubleArrayBuilder.addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0});
    assertEquals("10, 10, 10, 10, 10, 10, 10, 10", doubleArrayBuilder.toString());
    assertEquals(8, doubleArrayBuilder.size());
  }

  @Test
  public void testAddAll3() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    doubleArrayBuilder.addAll(new double[] {10.0, 10.0, 10.0, 10.0});
    assertEquals("10, 10, 10, 10", doubleArrayBuilder.toString());
    assertEquals(4, doubleArrayBuilder.size());
  }

  @Test
  public void testAddAll4() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new DoubleArrayBuilder(0)).addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0}));
  }

  @Test
  public void testAddAll5() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    doubleArrayBuilder.addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0}, 0, 1);
    assertEquals("10", doubleArrayBuilder.toString());
    assertEquals(1, doubleArrayBuilder.size());
  }

  @Test
  public void testAddAll6() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new DoubleArrayBuilder()).addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0}, -1, 1));
  }

  @Test
  public void testAddAll7() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder(1);
    doubleArrayBuilder.addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0}, 0, 1);
    assertEquals("10", doubleArrayBuilder.toString());
    assertEquals(1, doubleArrayBuilder.size());
  }

  @Test
  public void testAddAll8() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new DoubleArrayBuilder(0)).addAll(new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0}, 0, 1));
  }

  @Test
  public void testToArray() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    assertEquals(0, doubleArrayBuilder.toArray().length);
    assertEquals(0, doubleArrayBuilder.size());
  }

  @Test
  public void testToString() {
    assertEquals("", (new DoubleArrayBuilder()).toString());
  }

  @Test
  public void testToString2() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    doubleArrayBuilder.add(10.0);
    assertEquals("10", doubleArrayBuilder.toString());
  }

  @Test
  public void testToString3() {
    DoubleArrayBuilder doubleArrayBuilder = new DoubleArrayBuilder();
    doubleArrayBuilder.add(10.0);
    doubleArrayBuilder.add(0.5);
    assertEquals("10, 0.5", doubleArrayBuilder.toString());
  }
}


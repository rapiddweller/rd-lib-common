package com.rapiddweller.common.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class DoubleRingBufferTest {
  @Test
  public void testConstructor() {
    DoubleRingBuffer actualDoubleRingBuffer = new DoubleRingBuffer(3);
    assertFalse(actualDoubleRingBuffer.isFilled());
    assertEquals(3, actualDoubleRingBuffer.size());
    assertEquals(Double.NaN, actualDoubleRingBuffer.first(), 0.0);
    assertEquals(0, actualDoubleRingBuffer.getSampleCount());
  }

  @Test
  public void testConstructor2() {
    assertThrows(NegativeArraySizeException.class, () -> new DoubleRingBuffer(-1));
  }

  @Test
  public void testGetCapacity() {
    assertEquals(3, (new DoubleRingBuffer(3)).getCapacity());
  }

  @Test
  public void testSize() {
    assertEquals(3, (new DoubleRingBuffer(3)).size());
  }

  @Test
  public void testAdd() {
    DoubleRingBuffer doubleRingBuffer = new DoubleRingBuffer(3);
    doubleRingBuffer.add(10.0);
    assertEquals(10.0, doubleRingBuffer.sum(), 0.0);
    assertEquals(Double.NaN, doubleRingBuffer.first(), 0.0);
    assertEquals(1, doubleRingBuffer.getSampleCount());
  }

  @Test
  public void testAdd2() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new DoubleRingBuffer(0)).add(10.0));
  }

  @Test
  public void testAdd3() {
    DoubleRingBuffer doubleRingBuffer = new DoubleRingBuffer(1);
    doubleRingBuffer.add(10.0);
    assertTrue(doubleRingBuffer.isFilled());
    assertEquals(10.0, doubleRingBuffer.sum(), 0.0);
    assertEquals(10.0, doubleRingBuffer.first(), 0.0);
    assertEquals(1, doubleRingBuffer.getSampleCount());
  }

  @Test
  public void testLast() {
    assertEquals(0.0, (new DoubleRingBuffer(3)).last(), 0.0);
  }

  @Test
  public void testFirst() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).first(), 0.0);
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new DoubleRingBuffer(0)).first());
  }

  @Test
  public void testMin() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).min(), 0.0);
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new DoubleRingBuffer(0)).min());
  }

  @Test
  public void testMax() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).max(), 0.0);
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new DoubleRingBuffer(0)).max());
  }

  @Test
  public void testAverage() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).average(), 0.0);
  }

  @Test
  public void testMedian() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).median(), 0.0);
  }

  @Test
  public void testSum() {
    assertEquals(0.0, (new DoubleRingBuffer(3)).sum(), 0.0);
  }

  @Test
  public void testCorrectedStandardDeviation() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).correctedStandardDeviation(), 0.0);
  }

  @Test
  public void testStandardDeviation() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).standardDeviation(), 0.0);
  }

  @Test
  public void testVariance() {
    assertEquals(Double.NaN, (new DoubleRingBuffer(3)).variance(), 0.0);
  }
}


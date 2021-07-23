/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Testing {@link RingBuffer}.<br><br>
 * Created: 27.10.2019 18:52:51
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */

public class RingBufferTest {

  @Test
  public void testGetCapacity() {
    assertEquals(3, (new RingBuffer<>(Object.class, 3)).getCapacity());
  }

  @Test
  public void testIsFilled() {
    assertFalse((new RingBuffer<>(Object.class, 3)).isFilled());
    assertTrue((new RingBuffer<>(Object.class, 0)).isFilled());
  }

  @Test
  public void testContains() {
    assertFalse((new RingBuffer<>(Object.class, 3)).contains("object"));
  }

  @Test
  public void testAdd() {
    RingBuffer<Object> ringBuffer = new RingBuffer<>(Object.class, 3);
    assertNull(ringBuffer.add("object"));
    assertEquals(1, ringBuffer.size());
  }

  @Test
  public void testAdd2() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new RingBuffer<>(Object.class, 0)).add("object"));
  }

  @Test
  public void testAdd3() {
    RingBuffer<Object> ringBuffer = new RingBuffer<>(Object.class, 1);
    assertNull(ringBuffer.add("object"));
    assertEquals(1, ringBuffer.size());
  }

  @Test
  public void testConstructor() {
    RingBuffer<Object> actualRingBuffer = new RingBuffer<>(Object.class, 3);
    assertEquals(3, actualRingBuffer.buffer.length);
    assertEquals(0, actualRingBuffer.size());
  }

  @Test
  public void testConstructor2() {
    RingBuffer<Object> actualRingBuffer = new RingBuffer<>(Object.class, 0);
    assertEquals(0, actualRingBuffer.buffer.length);
    assertEquals(0, actualRingBuffer.size());
  }

  @Test
  public void testGet() {
    RingBuffer<Integer> buffer = new RingBuffer<>(Integer.class, 3);
    buffer.add(1);
    assertEquals(1, (int) buffer.get(0));
    buffer.add(2);
    assertEquals(2, (int) buffer.get(0));
    assertEquals(1, (int) buffer.get(1));
    buffer.add(3);
    assertEquals(3, (int) buffer.get(0));
    assertEquals(2, (int) buffer.get(1));
    assertEquals(1, (int) buffer.get(2));
    buffer.add(4);
    assertEquals(4, (int) buffer.get(0));
    assertEquals(3, (int) buffer.get(1));
    assertEquals(2, (int) buffer.get(2));
  }

  @Test
  public void testGet2() {
    assertNull((new RingBuffer<>(Object.class, 3)).get(1));
  }

  @Test
  public void testGet3() {
    assertNull((new RingBuffer<>(Object.class, 3)).get(-1));
  }

  @Test
  public void testGet4() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new RingBuffer<>(Object.class, 3)).get(-2147483648));
  }

}

package com.rapiddweller.common.array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ByteArrayBuilderTest {
    @Test
    public void testConstructor() {
        assertEquals(10, (new ByteArrayBuilder()).buffer.length);
        assertEquals(1, (new ByteArrayBuilder(1)).buffer.length);
    }

    @Test
    public void testAdd() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        ByteArrayBuilder actualAddResult = byteArrayBuilder.add((byte) 65);
        assertSame(byteArrayBuilder, actualAddResult);
        assertEquals("65", actualAddResult.toString());
        assertEquals(1, actualAddResult.itemCount);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testAddArrayIndexOutOfBoundsException() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(0);
        byteArrayBuilder.add((byte) 65);
    }

    @Test
    public void testToArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        assertEquals(0, byteArrayBuilder.toArray().length);
        assertNull(byteArrayBuilder.buffer);
        assertEquals(0, byteArrayBuilder.itemCount);
    }

    @Test
    public void testToString() {
        assertEquals("", (new ByteArrayBuilder()).toString());
    }

    @Test
    public void testToString2() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.add((byte) 65);
        assertEquals("65", byteArrayBuilder.toString());
    }

    @Test
    public void testToString3() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.add((byte) 65);
        byteArrayBuilder.add((byte) 0);
        assertEquals("65, 0", byteArrayBuilder.toString());
    }
}


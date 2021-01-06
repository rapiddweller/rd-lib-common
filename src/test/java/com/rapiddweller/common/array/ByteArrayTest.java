package com.rapiddweller.common.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteArrayTest {
    @Test
    public void testConstructor() {
        assertEquals(10, (new ByteArray()).getBytes().length);
        assertEquals(1, (new ByteArray(1)).getBytes().length);
    }
}


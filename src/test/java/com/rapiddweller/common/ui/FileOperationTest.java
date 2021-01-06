package com.rapiddweller.common.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileOperationTest {
    @Test
    public void testValueOf() {
        assertEquals(FileOperation.CUSTOM, FileOperation.valueOf("CUSTOM"));
    }

    @Test
    public void testValues() {
        assertEquals(3, FileOperation.values().length);
    }
}


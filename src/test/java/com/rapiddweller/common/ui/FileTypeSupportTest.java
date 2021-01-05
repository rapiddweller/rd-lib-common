package com.rapiddweller.common.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileTypeSupportTest {
    @Test
    public void testValueOf() {
        assertEquals(FileTypeSupport.directoriesOnly, FileTypeSupport.valueOf("directoriesOnly"));
    }

    @Test
    public void testValues() {
        assertEquals(3, FileTypeSupport.values().length);
    }
}


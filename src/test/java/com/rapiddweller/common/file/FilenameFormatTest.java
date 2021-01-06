package com.rapiddweller.common.file;

import static org.junit.Assert.assertTrue;

import java.text.ParsePosition;

import org.junit.Test;

public class FilenameFormatTest {
    @Test
    public void testSetFullPathUsed() {
        FilenameFormat filenameFormat = new FilenameFormat();
        filenameFormat.setFullPathUsed(true);
        assertTrue(filenameFormat.isFullPathUsed());
    }

    @Test
    public void testSetFullPathUsed2() {
        FilenameFormat filenameFormat = new FilenameFormat();
        filenameFormat.setFullPathUsed(true);
        assertTrue(filenameFormat.isFullPathUsed());
    }

    @Test
    public void testParseObject() {
        ParsePosition pos = new ParsePosition(1);
        assertTrue((new FilenameFormat()).parseObject("foo.txt", pos) instanceof java.io.File);
    }

    @Test
    public void testParseObject2() {
        ParsePosition pos = new ParsePosition(1);
        assertTrue((new FilenameFormat()).parseObject("foo.txt", pos) instanceof java.io.File);
    }
}


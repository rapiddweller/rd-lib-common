package com.rapiddweller.common.ui.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ui.FileOperation;
import com.rapiddweller.common.ui.FileTypeSupport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

public class FileFieldTest {
    @Test
    public void testConstructor() {
        FileField actualFileField = new FileField();
        assertTrue(actualFileField.chooser instanceof SwingFileChooser);
        assertTrue(actualFileField.fullPathDisplayed);
        assertEquals(FileOperation.OPEN, actualFileField.operation);
        assertTrue(actualFileField.isFullPathUsed());
    }

    @Test
    public void testConstructor2() {
        FileField actualFileField = new FileField(1);
        assertTrue(actualFileField.chooser instanceof SwingFileChooser);
        assertTrue(actualFileField.fullPathDisplayed);
        assertEquals(FileOperation.OPEN, actualFileField.operation);
        assertTrue(actualFileField.isFullPathUsed());
    }

    @Test
    public void testConstructor5() {
        FileField actualFileField = new FileField(1, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(),
                FileTypeSupport.filesOnly, FileOperation.OPEN);
        assertTrue(actualFileField.chooser instanceof SwingFileChooser);
        assertTrue(actualFileField.fullPathDisplayed);
        assertEquals(FileOperation.OPEN, actualFileField.operation);
        assertTrue(actualFileField.isFullPathUsed());
    }

    @Test
    public void testConstructor7() {
        FileField actualFileField = new FileField(1, Paths.get(System.getProperty("java.io.tmpdir"), "").toFile(),
                FileTypeSupport.filesOnly, FileOperation.OPEN);
        assertTrue(actualFileField.chooser instanceof SwingFileChooser);
        assertTrue(actualFileField.fullPathDisplayed);
        assertEquals(FileOperation.OPEN, actualFileField.operation);
        assertTrue(actualFileField.isFullPathUsed());
    }

}


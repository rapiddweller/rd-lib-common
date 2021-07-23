package com.rapiddweller.common.ui.swing;

import com.rapiddweller.common.ui.FileOperation;
import com.rapiddweller.common.ui.FileTypeSupport;
import org.junit.Assume;
import org.junit.Test;

import java.nio.file.Paths;

import static com.rapiddweller.common.SystemInfo.isLinux;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileFieldTest {
  @Test
  public void testConstructor() {
    Assume.assumeTrue(isLinux());
    FileField actualFileField = new FileField();
    assertTrue(actualFileField.chooser instanceof SwingFileChooser);
    assertTrue(actualFileField.fullPathDisplayed);
    assertEquals(FileOperation.OPEN, actualFileField.operation);
    assertTrue(actualFileField.isFullPathUsed());
  }

  @Test
  public void testConstructor2() {
    Assume.assumeTrue(isLinux());
    FileField actualFileField = new FileField(1);
    assertTrue(actualFileField.chooser instanceof SwingFileChooser);
    assertTrue(actualFileField.fullPathDisplayed);
    assertEquals(FileOperation.OPEN, actualFileField.operation);
    assertTrue(actualFileField.isFullPathUsed());
  }

  @Test
  public void testConstructor5() {
    Assume.assumeTrue(isLinux());
    FileField actualFileField = new FileField(1, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(),
        FileTypeSupport.filesOnly, FileOperation.OPEN);
    assertTrue(actualFileField.chooser instanceof SwingFileChooser);
    assertTrue(actualFileField.fullPathDisplayed);
    assertEquals(FileOperation.OPEN, actualFileField.operation);
    assertTrue(actualFileField.isFullPathUsed());
  }

  @Test
  public void testConstructor7() {
    Assume.assumeTrue(isLinux());
    FileField actualFileField = new FileField(1, Paths.get(System.getProperty("java.io.tmpdir"), "").toFile(),
        FileTypeSupport.filesOnly, FileOperation.OPEN);
    assertTrue(actualFileField.chooser instanceof SwingFileChooser);
    assertTrue(actualFileField.fullPathDisplayed);
    assertEquals(FileOperation.OPEN, actualFileField.operation);
    assertTrue(actualFileField.isFullPathUsed());
  }

}


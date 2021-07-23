package com.rapiddweller.common.file;

import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class FileSizeComparatorTest {
  @Test
  public void testCompare() {
    File file1 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    File file2 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    assertEquals(0, (new FileSizeComparator()).compare(file1, file2));
  }

  @Test
  public void testCompare2() {
    File file1 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    File file2 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    assertEquals(0, (new FileSizeComparator()).compare(file1, file2));
  }
}


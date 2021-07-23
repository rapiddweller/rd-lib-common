package com.rapiddweller.common.file;

import com.rapiddweller.common.NullSafeComparator;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class FilenameComparatorTest {
  @Test
  public void testCompare() {
    File o1 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    File o2 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    assertEquals(0, (new FilenameComparator()).compare(o1, o2));
  }

  @Test
  public void testCompare2() {
    FilenameComparator filenameComparator = new FilenameComparator(new NullSafeComparator<>());
    File o1 = Paths.get("", "").toFile();
    assertEquals(-8,
        filenameComparator.compare(o1, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testCompare3() {
    File o1 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    File o2 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
    assertEquals(0, (new FilenameComparator()).compare(o1, o2));
  }

  @Test
  public void testCompare4() {
    FilenameComparator filenameComparator = new FilenameComparator(new NullSafeComparator<>());
    File o1 = Paths.get("", "").toFile();
    assertEquals(-8,
        filenameComparator.compare(o1, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }
}


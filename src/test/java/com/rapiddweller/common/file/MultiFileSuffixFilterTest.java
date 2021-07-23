package com.rapiddweller.common.file;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MultiFileSuffixFilterTest {
  @Test
  public void testAccept() {
    MultiFileSuffixFilter multiFileSuffixFilter = new MultiFileSuffixFilter(true, "foo", "foo", "foo");
    assertFalse(multiFileSuffixFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept2() {
    MultiFileSuffixFilter multiFileSuffixFilter = new MultiFileSuffixFilter(true, "", "foo", "foo");
    assertTrue(multiFileSuffixFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept3() {
    MultiFileSuffixFilter multiFileSuffixFilter = new MultiFileSuffixFilter(true, "foo", "foo", "foo");
    assertFalse(multiFileSuffixFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept4() {
    MultiFileSuffixFilter multiFileSuffixFilter = new MultiFileSuffixFilter(true, "", "foo", "foo");
    assertTrue(multiFileSuffixFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }
}


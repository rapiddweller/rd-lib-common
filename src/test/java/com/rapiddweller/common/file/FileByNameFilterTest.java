package com.rapiddweller.common.file;

import com.rapiddweller.common.filter.ConstantFilter;
import com.rapiddweller.common.filter.IncludeExcludeFilter;
import com.rapiddweller.common.filter.OrFilter;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileByNameFilterTest {
  @Test
  public void testAccept() {
    OrFilter<String> orFilter = new OrFilter<>(null, null, null);
    OrFilter<String> orFilter1 = new OrFilter<>(null, null, null);
    FileByNameFilter fileByNameFilter = new FileByNameFilter(
        new OrFilter<>(new IncludeExcludeFilter<>(), orFilter, orFilter1));
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept10() {
    ConstantFilter<String> constantFilter = new ConstantFilter<>(true);
    OrFilter<String> orFilter = new OrFilter<>(null, null, null);
    FileByNameFilter fileByNameFilter = new FileByNameFilter(
        new OrFilter<>(constantFilter, orFilter, new OrFilter<>(null, null, null)));
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept11() {
    FileByNameFilter fileByNameFilter = new FileByNameFilter(new OrFilter<>());
    assertFalse(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept12() {
    FileByNameFilter fileByNameFilter = new FileByNameFilter(null);
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept2() {
    ConstantFilter<String> constantFilter = new ConstantFilter<>(true);
    OrFilter<String> orFilter = new OrFilter<>(null, null, null);
    FileByNameFilter fileByNameFilter = new FileByNameFilter(
        new OrFilter<>(constantFilter, orFilter, new OrFilter<>(null, null, null)));
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept3() {
    FileByNameFilter fileByNameFilter = new FileByNameFilter(new OrFilter<>());
    assertFalse(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept4() {
    FileByNameFilter fileByNameFilter = new FileByNameFilter(null);
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept5() {
    OrFilter<String> orFilter = new OrFilter<>(null, null, null);
    OrFilter<String> orFilter1 = new OrFilter<>(null, null, null);
    FileByNameFilter fileByNameFilter = new FileByNameFilter(
        new OrFilter<>(new IncludeExcludeFilter<>(), orFilter, orFilter1));
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept6() {
    ConstantFilter<String> constantFilter = new ConstantFilter<>(true);
    OrFilter<String> orFilter = new OrFilter<>(null, null, null);
    FileByNameFilter fileByNameFilter = new FileByNameFilter(
        new OrFilter<>(constantFilter, orFilter, new OrFilter<>(null, null, null)));
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept7() {
    FileByNameFilter fileByNameFilter = new FileByNameFilter(new OrFilter<>());
    assertFalse(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept8() {
    FileByNameFilter fileByNameFilter = new FileByNameFilter(null);
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  @Test
  public void testAccept9() {
    OrFilter<String> orFilter = new OrFilter<>(null, null, null);
    OrFilter<String> orFilter1 = new OrFilter<>(null, null, null);
    FileByNameFilter fileByNameFilter = new FileByNameFilter(
        new OrFilter<>(new IncludeExcludeFilter<>(), orFilter, orFilter1));
    assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }
}


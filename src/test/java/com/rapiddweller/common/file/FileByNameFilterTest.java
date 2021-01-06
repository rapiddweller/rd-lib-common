package com.rapiddweller.common.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.filter.ConstantFilter;
import com.rapiddweller.common.filter.IncludeExcludeFilter;
import com.rapiddweller.common.filter.OrFilter;

import java.nio.file.Paths;

import org.junit.Test;

public class FileByNameFilterTest {
    @Test
    public void testAccept() {
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        FileByNameFilter fileByNameFilter = new FileByNameFilter(
                new OrFilter<String>(new IncludeExcludeFilter<String>(), orFilter, orFilter1));
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept10() {
        ConstantFilter<String> constantFilter = new ConstantFilter<String>(true);
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        FileByNameFilter fileByNameFilter = new FileByNameFilter(
                new OrFilter<String>(constantFilter, orFilter, new OrFilter<String>(null, null, null)));
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept11() {
        FileByNameFilter fileByNameFilter = new FileByNameFilter(new OrFilter<String>());
        assertFalse(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept12() {
        FileByNameFilter fileByNameFilter = new FileByNameFilter(null);
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept2() {
        ConstantFilter<String> constantFilter = new ConstantFilter<String>(true);
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        FileByNameFilter fileByNameFilter = new FileByNameFilter(
                new OrFilter<String>(constantFilter, orFilter, new OrFilter<String>(null, null, null)));
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept3() {
        FileByNameFilter fileByNameFilter = new FileByNameFilter(new OrFilter<String>());
        assertFalse(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept4() {
        FileByNameFilter fileByNameFilter = new FileByNameFilter(null);
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept5() {
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        FileByNameFilter fileByNameFilter = new FileByNameFilter(
                new OrFilter<String>(new IncludeExcludeFilter<String>(), orFilter, orFilter1));
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept6() {
        ConstantFilter<String> constantFilter = new ConstantFilter<String>(true);
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        FileByNameFilter fileByNameFilter = new FileByNameFilter(
                new OrFilter<String>(constantFilter, orFilter, new OrFilter<String>(null, null, null)));
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept7() {
        FileByNameFilter fileByNameFilter = new FileByNameFilter(new OrFilter<String>());
        assertFalse(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept8() {
        FileByNameFilter fileByNameFilter = new FileByNameFilter(null);
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept9() {
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        FileByNameFilter fileByNameFilter = new FileByNameFilter(
                new OrFilter<String>(new IncludeExcludeFilter<String>(), orFilter, orFilter1));
        assertTrue(fileByNameFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }
}


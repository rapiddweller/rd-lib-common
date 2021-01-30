package com.rapiddweller.common.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import com.rapiddweller.common.NullSafeComparator;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Test;

public class FileTreeModelTest {

    @Test
    public void testConstructor2() {
        File toFileResult = Paths.get(System.getProperty("java.io.tmpdir"), "").toFile();
        assertSame((new FileTreeModel(toFileResult)).getRoot(), toFileResult);
    }

    @Test
    public void testConstructor4() {
        File toFileResult = Paths.get(System.getProperty("java.io.tmpdir"), "").toFile();
        assertSame((new FileTreeModel(toFileResult, new NullSafeComparator<>())).getRoot(), toFileResult);
    }

    @Test
    public void testConstructor5() {
        File toFileResult = Paths.get(System.getProperty("java.io.tmpdir"), "").toFile();
        assertSame((new FileTreeModel(toFileResult)).getRoot(), toFileResult);
    }


    @Test
    public void testConstructor7() {
        File toFileResult = Paths.get(System.getProperty("java.io.tmpdir"), "").toFile();
        assertSame((new FileTreeModel(toFileResult, new NullSafeComparator<>())).getRoot(), toFileResult);
    }


    @Test
    public void testIsLeaf() {
        FileTreeModel fileTreeModel = new FileTreeModel(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());
        assertFalse(fileTreeModel.isLeaf(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testIsLeaf2() {
        FileTreeModel fileTreeModel = new FileTreeModel(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());
        assertFalse(fileTreeModel.isLeaf(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testGetIndexOfChild() {
        FileTreeModel fileTreeModel = new FileTreeModel(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());
        File parent = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        assertEquals(-1,
                fileTreeModel.getIndexOfChild(parent, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }


    @Test
    public void testGetIndexOfChild3() {
        FileTreeModel fileTreeModel = new FileTreeModel(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());
        File parent = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        assertEquals(-1,
                fileTreeModel.getIndexOfChild(parent, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

}


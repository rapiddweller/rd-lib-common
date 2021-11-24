/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

import com.rapiddweller.common.exception.IllegalArgumentError;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the {@link FileUtil} class.
 * Created at 10.03.2009 18:21:54
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */

public class FileUtilTest {

  protected File ROOT_DIR = new File("target" + File.separator + "filetest");
  protected File SUB_DIR = new File(ROOT_DIR, "sub");
  protected File ROOT_DIR_FILE = new File(ROOT_DIR, "fr.txt");
  protected File SUB_DIR_FILE = new File(SUB_DIR, "fs.txt");

  @Test
  public void testAddTimestampToFilename() {
    String filename = "alpha.txt";
    String result = FileUtil.addTimestampToFilename(filename, LocalDateTime.of(2020, 2, 4, 8, 19));
    assertEquals("alpha-20-02-04T08-19-00.txt", result);
  }

  @Test
  public void testCopyFile() throws Exception {
    File rootDir = new File("target");
    File srcFile = new File(rootDir, getClass().getSimpleName() + ".original");
    File targetFile = new File(rootDir, getClass().getSimpleName() + ".copy");
    try {
      IOUtil.writeTextFile(srcFile.getAbsolutePath(), "0123456789");
      assertTrue(srcFile.exists());

      // create file no matter the preconditions
      FileUtil.copy(srcFile, targetFile, true);
      assertTrue(targetFile.exists());
      assertTrue(targetFile.length() >= 10);

      // check that copy fails if file exists and overwrite=false
      try {
        FileUtil.copy(srcFile, targetFile, false);
        fail("Exception expected if target file exists");
      } catch (ConfigurationError e) {
        // exception is expected here
      }

      // check that copy succeeds if file exists and overwrite=true
      FileUtil.copy(srcFile, targetFile, true);

    } finally {
      // remove the used files
      FileUtil.deleteIfExists(targetFile);
      FileUtil.deleteIfExists(srcFile);
    }
  }

  @Test
  public void testListFiles() throws Exception {
    createTestFolders();
    try {
      check(null, true, false, false, ROOT_DIR_FILE); // non-recursive, only files, w/o pattern
      check("fr.*", true, false, false, ROOT_DIR_FILE); // non-recursive, only files,  w/ pattern
      check("x.*", true, false, false); // non-recursive, only files, w/ pattern
      check(null, false, true, false, SUB_DIR); // non-recursive, only folders, w/o pattern
      check(null, true, true, false, SUB_DIR, ROOT_DIR_FILE); // non-recursive, files and folders, w/o pattern
      check(null, true, true, true, SUB_DIR, ROOT_DIR_FILE, SUB_DIR_FILE); // recursive, files and folders, w/o pattern
      check(null, false, true, true, SUB_DIR); // recursive, only folders, w/o pattern
      check("f.*", true, true, true, ROOT_DIR_FILE, SUB_DIR_FILE); // recursive, files and folders, "f.*" pattern
      check("s.*", false, true, true, SUB_DIR); // recursive, only folders, "s.*" pattern
    } finally {
      // remove the used files
      removeTestFolders();
    }
  }

  @Test
  public void testLocalFilename() {
    assertEquals(null, FileUtil.localFilename(null));
    assertEquals("", FileUtil.localFilename(""));
    assertEquals("x", FileUtil.localFilename("x"));
    assertEquals("x", FileUtil.localFilename("/x"));
    assertEquals("x", FileUtil.localFilename("y/z/x"));
    assertEquals("x", FileUtil.localFilename("y" + File.separator + "z" + File.separator + "x"));
  }

  @Test
  public void testRelativePath() {
    assertEquals(".", FileUtil.relativePath(new File("a/b/index.html"), new File("a/b"), '/'));
    assertEquals("b", FileUtil.relativePath(new File("a/index.html"), new File("a/b"), '/'));
    assertEquals("../c", FileUtil.relativePath(new File("a/b/index.html"), new File("a/c"), '/'));
  }

  @Test
  public void testFileOfLimitedPathLength_valid() throws Exception {
    File root = new File(SystemInfo.isWindows() ? "C:\\" : "/");
    File normResult = FileUtil.fileOfLimitedPathLength(
        root, "testx", ".xml", root.getCanonicalPath().length() + 10, false);
    assertEquals(root.getCanonicalPath() + "testx.xml", normResult.getCanonicalPath());
    File cutResult = FileUtil.fileOfLimitedPathLength(
        root, "testx", ".xml", root.getCanonicalPath().length() + 9, false);
    assertEquals(root.getCanonicalPath() + "test.xml", cutResult.getCanonicalPath());
  }

  @Test(expected = IllegalArgumentError.class)
  public void testFileOfLimitedPathLength_invalid() {
    File root = new File((SystemInfo.isWindows() ? "C:\\" : "/") + "01234567890123456789");
    FileUtil.fileOfLimitedPathLength(root, "test", ".xml", 10, false);
  }

  @Test
  public void testNewFile() {
    // file relative to working dir
    assertEquals(new File(SystemInfo.getCurrentDir(), "x").getAbsoluteFile(), FileUtil.newFile("x").getAbsoluteFile());
    if (!SystemInfo.isWindows()) {
      // file in user home
      assertEquals(new File(SystemInfo.getUserHome(), "x").getAbsolutePath(), FileUtil.newFile("~/x").getAbsolutePath());
      // file in other user's home
      File otherUsersHome = new File(new File(SystemInfo.getUserHome()).getParentFile(), "qawsed");
      assertEquals(new File(otherUsersHome, "x").getAbsolutePath(), FileUtil.newFile("~qawsed/x").getAbsolutePath());
    }
  }

  @Test
  public void testPrependFilePrefix() {
    assertEquals("p_test.txt", FileUtil.prependFilePrefix("p_", "test.txt"));
    assertEquals("localdir/p_test.txt", FileUtil.prependFilePrefix("p_", "localdir/test.txt"));
    assertEquals("/root/localdir/p_test.txt", FileUtil.prependFilePrefix("p_", "/root/localdir/test.txt"));
    assertEquals("localdir\\p_test.txt", FileUtil.prependFilePrefix("p_", "localdir\\test.txt"));
    assertEquals("C:\\root\\localdir\\p_test.txt", FileUtil.prependFilePrefix("p_", "C:\\root\\localdir\\test.txt"));
  }

  // test helpers ----------------------------------------------------------------------------------------------------

  private void check(String regex, boolean acceptingFiles, boolean acceptingFolders, boolean recursive,
                     File... expectedResult) {
    List<File> actual = FileUtil.listFiles(ROOT_DIR, regex, recursive, acceptingFiles, acceptingFolders);
    List<File> expected = CollectionUtil.toList(expectedResult);
    assertTrue("Expected " + expected + ", but was " + actual, CollectionUtil.equalsIgnoreOrder(expected, actual));
  }

  protected void createTestFolders() throws IOException {
    FileUtil.ensureDirectoryExists(ROOT_DIR);
    FileUtil.ensureDirectoryExists(SUB_DIR);
    IOUtil.writeTextFile(ROOT_DIR_FILE.getAbsolutePath(), "rfc");
    IOUtil.writeTextFile(SUB_DIR_FILE.getAbsolutePath(), "sfc");
  }

  protected void removeTestFolders() {
    FileUtil.deleteIfExists(SUB_DIR_FILE);
    FileUtil.deleteIfExists(SUB_DIR);
    FileUtil.deleteIfExists(ROOT_DIR_FILE);
    FileUtil.deleteIfExists(ROOT_DIR);
  }

}

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

package com.rapiddweller.common.file;

import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.Visitor;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created: 04.02.2007 08:20:57
 *
 * @author Volker Bergmann
 */
public class FileElementTest {

  @Test
  public void test() {
    File root = new File("target/classes/test");
    File alpha = new File(root, "alpha");
    FileUtil.ensureDirectoryExists(alpha);
    File beta = new File(alpha, "beta");
    FileUtil.ensureDirectoryExists(beta);
    CheckVisitor visitor = new CheckVisitor(root, alpha, beta);
    new FileElement(root).accept(visitor);
    assertTrue(visitor.allFound());
  }

  static class CheckVisitor implements Visitor<File> {

    private final File[] expectedFiles;
    private final boolean[] filesFound;

    public CheckVisitor(File... expectedFiles) {
      this.expectedFiles = expectedFiles;
      Arrays.sort(this.expectedFiles);
      this.filesFound = new boolean[expectedFiles.length];
    }

    @Override
    public void visit(File file) {
      int index = Arrays.binarySearch(expectedFiles, file);
      if (index >= 0) {
        filesFound[index] = true;
      }
    }

    public boolean allFound() {
      for (boolean b : filesFound) {
        if (!b) {
          return false;
        }
      }
      return true;
    }
  }

}

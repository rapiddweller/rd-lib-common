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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.Element;
import com.rapiddweller.common.TreeModel;
import com.rapiddweller.common.Visitor;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * databene TreeModel implementation for files.
 * Created: 08.05.2007 17:55:54
 * @author Volker Bergmann
 */
public class FileTreeModel implements TreeModel<File>, Element<File> {

  private final File root;
  private final Comparator<File> fileComparator;

  public FileTreeModel(File root) {
    this(root, new FilenameComparator());
  }

  public FileTreeModel(File root, Comparator<File> fileComparator) {
    if (!root.exists()) {
      throw ExceptionFactory.getInstance().illegalArgument("File/Directory not found: " + root);
    }
    this.root = root;
    this.fileComparator = fileComparator;
  }

  @Override
  public File getRoot() {
    return root;
  }

  @Override
  public File getParent(File child) {
    return child.getParentFile();
  }

  @Override
  public File getChild(File parent, int index) {
    return listFiles(parent)[index];
  }

  @Override
  public int getChildCount(File parent) {
    if (parent.isFile()) {
      return 0;
    } else {
      return listFiles(parent).length;
    }
  }

  @Override
  public boolean isLeaf(File node) {
    return (node).isFile();
  }

  @Override
  public int getIndexOfChild(File parent, File child) {
    File[] files = listFiles(parent);
    return ArrayUtil.indexOf(child, files);
  }

  private File[] listFiles(File parent) {
    File[] files = parent.listFiles();
    if (files != null) {
      Arrays.sort(files, fileComparator);
    } else {
      files = new File[0];
    }
    return files;
  }

  @Override
  public void accept(Visitor<File> visitor) {
    accept(visitor, root);
  }

  private void accept(Visitor<File> visitor, File file) {
    visitor.visit(file);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        for (File child : files) {
          accept(visitor, child);
        }
      }
    }
  }

}

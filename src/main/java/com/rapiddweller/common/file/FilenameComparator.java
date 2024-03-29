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

import com.rapiddweller.common.comparator.CompositeTextComparator;

import java.io.File;
import java.util.Comparator;

/**
 * Compares (local) files by their names,
 * supporting different comparation strategies by a user-definable Comparator.
 * Created: 11.05.2007 18:24:45
 *
 * @author Volker Bergmann
 */
public class FilenameComparator implements Comparator<File> {

  private final Comparator<String> nameComparator;

  /**
   * Instantiates a new Filename comparator.
   */
  public FilenameComparator() {
    this(new CompositeTextComparator());
  }

  /**
   * Instantiates a new Filename comparator.
   *
   * @param nameComparator the name comparator
   */
  public FilenameComparator(Comparator<String> nameComparator) {
    this.nameComparator = nameComparator;
  }

  @Override
  public int compare(File o1, File o2) {
    return nameComparator.compare(o1.getName(), o2.getName());
  }
}

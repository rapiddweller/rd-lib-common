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

import com.rapiddweller.common.comparator.LongComparator;

import java.io.File;
import java.util.Comparator;

/**
 * {@link Comparator} implementation which compares two {@link File}s by their size.
 * Created: 06.03.2011 15:11:11
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class FileSizeComparator implements Comparator<File> {

  @Override
  public int compare(File file1, File file2) {
    return LongComparator.compare(file1.length(), file2.length());
  }

}